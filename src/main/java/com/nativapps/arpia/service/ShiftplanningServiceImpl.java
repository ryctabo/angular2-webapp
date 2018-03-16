package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.ShiftplanningDao;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.MessengerPlanning;
import com.nativapps.arpia.database.entity.Operation;
import com.nativapps.arpia.database.entity.Shift;
import com.nativapps.arpia.database.entity.Shiftplanning;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MessengerPlanningResponse;
import com.nativapps.arpia.model.dto.MessengerResponse;
import com.nativapps.arpia.model.dto.OperationResponse;
import com.nativapps.arpia.model.dto.ShiftRequest;
import com.nativapps.arpia.model.dto.ShiftResponse;
import com.nativapps.arpia.model.dto.ShiftplanningRequest;
import com.nativapps.arpia.model.dto.ShiftplanningResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.ShiftplanningValidator;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftplanningServiceImpl extends GenericService implements 
        ShiftplanningService, DtoConverter<Shiftplanning, ShiftplanningRequest, 
        ShiftplanningResponse> {
    
    private final ShiftplanningDao dao;

    public ShiftplanningServiceImpl(ShiftplanningDao dao) {
        this.dao = dao;
    }

    @Override
    public ListResponse<ShiftplanningResponse> getAll(int start, int size, 
            Long operationId) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));
        
        List<ShiftplanningResponse> response = new ArrayList<>();
        for (Shiftplanning shiftplanning : dao.findAll(start, size, operationId)) {
            response.add(convertToDto(shiftplanning));
        }
        
        return new ListResponse<>(dao.findAllCount(operationId), response);
    }

    @Override
    public ShiftplanningResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public ShiftplanningResponse add(ShiftplanningRequest request) {
        ErrorMessageData emd = new ErrorMessageData(Response.Status.BAD_REQUEST
                .getStatusCode());
        ShiftplanningValidator.evaluateShiftplanning(request, emd, config);
        
        if (!emd.getMessages().isEmpty())
            throw new ServiceException(emd);
        
        return convertToDto(dao.save(convertToEntity(request)));
    }

    @Override
    public ShiftplanningResponse delete(Long id) {
        ShiftplanningResponse response = get(id);
        dao.delete(id);
        return response;
    }

    @Override
    public Shiftplanning convertToEntity(ShiftplanningRequest data) {
        Shiftplanning entity = new Shiftplanning();
        
        entity.setInitialDate(data.getInitialDate());
        entity.setFinalDate(data.getFinalDate());
        entity.setOperation(new Operation(data.getOperationId()));
        
        for (int i = 0; i < data.getShifts().size(); i++) {
            ShiftRequest shift = data.getShifts().get(i);
            Shift shiftE = new Shift();
            shiftE.getId().setIndex(i+1);
            shiftE.setStartTime(shift.getStartTime());
            shiftE.setEndTime(shift.getEndTime());
            shiftE.setCount(shift.getCount());
            
            entity.addShift(shiftE);
        }
        
        return entity;
    }

    @Override
    public ShiftplanningResponse convertToDto(Shiftplanning entity) {
        ShiftplanningResponse response = new ShiftplanningResponse();
        
        response.setId(entity.getId());
        response.setInitialDate(entity.getInitialDate());
        response.setFinalDate(entity.getFinalDate());
        response.setOperation(new OperationResponse(entity.getOperation()
                .getId(), entity.getOperation().getName()));
        
        for (Shift shift : entity.getShifts()) {
            ShiftResponse shiftR = new ShiftResponse();
            
            shiftR.setIndex(shift.getId().getIndex());
            shiftR.setShiftplanningId(shift.getId().getShiftplanningId());
            shiftR.setStartTime(shift.getStartTime());
            shiftR.setEndTime(shift.getEndTime());
            shiftR.setCount(shift.getCount());
            
            response.getShifts().add(shiftR);
        }
        
        return response;
    }

    /**
     * Allows to get a validate shiftplanning entity
     * 
     * @param id Shiftplanning ID
     * @return 
     */
    private Shiftplanning getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config
                    .getString("shiftplanning.id_required"));
        
        Shiftplanning entity = dao.find(id);
        if (entity == null) {
            String error = String.format(config
                    .getString("shiftplanning.not_found"), id);
            throw new NotFoundException(error);
        }
        
        return entity;
    }

    @Override
    public ListResponse<MessengerPlanningResponse> getMessengerPlanning(int start, int size, 
            Long shiftplanningId, Boolean assign) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));
        
        getEntity(shiftplanningId);
        
        List<MessengerPlanning> result = dao.getMessengerPlanning(start, size, 
                shiftplanningId, assign);
        
        List<MessengerPlanningResponse> response = new ArrayList<>();
        for (MessengerPlanning mp : result) {
            Messenger m = mp.getMessenger();
            Shift s = mp.getShift();
            
            MessengerResponse messenger = new MessengerResponse(m.getId(), 
                    m.getIdentification(), m.getName(), m.getLastName(), 
                    m.getGender(), m.getBirthday());
            
            ShiftResponse shift = new ShiftResponse(s.getId().getIndex(), 
                    s.getId().getShiftplanningId(), s.getStartTime(), 
                    s.getEndTime(), s.getCount());
            
            response.add(new MessengerPlanningResponse(messenger, shift));
        }
        
        return new ListResponse<>(dao.getMessengerPlanningCount(shiftplanningId, 
                assign), response);
    }

}
