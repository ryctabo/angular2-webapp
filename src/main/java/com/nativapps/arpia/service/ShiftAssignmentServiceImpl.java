package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.dao.ShiftAssignmentDao;
import com.nativapps.arpia.database.dao.ShiftDao;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.Shift;
import com.nativapps.arpia.database.entity.ShiftAssignment;
import com.nativapps.arpia.model.dto.ShiftAssignRequest;
import com.nativapps.arpia.model.dto.ShiftAssignResponse;
import com.nativapps.arpia.model.dto.SimpleMessengerResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftAssignmentServiceImpl extends GenericService 
        implements ShiftAssignmentService, DtoConverter<ShiftAssignment, 
        ShiftAssignRequest, ShiftAssignResponse> {
    
    private final ShiftAssignmentDao dao;
    
    private final ShiftDao shiftDao;
    
    private final MessengerDao messengerDao;

    public ShiftAssignmentServiceImpl(ShiftAssignmentDao dao, ShiftDao shiftDao,
            MessengerDao messengerDao) {
        this.dao = dao;
        this.shiftDao = shiftDao;
        this.messengerDao = messengerDao;
    }

    @Override
    public List<ShiftAssignResponse> getAll(Long shiftplanningId, Integer shiftIndex,
            Boolean clockin, Boolean clockout) {
        existsShift(shiftplanningId, shiftIndex);
        
        List<ShiftAssignResponse> response = new ArrayList<>();
        for (ShiftAssignment shiftAssignment : dao.findAll(new Shift
                .ShiftPK(shiftIndex, shiftplanningId), clockin, clockout)) {
            response.add(convertToDto(shiftAssignment));
        }
        
        return response;
    }

    @Override
    public ShiftAssignResponse assign(Long shiftplanningId, Integer shiftIndex, 
            ShiftAssignRequest request) {
        Shift shift = existsShift(shiftplanningId, shiftIndex);
        
        if (shift.getCount() <= dao.count(shift.getId()))
            throw new BadRequestException(config
                    .getString("shift_assign.max_count"));
        
        if (request == null)
            throw new BadRequestException(config
                    .getString("shift.assign_info_required"));
        else if (request.getMessengerId() == null)
            throw new BadRequestException(config
                    .getString("messenger.id_required"));
        
        Messenger messenger = messengerDao.find(request.getMessengerId());
        if (messenger == null) {
            String err = String.format(config.getString("messenger.not_found"), 
                    request.getMessengerId());
            throw new NotFoundException(err);
        }
        
        ShiftAssignment assignment = new ShiftAssignment(messenger, shift);
        
        return convertToDto(dao.save(assignment));
    }

    @Override
    public ShiftAssignResponse delete(Long shiftplanningId, Integer shiftIndex, 
            Long messengerId) {
        if (shiftplanningId == null || shiftplanningId <= 0)
            throw new BadRequestException(config
                    .getString("shiftplanning.id_required"));
        if (shiftIndex == null || shiftIndex <= 0)
            throw new BadRequestException(config
                    .getString("shift.index_required"));
        if (messengerId == null || messengerId <= 0)
            throw new BadRequestException(config
                    .getString("messenger.id_required"));
        
        ShiftAssignment.ShiftAssignmentPK id = new ShiftAssignment
                .ShiftAssignmentPK(new Messenger(messengerId), 
                        new Shift(shiftIndex, shiftplanningId));
        
        ShiftAssignment assignment = dao.find(id);
        if (assignment == null) {
            String err = String.format(config.getString("shift_assign.not_found"), 
                    messengerId, shiftplanningId, shiftIndex);
            throw new NotFoundException(err);
        }
        
        dao.delete(id);
        
        return convertToDto(assignment);
    }

    @Override
    public ShiftAssignment convertToEntity(ShiftAssignRequest data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ShiftAssignResponse convertToDto(ShiftAssignment entity) {
        ShiftAssignResponse response = new ShiftAssignResponse();
        Messenger messenger = entity.getId().getMessenger();
        
        response.setMessenger(new SimpleMessengerResponse(messenger.getId(), 
                messenger.getIdentification(), messenger.getName(), 
                messenger.getLastName(), messenger.getGender(), 
                messenger.getBirthday()));
        
        return response;
    }

    private Shift existsShift(Long shiftplanningId, Integer shiftIndex) {
        if (shiftplanningId == null || shiftplanningId <= 0)
            throw new BadRequestException(config
                    .getString("shiftplanning.id_required"));
        if (shiftIndex == null || shiftIndex <= 0)
            throw new BadRequestException(config
                    .getString("shift.index_required"));
        
        Shift shift = shiftDao.find(new Shift.ShiftPK(shiftIndex, 
                shiftplanningId));
        if (shift == null) {
            String err = String.format(config.getString("shift.not_found"), 
                    shiftplanningId, shiftIndex);
            throw new NotFoundException(err);
        }
        
        return shift;
    }

}
