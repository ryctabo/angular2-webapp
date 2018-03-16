package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.DebtDao;
import com.nativapps.arpia.database.entity.Closure;
import com.nativapps.arpia.database.entity.Debt;
import com.nativapps.arpia.database.entity.FeeInfo;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.ClosureData;
import com.nativapps.arpia.model.dto.DebtRequest;
import com.nativapps.arpia.model.dto.DebtResponse;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.FeeInfoData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.service.converter.UserSimpleConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.DebtValidator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class DebtServiceImpl extends GenericService implements DebtService,
        DtoConverter<Debt, DebtRequest, DebtResponse> {

    private final DebtDao controller;

    public DebtServiceImpl(DebtDao controller) {
        this.controller = controller;
    }

    @Override
    public ListResponse<DebtResponse> get(String concept, Calendar from,
            Calendar to, int start, int size, String orderBy, OrderType type,
            Boolean closureState) {
        List<DebtResponse> items = new ArrayList<>();
        List<Debt> debts = controller.findAll(concept, from, to, start, size,
                orderBy, type, closureState);

        for (Debt debt : debts) {
            items.add(convertToDto(debt));
        }

        long count = controller.getCount(concept, from, to, closureState);

        return new ListResponse<>(count, items);
    }

    @Override
    public DebtResponse get(Long id) {
        Debt debt = getEntity(id);
        return convertToDto(debt);
    }

    @Override
    public DebtResponse add(DebtRequest request, UserInfo userInfo) {
        DebtValidator.validate(request, userInfo, config);
        Debt newDebt = convertToEntity(request);
        return convertToDto(controller.save(newDebt));
    }

    @Override
    public DebtResponse update(Long id, DebtRequest request, UserInfo userInfo) {
        Debt debt = getEntity(id);
        DebtValidator.validate(request, userInfo, config);

        FeeInfo fee = new FeeInfo();
        fee.setFirstPayment(request.getFeeInfo().getFirstPayment());
        fee.setNumberOfFees(request.getFeeInfo().getNumberOfFees());
        fee.setPeriod(request.getFeeInfo().getPeriod());

        debt.setConcept(request.getConcept());
        debt.setFeeInfo(fee);
        debt.setOwner(new User(request.getOwnerId()));
        debt.setValue(request.getValue());
        return convertToDto(controller.save(debt));

    }

    @Override
    public DebtResponse updateDebtClosure(Long id, ClosureData closureData,
            UserInfo userInfo) {
        Debt debt = getEntity(id);

        if (userInfo == null)
            throw new InternalServerErrorException(config
                    .getString("debt.closure_user"));

        int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        ErrorMessageData emd = new ErrorMessageData(statusCode);

        if (closureData.getObservation() == null)
            emd.addMessage(config.getString("debt.closure_observation"));
        if (closureData.getType() == null)
            emd.addMessage(config.getString("debt.closure_type"));

        if (!emd.getMessages().isEmpty())
            throw new ServiceException(emd);

        Closure closure = new Closure();

        closure.setClosuredBy(new User(userInfo.getId()));
        closure.setDate(Calendar.getInstance());
        closure.setObservation(closureData.getObservation());
        closure.setType(closureData.getType());

        debt.setClosure(closure);

        return convertToDto(controller.save(debt));
    }

    @Override
    public Debt convertToEntity(DebtRequest data) {
        Debt debt = new Debt();

        FeeInfo fee = new FeeInfo();
        fee.setFirstPayment(data.getFeeInfo().getFirstPayment());
        fee.setNumberOfFees(data.getFeeInfo().getNumberOfFees());
        fee.setPeriod(data.getFeeInfo().getPeriod());

        debt.setFeeInfo(fee);
        debt.setConcept(data.getConcept());
        debt.setValue(data.getValue());
        debt.setOwner(new User(data.getOwnerId()));
        debt.setCreated(Calendar.getInstance());

        return debt;
    }

    @Override
    public DebtResponse convertToDto(Debt entity) {
        DebtResponse data = new DebtResponse();

        FeeInfoData fee = new FeeInfoData();
        fee.setId(entity.getFeeInfo().getId());
        fee.setFirstPayment(entity.getFeeInfo().getFirstPayment());
        fee.setNumberOfFees(entity.getFeeInfo().getNumberOfFees());
        fee.setPeriod(entity.getFeeInfo().getPeriod());

        if (entity.getClosure() != null) {
            ClosureData cdata = new ClosureData();
            cdata.setClosuredBy(UserSimpleConverter.instance()
                    .convertToDto(entity.getClosure().getClosuredBy()));
            cdata.setDate(entity.getClosure().getDate());
            cdata.setObservation(entity.getClosure().getObservation());
            cdata.setType(entity.getClosure().getType());

            data.setClosure(cdata);
        }

        data.setId(entity.getId());
        data.setValue(entity.getValue());
        data.setCreated(entity.getCreated());
        data.setConcept(entity.getConcept());
        data.setFeeInfo(fee);
        data.setOwner(UserSimpleConverter.instance().convertToDto(entity.getOwner()));

        return data;
    }

    /**
     * Get debt entity from the given ID.
     *
     * @param id debt ID
     * @return debt data
     *
     * @throws BadRequestException if the debt ID is null or less than or equal
     * to 0.
     * @throws NotFoundException if the debt data obtained is null.
     */
    private Debt getEntity(Long id) throws NotFoundException,
            BadRequestException {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("debt.id"));
        Debt debt = controller.find(id);
        if (debt == null) {
            final String FORMAT = config.getString("debt.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return debt;
    }

}
