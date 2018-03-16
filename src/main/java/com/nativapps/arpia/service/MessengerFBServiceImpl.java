package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.entity.CustomerParameter;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.SimpleMessengerResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import com.nativapps.arpia.database.dao.CustomerParamDao;
import com.nativapps.arpia.database.dao.MessengerFBDao;
import com.nativapps.arpia.database.entity.MessengerFB;
import com.nativapps.arpia.model.dto.SimpleMessengerRequest;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class MessengerFBServiceImpl extends GenericService implements MessengerFBService,
        DtoConverter<Messenger, SimpleMessengerRequest, SimpleMessengerResponse> {

    private final MessengerDao messengerDao = EntityControllerFactory
            .getMessengerController();

    private final CustomerParamDao parameterDao = EntityControllerFactory
            .getCustomerParamController();

    private final MessengerFBDao messengerFBDao = EntityControllerFactory
            .getMessengerFBController();

    @Override
    public List<SimpleMessengerResponse> getAll(Long customerId,
            MessengerFB.Type type) {
        if (customerId == null || customerId <= 0)
            throw new BadRequestException(config
                    .getString("customer.id_required"));

        CustomerParameter parameter = parameterDao.find(customerId);

        if (parameter == null)
            throw new NotFoundException(String.format(config
                    .getString("customer.not_found"), customerId));

        List<SimpleMessengerResponse> data = new ArrayList<>();

        for (MessengerFB messengerFB : messengerFBDao
                .findAllByType(customerId, type)) {
            data.add(convertToDto(messengerFB.getMessenger()));
        }

        return data;
    }

    @Override
    public SimpleMessengerResponse get(Long customerId, Long messengerId,
            MessengerFB.Type type) {
        return convertToDto(getEntity(customerId, messengerId, type)
                .getMessenger());
    }

    public MessengerFB getEntity(Long customerId, Long messengerId,
            MessengerFB.Type type) {
        ErrorMessageData errors = new ErrorMessageData(Response.Status.BAD_REQUEST.getStatusCode());
        if (customerId == null || customerId <= 0)
            errors.addMessage(config.getString("customer.id_required"));
        if (messengerId == null || messengerId <= 0)
            errors.addMessage(config.getString("meesenger.id_required"));
        if (type == null)
            errors.addMessage(config.getString("meesengerFB.type_required"));

        if (!errors.getMessages().isEmpty())
            throw new ServiceException(errors);

        MessengerFB messengerFB = messengerFBDao.find(customerId, messengerId, type);
        if (messengerFB == null) {
            if (type == MessengerFB.Type.FAVORITE_LIST)
                throw new NotFoundException(String.format(config
                        .getString("messengerFB.not_found_favorite"),
                        customerId, messengerId));
            else
                throw new NotFoundException(String.format(config
                        .getString("messengerFB.not_found_blacklist"),
                        customerId, messengerId));
        }

        return messengerFB;
    }

    @Override
    public SimpleMessengerResponse add(Long customerId,
            SimpleMessengerRequest data, MessengerFB.Type type) {
        ErrorMessageData errors = new ErrorMessageData(Response.Status.BAD_REQUEST
                .getStatusCode());
        if (customerId == null || customerId <= 0)
            errors.addMessage(config.getString("customer.id_required"));
        if (type == null)
            errors.addMessage(config.getString("messengerFB.type_required"));
        if (data == null)
            errors.addMessage(config.getString("messengerFB.is_null"));
        else if (data.getMessengerId() == null || data.getMessengerId() <= 0)
            errors.addMessage(config.getString("messenger.id_required"));

        if (!errors.getMessages().isEmpty())
            throw new ServiceException(errors);

        MessengerFB messengerFB = messengerFBDao.find(new MessengerFB
                .MessengerFBPK(customerId, data.getMessengerId()));
        if (messengerFB != null)
            throw new BadRequestException(String.format(config
                    .getString("messengerFB.exits"), data.getMessengerId(), 
                    customerId));

        CustomerParameter parameter = parameterDao.find(customerId);
        if (parameter == null)
            throw new BadRequestException(String.format(config
                    .getString("customer.not_found"), customerId));

        Messenger messenger = messengerDao.find(data.getMessengerId());
        if (messenger == null)
            throw new BadRequestException(String.format(config
                    .getString("messenger.not_found"), data.getMessengerId()));

        return convertToDto(messengerFBDao.save(new MessengerFB(parameter,
                messenger, type)).getMessenger());
    }

    @Override
    public SimpleMessengerResponse delete(Long customerId, Long messengerId,
            MessengerFB.Type type) {
        MessengerFB messengerFB = getEntity(customerId, messengerId, type);
        messengerFBDao.delete(messengerFB.getId());
        return convertToDto(messengerFB.getMessenger());
    }

    @Override
    public Messenger convertToEntity(SimpleMessengerRequest data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SimpleMessengerResponse convertToDto(Messenger entity) {
        return new SimpleMessengerResponse(entity.getId(),
                entity.getIdentification(), entity.getName(),
                entity.getLastName(), entity.getGender(), entity.getBirthday());
    }
}
