package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.CustomerDataDao;
import com.nativapps.arpia.database.dao.MarketingMessageDao;
import com.nativapps.arpia.database.entity.CustomerData;
import com.nativapps.arpia.database.entity.CustomerType;
import com.nativapps.arpia.database.entity.MarketingMessage;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.dto.CustomerDataDto;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MarketingMessageRequest;
import com.nativapps.arpia.model.dto.MarketingMessageResponse;
import com.nativapps.arpia.model.dto.UserResponse;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.TextUtil;
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
public class MarketingMessageServiceImpl extends GenericService implements MarketingMessageService,
        DtoConverter<MarketingMessage, MarketingMessageRequest, MarketingMessageResponse> {

    private final MarketingMessageDao dao;

    private final CustomerDataDao customerDao;

    public MarketingMessageServiceImpl(MarketingMessageDao dao,
            CustomerDataDao customerDao) {
        this.dao = dao;
        this.customerDao = customerDao;
    }

    @Override
    public ListResponse<MarketingMessageResponse> getAll(int start, int size,
            Long customerId) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<MarketingMessageResponse> response = new ArrayList<>();
        for (MarketingMessage marketingMessage : dao.findAll(start, size,
                customerId)) {
            response.add(convertToDto(marketingMessage));
        }

        return new ListResponse<>(dao.findAllCount(customerId), response);
    }

    @Override
    public MarketingMessageResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    /**
     * Search a marketing message by provided ID
     *
     * @param id Marketing message ID
     * @return Marketing message entity
     */
    public MarketingMessage getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("mark_msg.id_required"));

        MarketingMessage entity = dao.find(id);
        if (entity == null) {
            String err = String.format(config.getString("mark_msg.not_found"), id);
            throw new NotFoundException(err);
        }

        return entity;
    }

    @Override
    public MarketingMessageResponse add(MarketingMessageRequest data, UserInfo userInfo) {
        if (data == null)
            throw new BadRequestException(config.getString("mark_msg.is_null"));
        else {
            ErrorMessageData emd = new ErrorMessageData(Response.Status.BAD_REQUEST
                    .getStatusCode());
            if (data.getCustomerId() == null || data.getCustomerId() <= 0)
                emd.addMessage(config.getString("customer.id_required"));
            if (TextUtil.isEmpty(data.getObservations()))
                emd.addMessage(config.getString("mark_msg.observations_required"));

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }

        CustomerData customer = customerDao.find(data.getCustomerId());
        if (customer == null) {
            String err = String.format(config.getString("customer.not_found"),
                    data.getCustomerId());
            throw new BadRequestException(err);
        }

        MarketingMessage entity = convertToEntity(data);
        entity.setCustomer(customer);
        entity.setRegisterDate(Calendar.getInstance());
        entity.setUser(new User(userInfo.getId()));

        return convertToDto(dao.save(entity));
    }

    @Override
    public MarketingMessageResponse delete(Long id) {
        MarketingMessageResponse response = get(id);
        dao.delete(id);
        return response;
    }

    @Override
    public MarketingMessage convertToEntity(MarketingMessageRequest data) {
        MarketingMessage entity = new MarketingMessage();
        entity.setObservations(data.getObservations());
        if (data.getCall() != null)
            entity.setCall(data.getCall());
        if (data.getVisit() != null)
            entity.setVisit(data.getVisit());

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
    public MarketingMessageResponse convertToDto(MarketingMessage entity) {
        MarketingMessageResponse response = new MarketingMessageResponse(entity
                .getId(), entity.getRegisterDate(), entity.getObservations(),
                entity.isVisit(), entity.isCall());

        CustomerDataDto customer = new CustomerDataDto();
        customer.setId(entity.getCustomer().getId());
        customer.setType(entity.getCustomer().getType());
        if (entity.getCustomer().getType() == CustomerType.PARTICULAR) {
            customer.setDisplayName(entity.getCustomer().getParticular()
                    .getName() + entity.getCustomer().getParticular().getLastName());
            customer.setIdentification(entity.getCustomer().getParticular()
                    .getIdentification());
        } else {
            customer.setDisplayName(entity.getCustomer().getEstablishment()
                    .getName());
            customer.setIdentification(entity.getCustomer().getEstablishment()
                    .getNit());
        }

        response.setUser(convertToDtoUser(entity.getUser()));
        response.setCustomer(customer);

        return response;
    }

}
