package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.dao.PenaltyDao;
import com.nativapps.arpia.database.dao.UserDao;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.MobileNumber;
import com.nativapps.arpia.database.entity.Penalty;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MessengerResponse;
import com.nativapps.arpia.model.dto.PenaltyRequest;
import com.nativapps.arpia.model.dto.PenaltyResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.PenaltyValidator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

public class PenaltyServiceImpl extends GenericService
        implements PenaltyService,
        DtoConverter<Penalty, PenaltyRequest, PenaltyResponse> {

    private final PenaltyDao penaltyDao
            = EntityControllerFactory.getPenaltyController();
    private final MessengerDao messengerDao
            = EntityControllerFactory.getMessengerController();
    private final UserDao userDao
            = EntityControllerFactory.getUserController();

    /**
     * Get messenger entity by messenger id provided.
     *
     * @param id messenger ID
     * @return a messenger entity.
     *
     * @throws BadRequestException if the messenger ID is null or less than or
     * equal to 0.
     * @throws NotFoundException if the messenger obtained is null.
     */
    private Messenger getMessenger(Long id) throws BadRequestException,
            NotFoundException {
        if (id == null || id <= 0) {
            throw new BadRequestException(config.getString("messenger.id_required"));
        }

        Messenger messenger = messengerDao.find(id);
        if (messenger == null) {
            String msg = String.format(config.getString("messenger.not_found"), id);
            throw new NotFoundException(msg);
        }
        return messenger;
    }

    /**
     * Get penalty entity by id provided.
     *
     * @param id penalty id
     * @return Penalty entity
     *
     * @throws BadRequestException if the messenger ID is null or less than or
     * equal to 0.
     * @throws NotFoundException if the messenger obtained is null.
     */
    private Penalty getEntity(Long id) throws BadRequestException,
            NotFoundException {
        if (id == null | id <= 0)
            throw new BadRequestException(config.getString("penalty.id"));

        Penalty p = penaltyDao.find(id);

        if (p == null) {
            String msg = String.format(config.getString("penalty.not_found"), id);
            throw new NotFoundException(msg);
        }
        return p;
    }

    @Override
    public PenaltyResponse add(PenaltyRequest req, Long userId) {
        if (req == null)
            throw new BadRequestException(config.getString("penalty.required"));

        User assignedBy = userDao.find(userId);
        if (assignedBy == null) {
            String msg = String.format(
                    config.getString("user.not_found"), userId);
            throw new NotFoundException(msg);
        }

        ErrorMessageData emd = new ErrorMessageData();
        PenaltyValidator.evaluateCreation(req, emd, config);
        if (!emd.getMessages().isEmpty()) {
            emd.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
            throw new ServiceException(emd);
        }

        Messenger messenger = getMessenger(req.getMessengerId());
        Penalty penalty = convertToEntity(req);
        penalty.setMessenger(messenger);
        penalty.setAssigneBy(assignedBy);
        penalty.setCreated(Calendar.getInstance());
        penalty = penaltyDao.save(penalty);
        return convertToDto(penalty);
    }

    @Override
    public PenaltyResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public ListResponse<PenaltyResponse> getAll(Long messenger, int start, int size) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<PenaltyResponse> penalties = new ArrayList<>();

        for (Penalty penalty : penaltyDao.findAll(messenger, start, size)) {
            penalties.add(convertToDto(penalty));
        }
        long totalCount = penalties.size();
        return new ListResponse<>(totalCount, penalties);

    }

    @Override
    public PenaltyResponse update(Long id, PenaltyRequest r, Long assignedById) {
        Penalty p = getEntity(id);
        User assignedBy = userDao.find(assignedById);
        if (assignedBy == null) {
            String msg = String.format(
                    config.getString("user.not_found"), assignedById);
            throw new NotFoundException(msg);
        }

        ErrorMessageData emd = new ErrorMessageData();
        PenaltyValidator.evaluateCreation(r, emd, config);
        if (!emd.getMessages().isEmpty()) {
            emd.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
            throw new ServiceException(emd);
        }

        p.setDescription(r.getDescription());
        p.setAssigneBy(assignedBy);
        p.setUpdated(Calendar.getInstance());
        p.setMessenger(getMessenger(r.getMessengerId()));

        p = penaltyDao.save(p);
        return convertToDto(p);
    }

    @Override
    public PenaltyResponse delete(Long id) {
        Penalty p = getEntity(id);
        penaltyDao.delete(id);
        return convertToDto(p);
    }

    @Override
    public Penalty convertToEntity(PenaltyRequest data) {
        return new Penalty(data.getDescription());
    }

    @Override
    public PenaltyResponse convertToDto(Penalty e) {
        Messenger eMessenger = e.getMessenger();
        MessengerResponse messenger = null;
        if (eMessenger != null) {
            messenger = new MessengerResponse();
            messenger.setId(eMessenger.getId());
            messenger.setIdentification(eMessenger.getIdentification());
            messenger.setName(eMessenger.getName());
            messenger.setLastName(eMessenger.getLastName());
            messenger.setGender(eMessenger.getGender());
            messenger.setBirthday(eMessenger.getBirthday());
            messenger.setPhoto(eMessenger.getPhoto());
            MobileNumber mobile = eMessenger.getMobile();
            messenger.setMobile(mobile == null ? null : mobile.getId().getIndex());
            messenger.setCategory(eMessenger.getCategory());
        }

        return new PenaltyResponse(e.getId(), messenger, e.getCreated(),
                e.getUpdated(), e.getDescription());
    }
}
