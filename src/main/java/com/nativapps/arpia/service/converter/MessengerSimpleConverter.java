package com.nativapps.arpia.service.converter;

import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.model.dto.MessengerRequest;
import com.nativapps.arpia.model.dto.MessengerResponse;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class MessengerSimpleConverter implements DtoConverter<Messenger, MessengerRequest, MessengerResponse> {

    private static final MessengerSimpleConverter INSTANCE = new MessengerSimpleConverter();

    /**
     * Don't let anyone instantiate this class.
     */
    private MessengerSimpleConverter() { }

    public static MessengerSimpleConverter instance() {
        return INSTANCE;
    }

    @Override
    public Messenger convertToEntity(MessengerRequest data) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public MessengerResponse convertToDto(Messenger messenger) {
        if (messenger == null)
            return null;

        MessengerResponse response = new MessengerResponse();

        response.setId(messenger.getId());
        response.setIdentification(messenger.getIdentification());
        response.setName(messenger.getName());
        response.setLastName(messenger.getLastName());
        response.setGender(messenger.getGender());
        response.setBirthday(messenger.getBirthday());
        response.setPhoto(messenger.getPhoto());
        response.setCategory(messenger.getCategory());

        return response;
    }
}
