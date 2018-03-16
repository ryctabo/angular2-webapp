package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.FestiveDayDao;
import com.nativapps.arpia.database.entity.FestiveDay;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.FestiveDayRequest;
import com.nativapps.arpia.model.dto.FestiveDayResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class FestiveDayServiceImpl extends GenericService implements FestiveDayService,
        DtoConverter<FestiveDay, FestiveDayRequest, FestiveDayResponse> {

    private final FestiveDayDao controller;

    public FestiveDayServiceImpl(FestiveDayDao controller) {
        this.controller = controller;
    }

    /**
     * Get festive day entity from the given ID.
     *
     * @param id festive day ID
     * @return domicile data
     *
     * @throws BadRequestException if the domicile Id is null or less than or
     * equal to 0.
     * @throws NotFoundException if the domicile data obtained is null.
     */
    private FestiveDay getEntity(Long id) throws BadRequestException,
            NotFoundException {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("festive.id"));
        FestiveDay festiveDay = controller.find(id);
        if (festiveDay == null) {
            final String FORMAT = config.getString("festive.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return festiveDay;
    }

    /**
     * Validate if festive date have all attributes for save to database.
     *
     * @param request festive day data
     *
     * @throws BadRequestException if the festive day data is null
     * @throws ServiceException if the festive day data don't have any attribute
     * required
     */
    private void validate(FestiveDayRequest request) throws ServiceException,
            BadRequestException {
        if (request == null) {
            throw new BadRequestException(config.getString("festive.is_null"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);

            if (TextUtil.isEmpty(request.getName()))
                emd.addMessage(config.getString("festive.name"));

            if (request.getMonth() == null)
                emd.addMessage(config.getString("festive.month"));

            Integer day = request.getDayOfMonth();
            if (day == null || day <= 0 || day > 31)
                emd.addMessage(config.getString("festive.day"));

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }
    }

    @Override
    public List<FestiveDayResponse> get() {
        List<FestiveDayResponse> festiveDays = new ArrayList<>();
        for (FestiveDay festiveDay : controller.findAll())
            festiveDays.add(convertToDto(festiveDay));
        return festiveDays;
    }

    @Override
    public FestiveDayResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public FestiveDayResponse add(FestiveDayRequest request) {
        validate(request);
        return convertToDto(controller.save(convertToEntity(request)));
    }

    @Override
    public FestiveDayResponse update(Long id, FestiveDayRequest request) {
        FestiveDay festiveDay = getEntity(id);
        validate(request);

        festiveDay.setName(request.getName());
        festiveDay.setMonth(request.getMonth());
        festiveDay.setDayOfMonth(request.getDayOfMonth());

        return convertToDto(controller.save(festiveDay));
    }

    @Override
    public FestiveDayResponse delete(Long id) {
        FestiveDayResponse response = get(id);
        controller.delete(id);
        return response;
    }

    @Override
    public FestiveDay convertToEntity(FestiveDayRequest d) {
        return new FestiveDay(d.getName(), d.getDayOfMonth(), d.getMonth());
    }

    @Override
    public FestiveDayResponse convertToDto(FestiveDay e) {
        return new FestiveDayResponse(e.getId(), e.getName(), e.getDayOfMonth(),
                e.getMonth());
    }

}
