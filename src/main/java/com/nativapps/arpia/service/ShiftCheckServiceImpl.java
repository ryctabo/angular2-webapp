package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.ShiftAssignmentDao;
import com.nativapps.arpia.database.dao.ShiftCheckDao;
import com.nativapps.arpia.database.dao.ShiftCheckKeyDao;
import com.nativapps.arpia.database.entity.*;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ShiftCheckRequest;
import com.nativapps.arpia.model.dto.ShiftCheckResponse;
import com.nativapps.arpia.model.dto.SimpleMessengerResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.KeyGenerator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftCheckServiceImpl extends GenericService implements 
        ShiftCheckService, DtoConverter<ShiftCheck, ShiftCheckRequest, 
        ShiftCheckResponse> {
    
    private final ShiftCheckDao dao;
    
    private final ShiftAssignmentDao assignmentDao;

    private final ShiftCheckKeyDao checkKeyDao;

    public ShiftCheckServiceImpl(ShiftCheckDao dao, ShiftAssignmentDao assignmentDao,
                                 ShiftCheckKeyDao checkKeyDao) {
        this.dao = dao;
        this.assignmentDao = assignmentDao;
        this.checkKeyDao = checkKeyDao;
    }

    @Override
    public List<ShiftCheckResponse> getAll(Long shiftplanningId, 
            Integer shiftIndex, Long messengerId) {
        
        List<ShiftCheckResponse> response = new ArrayList<>();
        for (ShiftCheck shiftCheck : dao.findAll(new Shift.ShiftPK(shiftIndex, 
                shiftplanningId), messengerId)) {
            response.add(convertToDto(shiftCheck));
        }
        
        return response;
    }

    @Override
    public ShiftCheckResponse clockin(Long shiftplanningId, Integer shiftIndex, 
            ShiftCheckRequest request) {
        if (request == null)
            throw new BadRequestException(config
                    .getString("shift_check.is_null"));
        else {
            if (request.getGoodAppearance() == null)
                throw new BadRequestException(config.getString("shift_check."
                        + "good_appearance_required"));
        }
        
        ShiftAssignment assignment = existsAssignment(shiftplanningId, 
                shiftIndex, request.getMessengerId());
        
        ShiftCheck find = dao.find(assignment.getId());
        if (find != null)
            throw new BadRequestException(config
                    .getString("shift_check.clockin_done"));
        
        ShiftCheck check = convertToEntity(request);
        check.setAssignment(assignment);
        check.setClockIn(Calendar.getInstance());

        check = dao.save(check);
        ShiftCheckKey key = new ShiftCheckKey(KeyGenerator.getNumberKey(6),
                Calendar.getInstance(), check);
        checkKeyDao.save(key);
        
        return convertToDto(check);
    }

    @Override
    public ShiftCheckResponse clockout(Long shiftplanningId, Integer shiftIndex,
            ShiftCheckRequest request) {
        if (request == null)
            throw new BadRequestException(config
                    .getString("shift_check.is_null"));
        
        ShiftAssignment assignment = existsAssignment(shiftplanningId, 
                shiftIndex, request.getMessengerId());
        
        ShiftCheck check = dao.find(assignment.getId());
        if (check == null) {
            String err = String.format(config.getString("shift_check.not_found"), 
                    request.getMessengerId(), shiftplanningId, shiftIndex);
            throw new NotFoundException(err);
        }
        
        if (check.getClockOut() == null)
            check.setClockOut(Calendar.getInstance());
        else
            throw new BadRequestException(config
                    .getString("shift_check.clockout_done"));

        return convertToDto(dao.save(check));
    }

    @Override
    public ShiftCheck convertToEntity(ShiftCheckRequest data) {
        ShiftCheck entity = new ShiftCheck();
        entity.setGoodAppearance(data.getGoodAppearance());
        entity.setObservations(data.getObservations());
        
        return entity;
    }

    @Override
    public ShiftCheckResponse convertToDto(ShiftCheck entity) {
        ShiftCheckResponse response = new ShiftCheckResponse();
        Messenger m = entity.getAssignment().getId().getMessenger();
        response.setMessenger(new SimpleMessengerResponse(m.getId(), 
                m.getIdentification(), m.getName(), m.getLastName(), 
                m.getGender(), m.getBirthday()));
        response.setGoodAppearance(entity.isGoodAppearance());
        response.setObservations(entity.getObservations());
        response.setClockIn(entity.getClockIn());
        response.setClockOut(entity.getClockOut());
        
        return response;
    }

    private ShiftAssignment existsAssignment(Long shiftplanningId, 
            Integer shiftIndex, Long messengerId) {
        ErrorMessageData emd = new ErrorMessageData(Response.Status.BAD_REQUEST
                .getStatusCode());
        
        if (shiftplanningId == null || shiftplanningId <= 0)
            emd.addMessage(config.getString("shiftplanning.id_required"));
        if (shiftIndex == null || shiftIndex <= 0)
            emd.addMessage(config.getString("shift.index_required"));
        if (messengerId == null || messengerId <= 0)
            emd.addMessage(config.getString("messenger.id_required"));
        
        if (!emd.getMessages().isEmpty())
            throw new ServiceException(emd);
        
        ShiftAssignment.ShiftAssignmentPK id = new ShiftAssignment
                .ShiftAssignmentPK(new Messenger(messengerId), 
                        new Shift(shiftIndex, shiftplanningId));
        
        ShiftAssignment assignment = assignmentDao.find(id);
        if (assignment == null) {
            String err = String.format(config.getString("shift_assign.not_found"), 
                    messengerId, shiftplanningId, shiftIndex);
            throw new NotFoundException(err);
        }
        
        return assignment;
    }

}
