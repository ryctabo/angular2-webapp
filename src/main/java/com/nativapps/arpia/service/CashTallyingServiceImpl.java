package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.CashTallyingDao;
import com.nativapps.arpia.database.entity.CashRegister;
import com.nativapps.arpia.database.entity.CashTallying;
import com.nativapps.arpia.database.entity.Settlement;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.Denomination;
import com.nativapps.arpia.model.SystemConfig;
import com.nativapps.arpia.model.dto.CashRegisterRequest;
import com.nativapps.arpia.model.dto.CashRegisterResponse;
import com.nativapps.arpia.model.dto.CashTallyingRequest;
import com.nativapps.arpia.model.dto.CashTallyingResponse;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.SettlementResponse;
import com.nativapps.arpia.model.dto.UserResponse;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import java.text.NumberFormat;
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
public class CashTallyingServiceImpl extends GenericService implements CashTallyingService,
        DtoConverter<CashTallying, CashTallyingRequest, CashTallyingResponse> {

    private final CashTallyingDao dao;

    private final SystemConfig systemConfig;

    public CashTallyingServiceImpl(CashTallyingDao dao, SystemConfig systemConfig) {
        this.dao = dao;
        this.systemConfig = systemConfig;
    }

    @Override
    public ListResponse<CashTallyingResponse> getAll(int start, int size,
            Calendar from, Calendar to) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<CashTallyingResponse> response = new ArrayList<>();
        for (CashTallying cashTallying : dao.findAll(start, size, from, to)) {
            response.add(convertToDto(cashTallying));
        }

        return new ListResponse<>(dao.findAllCount(from, to), response);
    }

    @Override
    public CashTallyingResponse get(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config
                    .getString("cash_tallying.id_required"));

        CashTallying entity = dao.find(id);
        if (entity == null)
            throw new NotFoundException(String.format(config
                    .getString("cash_tallying.not_found"), id));

        return convertToDto(entity);
    }

    @Override
    public CashTallyingResponse add(CashTallyingRequest data, UserInfo userInfo) {
        if (data == null)
            throw new BadRequestException(config
                    .getString("cash_tallying.is_null"));
        if (data.getCashRegisters() == null || data.getCashRegisters().isEmpty())
            throw new BadRequestException(config
                    .getString("cash_tallying.registers_null"));
        if (data.getSettlement() == null)
            throw new BadRequestException(config
                    .getString("cash.tallying.settlement_null"));

        ErrorMessageData errors = new ErrorMessageData(Response.Status.BAD_REQUEST
                .getStatusCode());
        if (data.getSettlement().getProduced() == null)
            errors.addMessage(config
                    .getString("cash_tallying.settlement_produced_null"));
        if (data.getSettlement().getCredits() == null)
            errors.addMessage(config
                    .getString("cash_tallying.settlement_credits_null"));
        if (data.getSettlement().getAdvances() == null)
            errors.addMessage(config
                    .getString("cash_tallying.settlement_advances_null"));
        if (data.getSettlement().getExpenses() == null)
            errors.addMessage(config
                    .getString("cash_tallying.settlement_expenses_null"));

        for (CashRegisterRequest cashRegister : data.getCashRegisters()) {
            boolean exists = false;
            for (Denomination cash : systemConfig.getDenominations()) {
                if (cashRegister.getValue() == cash.getValue()
                        && cashRegister.getCashType() == cash.getCashType())
                    exists = true;
            }
            if (!exists) {
                String message = config
                        .getString("cash_tallying.denomination_not_found");
                String value = NumberFormat.getCurrencyInstance()
                        .format(cashRegister.getValue());
                errors.addMessage(String.format(message, value));
            }
        }

        if (!errors.getMessages().isEmpty())
            throw new ServiceException(errors);

        CashTallying entity = convertToEntity(data);
        entity.setRegisterDate(Calendar.getInstance());
        entity.setUser(new User(userInfo.getId()));

        return convertToDto(dao.save(entity));
    }

    @Override
    public CashTallying convertToEntity(CashTallyingRequest data) {
        CashTallying entity = new CashTallying();

        for (CashRegisterRequest register : data.getCashRegisters()) {
            entity.addCash(new CashRegister(register.getCount(), register
                    .getValue(), register.getCashType()));
        }

        Settlement settlement = new Settlement(data.getSettlement().getProduced(),
                data.getSettlement().getCredits(), data.getSettlement().getAdvances(),
                data.getSettlement().getExpenses());
        entity.setSettlement(settlement);
        entity.setObservations(data.getObservations());

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
    public CashTallyingResponse convertToDto(CashTallying entity) {
        CashTallyingResponse response = new CashTallyingResponse();

        response.setId(entity.getId());
        response.setRegisterDate(entity.getRegisterDate());
        response.setObservations(entity.getObservations());
        response.setUser(convertToDtoUser(entity.getUser()));

        float totalPapers = 0, totalCoins = 0;
        for (CashRegister cr : entity.getCash()) {
            CashRegisterResponse crr = new CashRegisterResponse(cr.getValue(),
                    cr.getCount(), cr.getCashType());
            response.addCashRegister(crr);

            if (crr.getCashType() == Denomination.Type.PAPER)
                totalPapers += crr.getTotal();
            else
                totalCoins += crr.getTotal();
        }

        response.setSettlement(new SettlementResponse(entity.getSettlement()
                .getProduced(), entity.getSettlement().getCredits(),
                entity.getSettlement().getAdvances(), entity.getSettlement()
                .getExpenses()));
        response.setTotalPapers(totalPapers);
        response.setTotalCoins(totalCoins);
        response.setTotalCash(totalPapers + totalCoins);
        response.setImbalance(response.getTotalCash() - response.getSettlement()
                .getTotal());

        return response;
    }

}
