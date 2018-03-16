package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.ShiftCheckDao;
import com.nativapps.arpia.database.dao.ShiftCheckKeyDao;
import com.nativapps.arpia.database.entity.*;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ShiftCheckKeyRequest;
import com.nativapps.arpia.model.dto.ShiftCheckKeyResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.KeyGenerator;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftCheckKeyServiceImpl extends GenericService implements ShiftCheckKeyService,
        DtoConverter<ShiftCheckKey, ShiftCheckKeyRequest, ShiftCheckKeyResponse>{

    private final ShiftCheckKeyDao dao;

    private final ShiftCheckDao checkDao;

    public ShiftCheckKeyServiceImpl(ShiftCheckKeyDao dao, ShiftCheckDao checkDao) {
        this.dao = dao;
        this.checkDao = checkDao;
    }

    @Override
    public List<ShiftCheckKeyResponse> getAll(Long shiftplanningId, Integer shiftIndex,
                                              Long messengerId) {

        ShiftCheck check = getShiftCheck(shiftplanningId, shiftIndex, messengerId);

        List<ShiftCheckKeyResponse> response = new ArrayList<>();
        for (ShiftCheckKey key : dao.findAll(check.getId())) {
            response.add(convertToDto(key));
        }

        return response;
    }

    @Override
    public ShiftCheckKeyResponse generateKey(Long shiftplanningId, Integer shiftIndex,
                                             Long messengerId) {
        ShiftCheck check = getShiftCheck(shiftplanningId, shiftIndex, messengerId);
        ShiftCheckKey key = new ShiftCheckKey(KeyGenerator.getNumberKey(6),
                Calendar.getInstance(), check);

        return convertToDto(dao.save(key));
    }

    @Override
    public ShiftCheckKeyResponse useKey(Long shiftplanningId, Integer shiftIndex,
                                        Long messengerId, ShiftCheckKeyRequest request) {
        ShiftCheck check = getShiftCheck(shiftplanningId, shiftIndex, messengerId);
        ShiftCheckKey checkKey = dao.find(check.getId(), request.getKey());
        if (checkKey == null)
            throw new NotFoundException(config.getString("sckey.not_found"));

        if (checkKey.getUseDate() != null)
            throw new BadRequestException(config.getString("sckey.key_used"));
        checkKey.setUseDate(Calendar.getInstance());

        return convertToDto(dao.save(checkKey));
    }

    @Override
    public ShiftCheckKey convertToEntity(ShiftCheckKeyRequest data) {
        return null;
    }

    @Override
    public ShiftCheckKeyResponse convertToDto(ShiftCheckKey entity) {
        ShiftCheckKeyResponse response = new ShiftCheckKeyResponse();
        response.setKey(entity.getKey());
        response.setCreationDate(entity.getCreationDate());
        response.setUseDate(entity.getUseDate());

        return response;
    }

    public ShiftCheck getShiftCheck(Long shiftplanningId, Integer shiftIndex,
                                    Long messengerId) {
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

        ShiftCheck check = checkDao.find(id);
        if (check == null) {
            String err = String.format(config.getString("shift_check.not_found"),
                    messengerId, shiftplanningId, shiftIndex);
            throw new NotFoundException(err);
        }

        return check;
    }
}
