package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.SpecialDayDao;
import com.nativapps.arpia.database.entity.SpecialDay;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.SpecialDayRequest;
import com.nativapps.arpia.model.dto.SpecialDayResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.SpecialDayValidator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

public class SpecialDayServiceImpl extends GenericService
        implements SpecialDayService,
        DtoConverter<SpecialDay, SpecialDayRequest, SpecialDayResponse> {

    private final SpecialDayDao controller
            = EntityControllerFactory.getSpecialDayController();

    /**
     * Get special day entity by id provided.
     *
     * @param id special day ID
     * @return a special day data
     *
     * @throws BadRequestException if the special day ID is null or less than or
     * equal to 0.
     * @throws NotFoundException if the special day obtained is null.
     */
    private SpecialDay getEntity(Long id) throws BadRequestException,
            NotFoundException {
        if (id == null || id <= 0) {
            throw new BadRequestException(config.getString("specialDay.id"));
        }

        SpecialDay specialDay = controller.find(id);
        if (specialDay == null) {
            String msg = String.format(config.getString("specialDay.not_found"), id);
            throw new NotFoundException(msg);
        }
        return specialDay;
    }

    /**
     * Converts the integer day index to text string.
     *
     * @param dayNumber Days of week are indexed starting at 1
     * @return day name
     */
    private String parserDayName(int dayNumber) {
        switch (dayNumber) {
            case 1:
                return config.getString("day.SUNDAY");
            case 2:
                return config.getString("day.MONDAY");
            case 3:
                return config.getString("day.TUESDAY");
            case 4:
                return config.getString("day.WEDNESDAY");
            case 5:
                return config.getString("day.THURSDAY");
            case 6:
                return config.getString("day.FRIDAY");
            case 7:
                return config.getString("day.SATURDAY");
        }
        return null;
    }

    @Override
    public SpecialDayResponse add(SpecialDayRequest dataRequest) {
        if (dataRequest == null)
            throw new BadRequestException(config.getString("specialDay.required"));

        ErrorMessageData emd = new ErrorMessageData();
        SpecialDayValidator.evaluateCreation(dataRequest, emd, config);
        //verify if exists errors
        if (!emd.getMessages().isEmpty()) {
            emd.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
            throw new ServiceException(emd);
        }

        SpecialDay specialDay = convertToEntity(dataRequest);
        specialDay = controller.save(specialDay);

        SpecialDayResponse response = convertToDto(specialDay);
        int indexDay = specialDay.getDate().get(Calendar.DAY_OF_WEEK);
        response.setDay(parserDayName(indexDay));
        return response;
    }

    @Override
    public SpecialDayResponse get(Long id) {
        SpecialDayResponse response = convertToDto(getEntity(id));
        int indexDay = response.getDate().get(Calendar.DAY_OF_WEEK);
        response.setDay(parserDayName(indexDay));
        return response;
    }

    @Override
    public List<SpecialDayResponse> getAll() {
        List<SpecialDayResponse> responses = new ArrayList();
        for (SpecialDay specialDay : controller.findAll()) {
            SpecialDayResponse response = convertToDto(specialDay);
            int indexDay = response.getDate().get(Calendar.DAY_OF_WEEK);
            response.setDay(parserDayName(indexDay));
            responses.add(response);
        }
        return responses;
    }

    @Override
    public SpecialDayResponse update(Long id, SpecialDayRequest dataRequest) {
        SpecialDay specialDay = getEntity(id);
        if (dataRequest == null)
            throw new BadRequestException(config.getString("specialDay.required"));

        ErrorMessageData emd = new ErrorMessageData();
        SpecialDayValidator.evaluateCreation(dataRequest, emd, config);
        //verify if exists errors
        if (!emd.getMessages().isEmpty()) {
            emd.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
            throw new ServiceException(emd);
        }

        specialDay.setDate(dataRequest.getDate());
        specialDay.setTypeDay(dataRequest.getTypeDay());
        specialDay = controller.save(specialDay);
        SpecialDayResponse response = convertToDto(specialDay);
        int indexDay = specialDay.getDate().get(Calendar.DAY_OF_WEEK);
        response.setDay(parserDayName(indexDay));
        return response;
    }

    @Override
    public SpecialDayResponse delete(Long id) {
        SpecialDayResponse response = convertToDto(getEntity(id));
        int indexDay = response.getDate().get(Calendar.DAY_OF_WEEK);
        response.setDay(parserDayName(indexDay));

        controller.delete(id);
        return response;
    }

    @Override
    public SpecialDay convertToEntity(SpecialDayRequest data) {
        return new SpecialDay(data.getDate(), data.getTypeDay());
    }

    @Override
    public SpecialDayResponse convertToDto(SpecialDay entity) {
        return new SpecialDayResponse(entity.getId(), null,
                entity.getDate(), entity.getTypeDay());
    }

}
