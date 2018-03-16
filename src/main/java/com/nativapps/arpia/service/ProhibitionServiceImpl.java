package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.Prohibition;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ProhibitionRequest;
import com.nativapps.arpia.model.dto.ProhibitionResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import com.nativapps.arpia.database.dao.ProhibitionDao;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public class ProhibitionServiceImpl extends GenericService
        implements ProhibitionService,
        DtoConverter<Prohibition, ProhibitionRequest, ProhibitionResponse> {

    private final ProhibitionDao prohibitionController
            = EntityControllerFactory.getProhibitionController();
    private final MessengerDao messengerController
            = EntityControllerFactory.getMessengerController();

    @Override
    public List<ProhibitionResponse> getAll() {
        List<ProhibitionResponse> responses = new ArrayList<>();
        for (Prohibition prohibition : prohibitionController.findAll()) {
            responses.add(convertToDto(prohibition));
        }
        return responses;
    }

    @Override
    public ProhibitionResponse get(Long id) {
        Prohibition response = getEntity(id);
        return convertToDto(response);
    }

    @Override
    public ProhibitionResponse add(Long messengerId, ProhibitionRequest r) {
        Messenger messenger = getMessengerEntity(messengerId);
        if (r == null) {
            throw new BadRequestException(
                    config.getString("prohibition.is_required"));
        } else {
            ErrorMessageData emd = new ErrorMessageData();
            if (r.getBank() == null)
                emd.addMessage(
                        config.getString("prohibition.bank_required"));
            if (r.getDocument() == null)
                emd.addMessage(
                        config.getString("prohibition.document_required"));
            if (r.getFood() == null)
                emd.addMessage(
                        config.getString("prohibition.food_required"));
            if (r.getLiqueur() == null)
                emd.addMessage(
                        config.getString("prohibition.liqueur_required"));
            if (r.getMoney() == null)
                emd.addMessage(
                        config.getString("prohibition.money_required"));
            if (r.getRent() == null)
                emd.addMessage(
                        config.getString("prohibition.rent_required"));
            if (r.getShopping() == null)
                emd.addMessage(
                        config.getString("prohibition.shopping_required"));
            if (r.getSoat() == null)
                emd.addMessage(
                        config.getString("prohibition.soat_required"));

            //verify if exists errors
            if (!emd.getMessages().isEmpty()) {
                emd.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
                throw new ServiceException(emd);
            }
        }
        Prohibition prohibition = convertToEntity(r);
        prohibition.setMessenger(messenger);
        prohibition = prohibitionController.save(prohibition);
        return convertToDto(prohibition);
    }

    @Override
    public ProhibitionResponse update(Long messengerId, ProhibitionRequest r) {
        Prohibition currentProhibition = getEntity(messengerId);
        if (r == null) {
            throw new BadRequestException(
                    config.getString("prohibition.is_required"));
        } else {
            ErrorMessageData emd = new ErrorMessageData();
            if (r.getBank() == null) {
                emd.addMessage(
                        config.getString("prohibition.bank_required"));
            }
            if (r.getDocument() == null) {
                emd.addMessage(
                        config.getString("prohibition.document_required"));
            }
            if (r.getFood() == null) {
                emd.addMessage(
                        config.getString("prohibition.food_required"));
            }
            if (r.getLiqueur() == null) {
                emd.addMessage(
                        config.getString("prohibition.liqueur_required"));
            }
            if (r.getMoney() == null) {
                emd.addMessage(
                        config.getString("prohibition.money_required"));
            }
            if (r.getRent() == null) {
                emd.addMessage(
                        config.getString("prohibition.rent_required"));
            }
            if (r.getShopping() == null) {
                emd.addMessage(
                        config.getString("prohibition.shopping_required"));
            }
            if (r.getSoat() == null) {
                emd.addMessage(
                        config.getString("prohibition.soat_required"));
            }
        }

        currentProhibition.setBank(r.getBank());
        currentProhibition.setDocument(r.getDocument());
        currentProhibition.setFood(r.getFood());
        currentProhibition.setLiqueur(r.getLiqueur());
        currentProhibition.setMoney(r.getMoney());
        currentProhibition.setRent(r.getRent());
        currentProhibition.setShopping(r.getShopping());
        currentProhibition.setSoat(r.getSoat());
        prohibitionController.save(currentProhibition);
        return convertToDto(currentProhibition);
    }

    @Override
    public Prohibition convertToEntity(ProhibitionRequest data) {
        return new Prohibition(data.getDocument(), data.getShopping(),
                data.getRent(), data.getMoney(), data.getLiqueur(),
                data.getFood(), data.getSoat(), data.getBank());
    }

    @Override
    public ProhibitionResponse convertToDto(Prohibition e) {
        return new ProhibitionResponse(e.getId(), e.isDocument(),
                e.isShopping(), e.isRent(), e.isMoney(), e.isLiqueur(),
                e.isFood(), e.isSoat(), e.isBank());
    }

    /**
     * Get prohibition entity from the given messenger ID.
     *
     * @param id messenger ID
     * @return prohibition entity
     *
     * @throws BadRequestException if messenger ID is null or less than or equal
     * to 0
     * @throws NotFoundException if the prohibition or the messenger obtained is
     * null
     */
    private Prohibition getEntity(Long messengerId) throws BadRequestException,
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

        Prohibition r = prohibitionController.findAllByMessengerId(messengerId);
        if (r == null) {
            throw new NotFoundException(
                    config.getString("prohibition.not_found"));
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
}
