package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.CashTallyingDao;
import com.nativapps.arpia.database.dao.DailyOperationDao;
import com.nativapps.arpia.database.dao.OperationDao;
import com.nativapps.arpia.database.entity.CashRegister;
import com.nativapps.arpia.database.entity.CashTallying;
import com.nativapps.arpia.database.entity.DailyOperation;
import com.nativapps.arpia.database.entity.Operation;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.dto.DailyOperationResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.OperationResponse;
import com.nativapps.arpia.model.dto.UserResponse;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.service.converter.DtoConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0.1
 */
public class DailyOperationServiceImpl extends GenericService implements DailyOperationService,
        DtoConverter<DailyOperation, DailyOperationResponse, DailyOperationResponse> {

    private final DailyOperationDao dao;

    private final CashTallyingDao cashTallyingDao;

    private final OperationDao operationDao;

    public DailyOperationServiceImpl(DailyOperationDao dao,
            CashTallyingDao cashTallyingDao, OperationDao operationDao) {
        this.dao = dao;
        this.cashTallyingDao = cashTallyingDao;
        this.operationDao = operationDao;
    }

    @Override
    public ListResponse<DailyOperationResponse> getAll(int start, int size,
            Long operationId) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<DailyOperationResponse> response = new ArrayList<>();
        for (DailyOperation dailyOperation : dao.findAll(start, size, operationId)) {
            response.add(convertToDto(dailyOperation));
        }

        return new ListResponse<>(dao.findAllCount(operationId), response);
    }

    @Override
    public DailyOperationResponse get(Long operationId, Long id) {
        getOperationEntity(operationId);

        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("daily_op.id_required"));

        DailyOperation entity = dao.find(operationId, id);
        if (entity == null)
            throw new NotFoundException(String.format(config
                    .getString("daily_op.not_found"), id));

        return convertToDto(entity);
    }

    @Override
    public DailyOperationResponse open(Long operationId, UserInfo userInfo) {
        Operation operation = getOperationEntity(operationId);

        DailyOperation last = dao.last(operationId);
        if (last != null && last.getClosingDate() == null)
            throw new BadRequestException(String.format(config
                    .getString("daily_op.not_closing_day"), last.getId()));

        DailyOperation dailyOp = new DailyOperation(Calendar.getInstance());
        dailyOp.setOperation(operation);
        dailyOp.setUserOpening(new User(userInfo.getId()));

        return convertToDto(dao.save(dailyOp));
    }

    @Override
    public DailyOperationResponse close(Long operationId, UserInfo userInfo) {
        getOperationEntity(operationId);

        DailyOperation last = dao.last(operationId);
        if (last == null)
            throw new BadRequestException(config
                    .getString("daily_op.not_record"));
        else if (last.getClosingDate() != null)
            throw new BadRequestException(String.format(config
                    .getString("daily_op.closing_day"), last.getId()));

        last.setClosingDate(Calendar.getInstance());
        List<CashTallying> tallying = cashTallyingDao.findAll(0, 0,
                last.getOpeningDate(), last.getClosingDate());

        for (CashTallying cashT : tallying) {
            last.setProduced(last.getProduced() + cashT.getSettlement().getProduced());
            last.setCredits(last.getCredits() + cashT.getSettlement().getCredits());
            last.setAdvances(last.getAdvances() + cashT.getSettlement().getAdvances());
            last.setEgress(last.getEgress() + cashT.getSettlement().getExpenses());

            for (CashRegister cashR : cashT.getCash()) {
                last.setCashCounted(last.getCashCounted()
                        + cashR.getCount() * cashR.getValue());
            }
        }

        last.setCashExpected(last.getProduced() - last.getCredits()
                - last.getAdvances() - last.getEgress());

        last.setUserClosing(new User(userInfo.getId()));
        last.setInbalance(last.getCashCounted() - last.getCashExpected());

        return convertToDto(dao.save(last));
    }

    @Override
    public DailyOperation convertToEntity(DailyOperationResponse data) {
        throw new UnsupportedOperationException("Not supported yet.");
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
    public DailyOperationResponse convertToDto(DailyOperation entity) {
        DailyOperationResponse response = new DailyOperationResponse(entity.getId(),
                entity.getOpeningDate(), entity.getClosingDate(),
                entity.getProduced(), entity.getAdvances(), entity.getCredits(),
                entity.getEgress(), entity.getCashExpected(), entity.getCashCounted());

        response.setOperation(new OperationResponse(entity.getOperation()
                .getId(), entity.getOperation().getName()));
        response.setUserOpening(convertToDtoUser(entity.getUserOpening()));
        if (entity.getUserClosing() != null)
            response.setUserClosing(convertToDtoUser(entity.getUserClosing()));

        return response;
    }

    private Operation getOperationEntity(Long operationId) {
        if (operationId == null || operationId <= 0)
            throw new BadRequestException(config
                    .getString("operation.id_required"));

        Operation operation = operationDao.find(operationId);
        if (operation == null)
            throw new NotFoundException(String.format(config
                    .getString("operation.not_found"), operationId));

        return operation;
    }
}
