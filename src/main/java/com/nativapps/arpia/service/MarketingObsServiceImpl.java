package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.CustomerDataDao;
import com.nativapps.arpia.database.dao.MarketingObsDao;
import com.nativapps.arpia.database.entity.CustomerData;
import com.nativapps.arpia.database.entity.CustomerType;
import com.nativapps.arpia.database.entity.Establishment;
import com.nativapps.arpia.database.entity.MarketingObs;
import com.nativapps.arpia.database.entity.Particular;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.dto.CustomerDataDto;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MarketingObsRequest;
import com.nativapps.arpia.model.dto.MarketingObsResponse;
import com.nativapps.arpia.model.dto.UserResponse;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0.1
 */
public class MarketingObsServiceImpl extends GenericService implements MarketingObsService,
        DtoConverter<MarketingObs, MarketingObsRequest, MarketingObsResponse> {

    private final MarketingObsDao dao;
    private final CustomerDataDao customerDao;

    public MarketingObsServiceImpl(MarketingObsDao dao, CustomerDataDao customerDao) {
        this.dao = dao;
        this.customerDao = customerDao;
    }

    @Override
    public ListResponse<MarketingObsResponse> getAll(int start, int size,
            Long customerId) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<MarketingObsResponse> response = new ArrayList<>();
        for (MarketingObs marketingObs : dao.findAll(start, size, customerId)) {
            response.add(convertToDto(marketingObs));
        }

        return new ListResponse<>(dao.findAllCount(customerId), response);
    }

    /**
     * Returns a validate marketing observation entity
     *
     * @param id
     * @return
     */
    private MarketingObs getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("mark_obs.id_required"));

        MarketingObs entity = dao.find(id);
        if (entity == null)
            throw new NotFoundException(String.format(config
                    .getString("mark_obs.not_found"), id));

        return entity;
    }

    @Override
    public MarketingObsResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public MarketingObsResponse add(MarketingObsRequest data, UserInfo userInfo) {
        if (data == null)
            throw new BadRequestException(config.getString("mark_obs.is_null"));
        else {
            ErrorMessageData errors = new ErrorMessageData(Response.Status.BAD_REQUEST
                    .getStatusCode());
            if (data.getCustomerId() == null)
                errors.addMessage(config.getString("customer.id_required"));
            if (data.getObservations() == null)
                errors.addMessage(config.getString("mark_obs.observations_required"));

            if (!errors.getMessages().isEmpty())
                throw new ServiceException(errors);
        }

        CustomerData customer = customerDao.find(data.getCustomerId());
        if (customer == null)
            throw new BadRequestException(String.format(config
                    .getString("customer.not_found"), data.getCustomerId()));

        MarketingObs entity = convertToEntity(data);
        entity.setCustomer(customer);
        entity.setUser(new User(userInfo.getId()));

        return convertToDto(dao.save(entity));
    }

    @Override
    public MarketingObsResponse update(Long id, MarketingObsRequest data,
            UserInfo userInfo) {
        MarketingObs entity = getEntity(id);
        if (data == null)
            throw new BadRequestException(config.getString("mark_obs.is_null"));

        if (data.getObservations() != null)
            entity.setObservations(data.getObservations());
        if (data.getCustomerId() != null) {
            CustomerData customer = customerDao.find(data.getCustomerId());
            if (customer == null)
                throw new BadRequestException(String.format(config
                        .getString("customer.not_found"), data.getCustomerId()));
            entity.setCustomer(customer);
        }
        entity.setUser(new User(userInfo.getId()));

        return convertToDto(dao.save(entity));
    }

    @Override
    public MarketingObsResponse delete(Long id) {
        MarketingObsResponse response = get(id);
        dao.delete(id);
        return response;
    }

    @Override
    public MarketingObs convertToEntity(MarketingObsRequest data) {
        MarketingObs entity = new MarketingObs();
        entity.setObservations(data.getObservations());
        entity.setRegisterDate(Calendar.getInstance());

        return entity;
    }

    /**
     * Convert from user entity to user response.
     *
     * @param user user entity
     * @return user response
     */
    private UserResponse convertToDtoUser(User user) {
        if (user == null)
            return null;

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setDisplayName(user.getDisplayName());
        response.setEmail(user.getEmail());
        response.setUrlPic(user.getUrlPic());
        response.setUsername(user.getUsername());

        return response;
    }

    @Override
    public MarketingObsResponse convertToDto(MarketingObs entity) {
        MarketingObsResponse response = new MarketingObsResponse();

        response.setId(entity.getId());
        response.setRegisterDate(entity.getRegisterDate());
        response.setObservations(entity.getObservations());

        CustomerDataDto customer = new CustomerDataDto();
        customer.setId(entity.getCustomer().getId());

        if (entity.getCustomer().getType() == CustomerType.PARTICULAR) {
            Particular p = entity.getCustomer().getParticular();
            customer.setDisplayName(p.getName() + " " + p.getLastName());
            customer.setIdentification(p.getIdentification());
        } else {
            Establishment e = entity.getCustomer().getEstablishment();
            customer.setDisplayName(e.getName());
            customer.setIdentification(e.getNit());
        }

        response.setCustomer(customer);
        response.setUser(convertToDtoUser(entity.getUser()));

        return response;
    }
}
