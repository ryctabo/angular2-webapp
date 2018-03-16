package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.PersonDao;
import com.nativapps.arpia.database.entity.Address;
import com.nativapps.arpia.database.entity.Email;
import com.nativapps.arpia.database.entity.Person;
import com.nativapps.arpia.database.entity.Phone;
import com.nativapps.arpia.model.dto.AddressResponse;
import com.nativapps.arpia.model.dto.EmailResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.NeighborhoodResponse;
import com.nativapps.arpia.model.dto.PersonRequest;
import com.nativapps.arpia.model.dto.PersonResponse;
import com.nativapps.arpia.model.dto.PhoneResponse;
import com.nativapps.arpia.service.converter.DtoConverter;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class PersonServiceImpl extends GenericService implements PersonService,
        DtoConverter<Person, PersonRequest, PersonResponse> {

    private final PersonDao personDao;

    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public ListResponse<PersonResponse> getAll(int start, int size, String search) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<PersonResponse> response = new ArrayList<>();
        for (Person person : personDao.findAll(start, size, search)) {
            response.add(convertToDto(person));
        }

        return new ListResponse<>(personDao.findAllCount(search), response);
    }

    @Override
    public PersonResponse get(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("person.id_required"));

        Person person = personDao.find(id);
        if (person == null)
            throw new NotFoundException(String.format(config
                    .getString("person.not_found"), id));

        return convertToDto(person);
    }

    @Override
    public Person convertToEntity(PersonRequest data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PersonResponse convertToDto(Person entity) {
        PersonResponse response = new PersonResponse();

        response.setId(entity.getId());
        response.setIdentification(entity.getIdentification());
        response.setName(entity.getName());
        response.setLastName(entity.getLastName());
        response.setGender(entity.getGender());
        response.setBirthday(entity.getBirthday());

        if (entity.getAddresses() != null) {
            for (Address address : entity.getAddresses()) {
                response.getAddresses().add(new AddressResponse(address.getId(),
                        address.getTag(), address.getResidentialAddress(),
                        new NeighborhoodResponse(address.getNeighborhood().getId(),
                                address.getNeighborhood().getName())));
            }
        }

        if (entity.getEmails() != null) {
            for (Email email : entity.getEmails()) {
                response.getEmails().add(new EmailResponse(email.getAddress(),
                        email.isConfirmed()));
            }
        }

        if (entity.getPhones() != null) {
            for (Phone phone : entity.getPhones()) {
                response.getPhones().add(new PhoneResponse(phone.getTag(),
                        phone.getNumber(), phone.getType(), phone.isConfirmed()));
            }
        }

        return response;
    }

}
