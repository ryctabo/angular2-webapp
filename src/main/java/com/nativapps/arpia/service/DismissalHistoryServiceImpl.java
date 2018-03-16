package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.DismissalHistoryDao;
import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.entity.DismissalHistory;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.model.dto.DismissalHistoryRequest;
import com.nativapps.arpia.model.dto.DismissalHistoryResponse;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class DismissalHistoryServiceImpl extends GenericService implements DismissalHistoryService,
        DtoConverter<DismissalHistory, DismissalHistoryRequest, DismissalHistoryResponse> {

    private final DismissalHistoryDao controller;

    public DismissalHistoryServiceImpl(DismissalHistoryDao controller) {
        this.controller = controller;
    }

    /**
     * Get dismissal history entity from the given ID.
     *
     * @param id the dismissal history ID
     * @return ths dismissal history entity
     *
     * @throws BadRequestException if dismissal history ID is null or less than
     * or equal to 0
     * @throws NotFoundException if dismissal history entity is null
     */
    private DismissalHistory getEntity(Long id) throws BadRequestException,
            NotFoundException {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("d_history.id_required"));
        DismissalHistory dismissalHistory = controller.find(id);
        if (dismissalHistory == null) {
            String m = String.format(config.getString("d_history.not_found"), id);
            throw new NotFoundException(m);
        }
        return dismissalHistory;
    }

    @Override
    public List<DismissalHistoryResponse> getAll() {
        List<DismissalHistoryResponse> history = new ArrayList<>();
        for (DismissalHistory dHistory : controller.findAll())
            history.add(convertToDto(dHistory));
        return history;
    }

    @Override
    public List<DismissalHistoryResponse> getAll(Long messengerId) {
        if (messengerId == null || messengerId <= 0)
            throw new BadRequestException(config.getString("d_history.messenger_id"));

        List<DismissalHistoryResponse> history = new ArrayList<>();
        for (DismissalHistory dHistory : controller.findAll(messengerId))
            history.add(convertToDto(dHistory));
        return history;
    }

    @Override
    public DismissalHistoryResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public DismissalHistoryResponse add(DismissalHistoryRequest dismissalHistory) {
        if (dismissalHistory == null)
            throw new BadRequestException(config.getString("d_history.is_null"));

        int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        ErrorMessageData emd = new ErrorMessageData(statusCode);

        if (dismissalHistory.getDismissal() == null)
            emd.addMessage(config.getString("d_history.dismissal"));
        if (TextUtil.isEmpty(dismissalHistory.getReason()))
            emd.addMessage(config.getString("d_history.reason"));
        if (dismissalHistory.getMessengerId() == null
                || dismissalHistory.getMessengerId() <= 0)
            emd.addMessage(config.getString("d_history.messenger_id"));

        if (!emd.getMessages().isEmpty())
            throw new ServiceException(emd);

        MessengerDao mController = EntityControllerFactory.getMessengerController();
        Messenger messenger = mController.find(dismissalHistory.getMessengerId());
        if (messenger == null)
            throw new BadRequestException(config.getString("messenger.not_found"));

        messenger.setDismissal(dismissalHistory.getDismissal());
        messenger.getUser().setEnabled(dismissalHistory.getDismissal());
        mController.save(messenger);

        DismissalHistory entity = null;
        try {
            entity = controller.save(convertToEntity(dismissalHistory));
        } catch (Exception e) {
            messenger.setDismissal(!dismissalHistory.getDismissal());
            messenger.getUser().setEnabled(!dismissalHistory.getDismissal());
            mController.save(messenger);

            String msg = "Error: " + e.getCause().getMessage();
            throw new InternalServerErrorException(msg, e);
        }

        return convertToDto(entity);
    }

    @Override
    public DismissalHistory convertToEntity(DismissalHistoryRequest d) {
        return new DismissalHistory(d.getDismissal(), d.getReason(),
                new Messenger(d.getMessengerId()));
    }

    @Override
    public DismissalHistoryResponse convertToDto(DismissalHistory e) {
        return new DismissalHistoryResponse(e.getId(), e.getRegister(),
                e.isDismissal(), e.getReason(), e.getMessenger().getId());
    }

}
