package com.nativapps.arpia.service.converter;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.ParticularDao;
import com.nativapps.arpia.database.entity.Address;
import com.nativapps.arpia.database.entity.Email;
import com.nativapps.arpia.database.entity.Particular;
import com.nativapps.arpia.database.entity.Phone;
import com.nativapps.arpia.model.dto.AddressResponse;
import com.nativapps.arpia.model.dto.EmailResponse;
import com.nativapps.arpia.model.dto.NeighborhoodResponse;
import com.nativapps.arpia.model.dto.ParticularRequest;
import com.nativapps.arpia.model.dto.ParticularResponse;
import com.nativapps.arpia.model.dto.PhoneResponse;
import java.util.ArrayList;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class ParticularSimpleConverter implements DtoConverter<Particular, ParticularRequest, ParticularResponse> {

    private static final ParticularSimpleConverter INSTANCE = new ParticularSimpleConverter();

    /**
     * Don't let anyone instantiate this class.
     */
    private ParticularSimpleConverter() {
    }

    public static ParticularSimpleConverter instance() {
        return INSTANCE;
    }

    @Override
    public Particular convertToEntity(ParticularRequest data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ParticularResponse convertToDto(Particular entity) {
        ParticularResponse data = new ParticularResponse();

        data.setId(entity.getCustomerData().getId());
        data.setIdentification(entity.getIdentification());
        data.setName(entity.getName());
        data.setLastName(entity.getLastName());
        data.setGender(entity.getGender());
        data.setBirthday(entity.getBirthday());
        data.setObservations(entity.getCustomerData().getObservations());

        if (entity.getAddresses() != null) {
            data.setAddresses(new ArrayList<AddressResponse>());
            for (Address address : entity.getAddresses()) {
                data.getAddresses().add(new AddressResponse(address.getId(),
                        address.getTag(), address.getResidentialAddress(),
                        new NeighborhoodResponse(address.getNeighborhood()
                                .getId(), address.getNeighborhood().getName())));
            }
        }

        if (entity.getEmails() != null) {
            data.setEmails(new ArrayList<EmailResponse>());
            for (Email email : entity.getEmails()) {
                data.getEmails().add(new EmailResponse(email.getAddress(),
                        email.isConfirmed()));
            }
        }

        if (entity.getPhones() != null) {
            data.setPhones(new ArrayList<PhoneResponse>());
            for (Phone phone : entity.getPhones()) {
                data.getPhones().add(new PhoneResponse(phone.getTag(),
                        phone.getNumber(), phone.getType(), phone.isConfirmed()));
            }
        }

        return data;
    }

}
