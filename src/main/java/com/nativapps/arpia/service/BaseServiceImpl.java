package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.BaseDao;
import com.nativapps.arpia.database.dao.CashDao;
import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.dao.OperationDao;
import com.nativapps.arpia.database.entity.Base;
import com.nativapps.arpia.database.entity.BaseRecord;
import com.nativapps.arpia.database.entity.Cash;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.Operation;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.SystemConfig;
import com.nativapps.arpia.model.dto.BaseRecordData;
import com.nativapps.arpia.model.dto.BaseRequest;
import com.nativapps.arpia.model.dto.BaseResponse;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MessengerResponse;
import com.nativapps.arpia.model.dto.OperationResponse;
import com.nativapps.arpia.model.dto.SimpleMessengerResponse;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.DateUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0.1
 */
public class BaseServiceImpl extends GenericService implements BaseService,
        DtoConverter<Base, BaseRequest, BaseResponse> {

    private final BaseDao baseDao;

    private final MessengerDao messengerDao;

    private final OperationDao operationDao;

    private final CashDao cashDao;

    private final SystemConfig systemConfig;

    public BaseServiceImpl(BaseDao baseDao, MessengerDao messengerDao,
            OperationDao operationDao, CashDao cashDao, SystemConfig systemConfig) {
        this.baseDao = baseDao;
        this.messengerDao = messengerDao;
        this.operationDao = operationDao;
        this.cashDao = cashDao;
        this.systemConfig = systemConfig;
    }

    @Override
    public ListResponse<BaseResponse> getAll(int start, int size, Long messengerId,
            Boolean debt) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<BaseResponse> response = new ArrayList<>();
        for (Base base : baseDao.findAll(start, size, messengerId, debt))
            response.add(convertToDto(base));

        return new ListResponse<>(baseDao.findAllCount(messengerId, debt),
                response);
    }

    @Override
    public BaseResponse get(Long baseId) {
        if (baseId == null || baseId <= 0)
            throw new BadRequestException(config.getString("base.id_required"));

        Base base = baseDao.find(baseId);
        if (base == null) {
            final String FORMAT = config.getString("base.not_found");
            throw new NotFoundException(String.format(FORMAT, baseId));
        }

        return convertToDto(base);
    }

    @Override
    public BaseResponse add(Long baseId, BaseRequest data, UserInfo userInfo) {
        if (data == null)
            throw new BadRequestException(config.getString("base.is_null"));
        else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData errors = new ErrorMessageData(statusCode);

            if (data.getMessenger() == null || data.getMessenger() <= 0)
                errors.addMessage(config.getString("messenger.id_required"));
            if (data.getOperation() == null || data.getOperation() <= 0)
                errors.addMessage(config.getString("operation.id_required"));
            if (data.getCount() == null || data.getCount() == 0)
                errors.addMessage(config.getString("base.count_required"));

            if (!errors.getMessages().isEmpty())
                throw new ServiceException(errors);
        }

        Messenger messenger = messengerDao.find(data.getMessenger());
        if (messenger == null) {
            final String FORMAT = config.getString("messenger.not_found");
            throw new BadRequestException(String.format(FORMAT, data.getMessenger()));
        }

        Operation operation = operationDao.find(data.getOperation());
        if (operation == null) {
            final String FORMAT = config.getString("operation.not_found");
            throw new BadRequestException(String.format(FORMAT, data.getOperation()));
        }

        Base base = baseDao.findLastRegister(data.getMessenger());

        Calendar registerDate = Calendar.getInstance();
        User user = new User(userInfo.getId());
        BaseRecord.Concept concept = data.getCount() > 0
                ? BaseRecord.Concept.DELIVERY : BaseRecord.Concept.RETURN;
        BaseRecord record = new BaseRecord(user, concept, Math.abs(data
                .getCount()), registerDate, data.getObservations());

        if (baseId != null) {
            base = baseDao.find(baseId);
            if (base == null)
                throw new BadRequestException(String.format(config
                        .getString("base.not_found"), baseId));
            if (record.getConcept() == BaseRecord.Concept.DELIVERY) {
                base.setBaseDelivery(base.getBaseDelivery() + record.getCount());
                base.addRecord(record);
            } else if (base.getBaseReturn() + record.getCount() <= base
                    .getBaseDelivery()) {
                base.setBaseReturn(base.getBaseReturn() + record.getCount());
                base.addRecord(record);
            } else
                throw new BadRequestException(config
                        .getString("base.error_count"));
        } else if (base != null && base.getAmount() == systemConfig.getAmount()
                && DateUtil.isToday(base.getRegisterDate())) {
            if (record.getConcept() == BaseRecord.Concept.DELIVERY) {
                base.setBaseDelivery(base.getBaseDelivery() + record.getCount());
                base.addRecord(record);
            } else if (base.getBaseReturn() + record.getCount() <= base
                    .getBaseDelivery()) {
                base.setBaseReturn(base.getBaseReturn() + record.getCount());
                base.addRecord(record);
            } else
                throw new BadRequestException(config
                        .getString("base.error_count"));
        } else {
            base = new Base();
            if (record.getConcept() == BaseRecord.Concept.RETURN)
                throw new BadRequestException(config
                        .getString("base.error_concept"));
            base.setAmount(systemConfig.getAmount());
            base.setBaseDelivery(record.getCount());
            base.setMessenger(messenger);
            base.setOperation(operation);
            base.addRecord(record);
            base.setRegisterDate(registerDate);
        }

        addCashRegister(base, record, operation, user);

        return convertToDto(baseDao.save(base));
    }

    @Override
    public BaseResponse convertToDto(Base entity) {
        BaseResponse response = new BaseResponse();

        response.setId(entity.getId());
        
        Messenger m = entity.getMessenger();
        response.setMessenger(new MessengerResponse(m.getId(),
                m.getIdentification(), m.getName(), m.getLastName(),
                m.getGender(), m.getBirthday()));
        
        Operation o = entity.getOperation();
        response.setOperation(new OperationResponse(o.getId(), o.getName()));
        
        response.setBaseDelivery(entity.getBaseDelivery());
        response.setBaseReturn(entity.getBaseReturn());
        response.setAmount(entity.getAmount());
        response.setRegisterDate(entity.getRegisterDate());

        for (BaseRecord record : entity.getRecords()) {
            response.getRecords().add(new BaseRecordData(record.getUser()
                    .getId(), record.getUser().getDisplayName(),
                    record.getConcept(), record.getCount(), record
                    .getRegisterDate(), record.getObservations()));
        }

        return response;
    }

    @Override
    public Base convertToEntity(BaseRequest data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addCashRegister(Base base, BaseRecord record, Operation operation,
            User user) {
        Cash cash = new Cash();
        cash.setAmount(record.getCount() * base.getAmount());
        cash.setUser(user);
        cash.setOperation(operation);
        cash.setRegisterDate(Calendar.getInstance());
        cash.setRegisterType(record.getConcept()
                == BaseRecord.Concept.DELIVERY ? Cash.Type.OUT : Cash.Type.IN);
        String reason = cash.getRegisterType() == Cash.Type.IN
                ? "base.reason_in" : "base.reason_out";
        cash.setReason(String.format(config.getString(reason), record.getCount(),
                String.valueOf(base.getAmount()), record.getBase().getMessenger()
                .getId()));

        Cash last = cashDao.lastRegister();

        if (last != null) {
            if (cash.getAmount() > last.getTotalCash()
                    && cash.getRegisterType() == Cash.Type.OUT)
                throw new BadRequestException(config.getString("cash.no_money"));
            else if (cash.getRegisterType() == Cash.Type.IN)
                cash.setTotalCash(last.getTotalCash() + cash.getAmount());
            else if (cash.getAmount() <= last.getTotalCash())
                cash.setTotalCash(last.getTotalCash() - cash.getAmount());
        } else if (cash.getRegisterType() == Cash.Type.IN)
            cash.setTotalCash(cash.getAmount());
        else
            throw new BadRequestException(config.getString("cash.no_money"));

        cashDao.save(cash);
    }
}
