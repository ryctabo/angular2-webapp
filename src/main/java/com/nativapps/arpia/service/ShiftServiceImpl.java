package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.dao.ShiftAssignmentDao;
import com.nativapps.arpia.database.dao.ShiftDao;
import com.nativapps.arpia.database.dao.ShiftplanningDao;
import com.nativapps.arpia.database.entity.Shift;
import com.nativapps.arpia.database.entity.Shiftplanning;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ShiftRequest;
import com.nativapps.arpia.model.dto.ShiftResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.ShiftValidator;
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
public class ShiftServiceImpl extends GenericService implements ShiftService, 
        DtoConverter<Shift, ShiftRequest, ShiftResponse>{

    private final ShiftDao shiftDao;
    
    private final ShiftplanningDao shiftplanningDao;
    
    private final MessengerDao messengerDao;
    
    private final ShiftAssignmentDao saDao;

    public ShiftServiceImpl(ShiftDao shiftDao, ShiftplanningDao shiftplanningDao, 
            MessengerDao messengerDao, ShiftAssignmentDao saDao) {
        this.shiftDao = shiftDao;
        this.shiftplanningDao = shiftplanningDao;
        this.messengerDao = messengerDao;
        this.saDao = saDao;
    }
    
    @Override
    public List<ShiftResponse> getAll(Long shiftplanningId) {
        existShiftplanning(shiftplanningId);
        
        List<ShiftResponse> response = new ArrayList<>();
        for (Shift shift : shiftDao.findAll(shiftplanningId)) {
            response.add(convertToDto(shift));
        }
        
        return response;
    }

    @Override
    public ShiftResponse get(Long shiftplanningId, Integer index) {
        return convertToDto(getEntity(shiftplanningId, index));
    }

    @Override
    public ShiftResponse add(Long shiftplanningId, ShiftRequest request) {
        Shiftplanning shiftplanning = existShiftplanning(shiftplanningId);
        ErrorMessageData emd = new ErrorMessageData(Response.Status.BAD_REQUEST
                .getStatusCode());
        ShiftValidator.evaluateShift(request, shiftplanning.getInitialDate(), 
                shiftplanning.getFinalDate(), emd, config);
        
        if (!emd.getMessages().isEmpty())
            throw new ServiceException(emd);
        
        Shift shift = convertToEntity(request);
        shift.getId().setIndex(shiftDao.count(shiftplanningId)+1);
        shift.setShiftplanning(shiftplanning);
        
        return convertToDto(shiftDao.save(shift));
    }

    @Override
    public ShiftResponse update(Long shiftplanningId, Integer index, ShiftRequest request) {
        Shift entity = getEntity(shiftplanningId, index);
        ErrorMessageData emd = new ErrorMessageData(Response.Status.BAD_REQUEST
                .getStatusCode());
        ShiftValidator.evaluateShift(request, entity.getShiftplanning().getInitialDate(),
                entity.getShiftplanning().getFinalDate(), emd, config);
        
        if (!emd.getMessages().isEmpty())
            throw new ServiceException(emd);
        
        entity.setStartTime(request.getStartTime());
        entity.setEndTime(request.getEndTime());
        entity.setCount(request.getCount());
        
        return convertToDto(shiftDao.save(entity));
    }

    @Override
    public ShiftResponse delete(Long shiftplanningId, Integer index) {
        ShiftResponse response = get(shiftplanningId, index);
        shiftDao.delete(new Shift.ShiftPK(index, shiftplanningId));
        return response;
    }

    @Override
    public Shift convertToEntity(ShiftRequest data) {
        Shift shift = new Shift();
        shift.setStartTime(data.getStartTime());
        shift.setEndTime(data.getEndTime());
        shift.setCount(data.getCount());
        
        return shift;
    }

    @Override
    public ShiftResponse convertToDto(Shift entity) {
        ShiftResponse response = new ShiftResponse();
        response.setIndex(entity.getId().getIndex());
        response.setShiftplanningId(entity.getId().getShiftplanningId());
        response.setStartTime(entity.getStartTime());
        response.setEndTime(entity.getEndTime());
        response.setCount(entity.getCount());
        
        return response;
    }

    /**
     * Validate and return a shift entity
     * 
     * @param shiftplanningId shiftplanning ID
     * @param shiftId shift ID
     * @return searched shift entity
     */
    private Shift getEntity(Long shiftplanningId, Integer index) {
        existShiftplanning(shiftplanningId);
        if (index == null || index <= 0)
            throw new BadRequestException(config
                    .getString("shift.index_required"));
        
        Shift entity = shiftDao.find(new Shift.ShiftPK(index, shiftplanningId));
        if (entity == null) {
            String err = String.format(config.getString("shift.not_found"), 
                    shiftplanningId, index);
            throw new NotFoundException(err);
        }
        
        return entity;
    }
    
    private Shiftplanning existShiftplanning(Long shiftplanningId) {
        if (shiftplanningId == null || shiftplanningId <= 0)
            throw new BadRequestException(config
                    .getString("shiftplanning.id_required"));
        
        Shiftplanning planning = shiftplanningDao.find(shiftplanningId);
        if (shiftplanningDao.find(shiftplanningId) == null)
            throw new NotFoundException(config
                    .getString("shiftplanning.not_found"));
        
        return planning;
    }
}
