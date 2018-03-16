package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.EmailDao;
import com.nativapps.arpia.database.dao.NeighborhoodDao;
import com.nativapps.arpia.database.dao.ParticularDao;
import com.nativapps.arpia.database.dao.PhoneDao;
import com.nativapps.arpia.database.entity.Address;
import com.nativapps.arpia.database.entity.Email;
import com.nativapps.arpia.database.entity.Gender;
import com.nativapps.arpia.database.entity.Neighborhood;
import com.nativapps.arpia.database.entity.Particular;
import com.nativapps.arpia.database.entity.Phone;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.AddressRequest;
import com.nativapps.arpia.model.dto.EmailRequest;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.ParticularRequest;
import com.nativapps.arpia.model.dto.ParticularResponse;
import com.nativapps.arpia.model.dto.PhoneRequest;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.converter.ParticularSimpleConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.PersonValidator;
import com.nativapps.arpia.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class ParticularServiceImpl extends GenericService implements ParticularService,
        DtoConverter<Particular, ParticularRequest, ParticularResponse> {

    private final ParticularDao particularDao = EntityControllerFactory
            .getParticularController();

    private final NeighborhoodDao neighborhoodDao = EntityControllerFactory
            .getNeighborhoodController();

    @Override
    public ParticularResponse add(ParticularRequest data) {
        if (data == null)
            throw new BadRequestException(config
                    .getString("particular.is_null"));
        else {
            ErrorMessageData errors = new ErrorMessageData();
            PersonValidator.evaluatePerson(data, errors, config);

            if (data.getAddresses() != null && !data.getAddresses().isEmpty()) {
                for (AddressRequest address : data.getAddresses()) {
                    PersonValidator.evaluateAddress(address, errors, config);
                }
            } else
                errors.addMessage(config.getString("addres.at_least"));

            if (data.getEmails() != null && !data.getEmails().isEmpty()) {
                for (EmailRequest edata : data.getEmails()) {
                    PersonValidator.evaluateEmail(edata, errors, config);
                }
            }

            if (data.getPhones() != null && !data.getPhones().isEmpty()) {
                for (PhoneRequest pdata : data.getPhones()) {
                    PersonValidator.evaluatePhone(pdata, errors, config);
                }
            } else
                errors.addMessage(config.getString("phone.at_least"));

            if (!errors.getMessages().isEmpty()) {
                errors.setStatusCode(Response.Status.BAD_REQUEST
                        .getStatusCode());
                throw new ServiceException(errors);
            }
        }

        return convertToDto(particularDao.save(convertToEntity(data)));
    }

    @Override
    public ParticularResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    /**
     * Returns the particular entity from id provided
     *
     * @param id ID of the particular
     * @return searched entity
     */
    private Particular getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("person.id_required"));

        Particular particular = particularDao.findByCustomerId(id);

        if (particular == null)
            throw new NotFoundException(String.format(config
                    .getString("particular.not_found"), id));

        return particular;
    }

    @Override
    public ListResponse<ParticularResponse> getAll(int start, int size, String search,
            String orderBy, OrderType orderType, Gender gender) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<ParticularResponse> response = new ArrayList<>();
        List<Particular> result = particularDao.findAll(start, size, search,
                orderBy, orderType, gender);

        for (Particular particular : result) {
            response.add(convertToDto(particular));
        }

        return new ListResponse<>(particularDao.findAllCount(search, gender),
                response);
    }

    @Override
    public ParticularResponse update(Long id, ParticularRequest data) {
        Particular entity = getEntity(id);

        if (!TextUtil.isEmpty(data.getIdentification()) && !data
                .getIdentification().equalsIgnoreCase(entity
                        .getIdentification()))
            entity.setIdentification(data.getIdentification());
        if (!TextUtil.isEmpty(data.getName()) && !data.getName()
                .equalsIgnoreCase(entity.getName()))
            entity.setName(data.getName());
        if (!TextUtil.isEmpty(data.getLastName()) && !data.getLastName()
                .equalsIgnoreCase(entity.getLastName()))
            entity.setLastName(data.getLastName());
        if (!TextUtil.isEmpty(data.getObservations()) && !data.getObservations()
                .equalsIgnoreCase(data.getObservations()))
            entity.getCustomerData().setObservations(data.getObservations());
        if (data.getGender() != null && data.getGender() != entity.getGender())
            entity.setGender(data.getGender());
        if (data.getBirthday() != null && !data.getBirthday().equals(entity
                .getBirthday()))
            entity.setBirthday(data.getBirthday());

        return convertToDto(particularDao.save(entity));
    }

    @Override
    public ParticularResponse delete(Long id) {
        ParticularResponse data = get(id);
        particularDao.delete(id);
        return data;
    }

    @Override
    public Particular convertToEntity(ParticularRequest data) {
        Particular particular = new Particular();

        particular.setIdentification(data.getIdentification());
        particular.setName(data.getName());
        particular.setLastName(data.getLastName());
        particular.setBirthday(data.getBirthday());
        particular.setGender(data.getGender());
        particular.getCustomerData().setObservations(data.getObservations());

        for (AddressRequest adata : data.getAddresses()) {
            Neighborhood neighborhood = neighborhoodDao.find(adata
                    .getNeighborhood());
            if (neighborhood == null)
                throw new NotFoundException(String.format(config
                        .getString("address.neighborhood_not_found"), adata
                        .getNeighborhood()));
            particular.addAddress(new Address(adata.getTag(),
                    adata.getResidentialAddress(), neighborhood));
        }

        for (EmailRequest edata : data.getEmails()) {
            particular.addEmail(new Email(edata.getAddress()));
        }

        for (PhoneRequest pdata : data.getPhones()) {
            particular.addPhone(new Phone(pdata.getTag(), pdata.getNumber(),
                    pdata.getPhoneType()));
        }

        return particular;
    }

    @Override
    public ParticularResponse convertToDto(Particular entity) {
        return ParticularSimpleConverter.instance().convertToDto(entity);
    }
}
