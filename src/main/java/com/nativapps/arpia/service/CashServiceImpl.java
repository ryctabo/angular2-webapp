package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.CashDao;
import com.nativapps.arpia.database.dao.OperationDao;
import com.nativapps.arpia.database.entity.Cash;
import com.nativapps.arpia.database.entity.Operation;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.dto.CashRequest;
import com.nativapps.arpia.model.dto.CashResponse;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.OperationResponse;
import com.nativapps.arpia.model.dto.UserResponse;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
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
public class CashServiceImpl extends GenericService implements CashService,
        DtoConverter<Cash, CashRequest, CashResponse> {

    private final CashDao dao;

    private final OperationDao operationDao;

    public CashServiceImpl(CashDao dao, OperationDao operationDao) {
        this.dao = dao;
        this.operationDao = operationDao;
    }

    @Override
    public ListResponse<CashResponse> getAll(int start, int size) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<CashResponse> response = new ArrayList<>();
        for (Cash cash : dao.findAll(start, size)) {
            response.add(convertToDto(cash));
        }

        return new ListResponse<>(dao.findAllCount(), response);
    }

    @Override
    public CashResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    /**
     * Returns a validate cash register entity
     *
     * @param id Cash register ID
     * @return Seached cash register
     */
    public Cash getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("cash.id_required"));

        Cash entity = dao.find(id);
        if (entity == null)
            throw new NotFoundException(String.format(config
                    .getString("cash.not_found"), id));

        return entity;
    }

    @Override
    public CashResponse add(CashRequest data, UserInfo userInfo) {
        if (data == null)
            throw new BadRequestException("cash.is_null");
        else {
            ErrorMessageData errors = new ErrorMessageData(Response.Status.BAD_REQUEST
                    .getStatusCode());
            if (data.getAmount() == null)
                errors.addMessage(config.getString("cash.amount_required"));
            if (data.getOperation() == null)
                errors.addMessage(config.getString("cash.operation_required"));
            else if (operationDao.find(data.getOperation()) == null)
                errors.addMessage(String.format(config
                        .getString("operation.not_found"), data.getOperation()));
            if (data.getReason() == null)
                errors.addMessage(config.getString("cash.reason_required"));
            if (data.getRegisterType() == null)
                errors.addMessage(config.getString("cash.type_required"));

            if (!errors.getMessages().isEmpty())
                throw new ServiceException(errors);
        }

        Cash entity = convertToEntity(data);
        Cash last = dao.lastRegister();

        entity.setUser(new User(userInfo.getId()));
        if (last != null) {
            if (entity.getAmount() > last.getTotalCash()
                    && entity.getRegisterType() == Cash.Type.OUT)
                throw new BadRequestException(config.getString("cash.no_money"));
            else if (entity.getRegisterType() == Cash.Type.IN)
                entity.setTotalCash(last.getTotalCash() + entity.getAmount());
            else if (entity.getAmount() <= last.getTotalCash())
                entity.setTotalCash(last.getTotalCash() - entity.getAmount());
        } else if (entity.getRegisterType() == Cash.Type.IN)
            entity.setTotalCash(entity.getAmount());
        else
            throw new BadRequestException(config.getString("cash.no_money"));

        return convertToDto(dao.save(entity));
    }

    @Override
    public Cash convertToEntity(CashRequest data) {
        Cash entity = new Cash();

        entity.setAmount(data.getAmount());
        entity.setOperation(new Operation(data.getOperation()));
        entity.setReason(data.getReason());
        entity.setRegisterDate(Calendar.getInstance());
        entity.setRegisterType(data.getRegisterType());

        return entity;
    }

    /**
     * Convert from user entity to user response.
     *
     * @param user user entity
     * @return user response
     */
    private UserResponse convertToDtoUser(User user) {
        if (user == null)
            return null;

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setDisplayName(user.getDisplayName());
        response.setEmail(user.getEmail());
        response.setUrlPic(user.getUrlPic());
        response.setUsername(user.getUsername());

        return response;
    }

    @Override
    public CashResponse convertToDto(Cash entity) {
        CashResponse response = new CashResponse();

        response.setId(entity.getId());
        response.setAmount(entity.getAmount());
        response.setOperation(new OperationResponse(entity.getOperation()
                .getId(), entity.getOperation().getName()));
        response.setReason(entity.getReason());
        response.setRegisterDate(entity.getRegisterDate());
        response.setRegisterType(entity.getRegisterType());
        response.setUser(convertToDtoUser(entity.getUser()));
        response.setTotalCash(entity.getTotalCash());

        return response;
    }
}
