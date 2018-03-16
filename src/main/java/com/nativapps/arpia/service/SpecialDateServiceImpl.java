package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.SpecialDateDao;
import com.nativapps.arpia.database.entity.SpecialDate;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.SpecialDateRequest;
import com.nativapps.arpia.model.dto.SpecialDateResponse;
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
public class SpecialDateServiceImpl extends GenericService implements SpecialDateService,
        DtoConverter<SpecialDate, SpecialDateRequest, SpecialDateResponse> {

    private final SpecialDateDao controller;

    public SpecialDateServiceImpl(SpecialDateDao controller) {
        this.controller = controller;
    }

    private SpecialDate getEntity(Long id) throws BadRequestException,
            NotFoundException {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("special.id"));
        SpecialDate specialDate = controller.find(id);
        if (specialDate == null) {
            final String FORMAT = config.getString("special.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return specialDate;
    }

    private void validate(SpecialDateRequest request)
            throws BadRequestException, ServiceException {
        if (request == null) {
            throw new BadRequestException(config.getString("special.is_null"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);

            if (TextUtil.isEmpty(request.getName()))
                emd.addMessage(config.getString("special.name"));
            if (request.getDate() == null)
                emd.addMessage(config.getString("special.date"));

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }
    }

    @Override
    public List<SpecialDateResponse> get() {
        List<SpecialDateResponse> specialDates = new ArrayList<>();
        for (SpecialDate specialDate : controller.findAll())
            specialDates.add(convertToDto(specialDate));
        return specialDates;
    }

    @Override
    public SpecialDateResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public SpecialDateResponse add(SpecialDateRequest request) {
        validate(request);
        return convertToDto(controller.save(convertToEntity(request)));
    }

    @Override
    public SpecialDateResponse update(Long id, SpecialDateRequest request) {
        SpecialDate specialDate = getEntity(id);
        validate(request);

        specialDate.setName(request.getName());
        specialDate.setDate(request.getDate());

        return convertToDto(controller.save(specialDate));
    }

    @Override
    public SpecialDateResponse delete(Long id) {
        SpecialDateResponse specialDate = get(id);
        controller.delete(id);
        return specialDate;
    }

    @Override
    public SpecialDate convertToEntity(SpecialDateRequest data) {
        return new SpecialDate(data.getName(), data.getDate());
    }

    @Override
    public SpecialDateResponse convertToDto(SpecialDate e) {
        return new SpecialDateResponse(e.getId(), e.getName(), e.getDate());
    }

}
