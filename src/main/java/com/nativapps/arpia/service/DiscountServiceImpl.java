package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.DiscountDao;
import com.nativapps.arpia.database.entity.Discount;
import com.nativapps.arpia.database.entity.DiscountChangeLog;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.DiscountRequest;
import com.nativapps.arpia.model.dto.DiscountResponse;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.TextUtil;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public class DiscountServiceImpl extends GenericService implements DiscountService,
        DtoConverter<Discount, DiscountRequest, DiscountResponse> {

    private final DiscountDao controller;

    public DiscountServiceImpl(DiscountDao controller) {
        this.controller = controller;
    }

    /**
     * Get discount entity from the given ID.
     *
     * @param id discount ID
     * @return discount data
     * @throws BadRequestException if the discount ID is null or less than or equal to 0.
     * @throws NotFoundException   if the discount data obtained is null.
     */
    private Discount getEntity(Long id) throws NotFoundException,
            BadRequestException {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("discount.id"));
        Discount discount = controller.find(id);
        if (discount == null) {
            final String FORMAT = config.getString("discount.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return discount;
    }

    /**
     * Validate if discount data comply with all requirements.
     *
     * @param request  discount data
     * @param userInfo user info
     * @throws BadRequestException          if the discount data is null
     * @throws ServiceException             if the discount data don't have any attribute required
     * @throws InternalServerErrorException if the user information is null
     */
    private void validate(DiscountRequest request, UserInfo userInfo) throws ServiceException,
            BadRequestException, InternalServerErrorException {
        if (userInfo == null)
            throw new InternalServerErrorException(config.getString("user.info"));

        if (request == null) {
            throw new BadRequestException(config.getString("discount.is_null"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);

            if (TextUtil.isEmpty(request.getName()))
                emd.addMessage(config.getString("discount.name"));
            if (request.getStartDate() == null)
                emd.addMessage(config.getString("discount.start_date"));
            if (request.getEndDate() == null)
                emd.addMessage(config.getString("discount.end_date"));
            if (request.getPercent() == null || request.getPercent() <= 0f)
                emd.addMessage(config.getString("discount.percent"));

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }
    }

    @Override
    public ListResponse<DiscountResponse> get(String name, Boolean active, int start, int size,
                                              String orderBy, OrderType type) {
        List<DiscountResponse> discounts = new ArrayList<>();

        for (Discount discount : controller.findAll(name, active, start, size, orderBy, type))
            discounts.add(convertToDto(discount));

        long count = controller.getCount(name, active);
        return new ListResponse<>(count, discounts);
    }

    @Override
    public DiscountResponse get(Long id) {
        Discount discount = getEntity(id);
        return convertToDto(discount);
    }

    @Override
    public DiscountResponse add(DiscountRequest request, UserInfo userInfo) {
        validate(request, userInfo);

        Discount discount = convertToEntity(request);

        DiscountChangeLog changeLog = new DiscountChangeLog();
        changeLog.setUser(new User(userInfo.getId()));
        changeLog.setDiscount(discount);
        changeLog.setRegisterDate(Calendar.getInstance());
        changeLog.getId().setIndex(1);

        discount.getChanges().add(changeLog);

        return convertToDto(controller.save(discount));
    }

    @Override
    public DiscountResponse update(Long id, DiscountRequest request, UserInfo userInfo) {
        Discount discount = getEntity(id);
        validate(request, userInfo);

        discount.setName(request.getName());
        discount.setStartDate(request.getStartDate());
        discount.setEndDate(request.getEndDate());
        discount.setPercent(request.getPercent());
        discount.setUseLimit(request.getUseLimit());

        DiscountChangeLog changeLog = new DiscountChangeLog();
        changeLog.getId().setIndex(controller.getCountChanges(id) + 1);
        changeLog.setUser(new User(userInfo.getId()));
        changeLog.setDiscount(discount);
        changeLog.setRegisterDate(Calendar.getInstance());

        return convertToDto(controller.saveDiscountAndLog(discount, changeLog));
    }

    @Override
    public DiscountResponse delete(Long id) {
        DiscountResponse discount = get(id);
        controller.delete(id);
        return discount;
    }

    @Override
    public Discount convertToEntity(DiscountRequest data) {
        return new Discount(data.getName(), data.getStartDate(), data.getEndDate(),
                data.getPercent(), data.getUseLimit());
    }

    @Override
    public DiscountResponse convertToDto(Discount entity) {
        DiscountResponse discount = new DiscountResponse();

        discount.setId(entity.getId());
        discount.setName(entity.getName());
        discount.setStartDate(entity.getStartDate());
        discount.setEndDate(entity.getEndDate());
        discount.setPercent(entity.getPercent());
        discount.setUseLimit(entity.getUseLimit());
        discount.setCountUsed(controller.getCountUsed(entity.getId()));

        return discount;
    }

}
