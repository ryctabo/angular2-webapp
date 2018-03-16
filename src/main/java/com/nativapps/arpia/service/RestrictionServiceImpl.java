package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.dao.RestrictionDao;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.Restriction;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.RestrictionRequest;
import com.nativapps.arpia.model.dto.RestrictionResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public class RestrictionServiceImpl extends GenericService
        implements RestrictionService,
        DtoConverter<Restriction, RestrictionRequest, RestrictionResponse> {

    private final RestrictionDao restrictionController
            = EntityControllerFactory.getRestrictionController();
    private final MessengerDao messengerController
            = EntityControllerFactory.getMessengerController();

    @Override
    public List<RestrictionResponse> getAll() {
        List<RestrictionResponse> restrictions = new ArrayList<>();
        for (Restriction restriction : restrictionController.findAll()) {
            restrictions.add(convertToDto(restriction));
        }
        return restrictions;
    }

    @Override
    public RestrictionResponse get(Long id) {
        Restriction response = getEntity(id);
        return convertToDto(response);
    }

    @Override
    public RestrictionResponse add(Long messengerId, RestrictionRequest r) {
        Messenger messenger = getMessengerEntity(messengerId);
        if (r == null) {
            throw new BadRequestException(
                    config.getString("restriction.is_required"));
        } else {
            ErrorMessageData emd = new ErrorMessageData();
            if (r.getBreakDay() == null)
                emd.addMessage(
                        config.getString("restriction.break_required"));
            if (r.getServiceBlocked() == null)
                emd.addMessage(
                        config.getString("restriction.service_required"));
            if (r.getTransferBlocked() == null)
                emd.addMessage(
                        config.getString("restriction.transfer_required"));
            if (r.getMoneyLimit() == null || r.getMoneyLimit() < 0)
                emd.addMessage(
                        config.getString("restriction.money_required"));

            //verify if exists errors
            if (!emd.getMessages().isEmpty()) {
                emd.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
                throw new ServiceException(emd);
            }
        }
        Restriction restriction = convertToEntity(r);
        restriction.setMessenger(messenger);
        restriction = restrictionController.save(restriction);
        return convertToDto(restriction);
    }

    @Override
    public RestrictionResponse update(Long messengerId, RestrictionRequest r) {
        Restriction currentRestriction = getEntity(messengerId);
        if (r == null) {
            throw new BadRequestException(
                    config.getString("restriction.is_required"));
        } else {
            ErrorMessageData emd = new ErrorMessageData();
            if (r.getBreakDay() == null)
                emd.addMessage(
                        config.getString("restriction.break_required"));
            if (r.getServiceBlocked() == null)
                emd.addMessage(
                        config.getString("restriction.service_required"));
            if (r.getTransferBlocked() == null)
                emd.addMessage(
                        config.getString("restriction.transfer_required"));
            if (r.getMoneyLimit() == null || r.getMoneyLimit() < 0)
                emd.addMessage(
                        config.getString("restriction.money_required"));

            //verify if exists errors
            if (!emd.getMessages().isEmpty()) {
                emd.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
                throw new ServiceException(emd);
            }
        }

        currentRestriction.setBreakDay(r.getBreakDay());
        currentRestriction.setTransferBlocked(r.getTransferBlocked());
        currentRestriction.setServiceBlocked(r.getServiceBlocked());
        currentRestriction.setMoneyLimit(r.getMoneyLimit());
        restrictionController.save(currentRestriction);
        return convertToDto(currentRestriction);
    }

    @Override
    public Restriction convertToEntity(RestrictionRequest data) {
        return new Restriction(data.getBreakDay(), data.getTransferBlocked(),
                data.getServiceBlocked(), data.getMoneyLimit());
    }

    @Override
    public RestrictionResponse convertToDto(Restriction e) {
        return new RestrictionResponse(e.getId(), e.isBreakDay(),
                e.isTransferBlocked(), e.isServiceBlocked(), e.getMoneyLimit());
    }

    /**
     * Get restriction entity from the given messenger ID.
     *
     * @param id messenger ID
     * @return restriction entity
     *
     * @throws BadRequestException if messenger ID is null or less than or equal
     * to 0
     * @throws NotFoundException if the restriction or the messenger obtained is
     * null
     */
    private Restriction getEntity(Long messengerId) throws BadRequestException,
            NotFoundException {
        if (messengerId == null && messengerId <= 0) {
            throw new BadRequestException(
                    config.getString("messenger.id_required"));
        }

        Messenger messenger = messengerController.find(messengerId);
        if (messenger == null) {
            throw new NotFoundException(
                    config.getString("messenger.not_found"));
        }

        Restriction r = restrictionController.findAllByMessengerId(messengerId);
        if (r == null) {
            throw new NotFoundException(
                    config.getString("restriction.not_found"));
        }
        return r;
    }

    /**
     * Get messenger entity by id provided
     *
     * @param messengerId
     * @return
     */
    private Messenger getMessengerEntity(Long messengerId) {
        if (messengerId == null || messengerId <= 0) {
            throw new BadRequestException(
                    config.getString("meesenger.id_required"));
        }

        Messenger currentMessenger = messengerController.find(messengerId);

        if (currentMessenger == null) {
            String msg = String.format(
                    config.getString("messenger.not_found"), messengerId);
            throw new NotFoundException(msg);
        }
        return currentMessenger;
    }

    @Override
    public RestrictionResponse delete(Long messengerId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
