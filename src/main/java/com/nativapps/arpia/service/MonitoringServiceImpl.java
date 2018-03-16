package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.DomicileExecuteDao;
import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.MonitoringDao;
import com.nativapps.arpia.database.entity.DomicileExecute;
import com.nativapps.arpia.database.entity.Monitoring;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MonitoringRequest;
import com.nativapps.arpia.model.dto.MonitoringResponse;
import com.nativapps.arpia.model.dto.UserResponse;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.TextUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1
 */
public class MonitoringServiceImpl extends GenericService implements MonitoringService,
        DtoConverter<Monitoring, MonitoringRequest, MonitoringResponse> {

    private final MonitoringDao controller;

    public MonitoringServiceImpl(MonitoringDao controller) {
        this.controller = controller;
    }

    /**
     * Get monitoring entity from the given ID.
     *
     * @param id monitoring ID
     * @return monitoring data
     *
     * @throws BadRequestException if the monitoring ID is null or less than or
     * equal to 0.
     * @throws NotFoundException if the monitoring data obtained is null.
     */
    private Monitoring getEntity(Long id) throws NotFoundException,
            BadRequestException {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("monitoring.id"));
        Monitoring monitoring = controller.find(id);
        if (monitoring == null) {
            final String FORMAT = config.getString("monitoring.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return monitoring;
    }

    /**
     * Validate if monitoring data comply with all requeriments.
     *
     * @param request monitoring data
     * @param userInfo user info
     *
     * @throws BadRequestException if the monitoring data is null
     * @throws ServiceException if the monitoring data don't have any attribute
     * required
     * @throws InternalServerErrorException if the user information is null
     */
    private void validate(MonitoringRequest request, UserInfo userInfo)
            throws ServiceException, BadRequestException, InternalServerErrorException {
        if (userInfo == null)
            throw new InternalServerErrorException(config.getString("monitoring.user"));
        if (request == null) {
            throw new BadRequestException(config.getString("monitoring.is_null"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);

            if (TextUtil.isEmpty(request.getContent()))
                emd.addMessage(config.getString("monitoring.content"));

            DomicileExecuteDao dController = EntityControllerFactory.getDomicileExecuteController();
            if (request.getDomicileId() == null || request.getDomicileId() <= 0)
                emd.addMessage(config.getString("dexecute.id"));
            DomicileExecute domicile = dController.find(request.getDomicileId());
            if (domicile == null) {
                final String FORMAT = config.getString("dexecute.not_found");
                emd.addMessage(String.format(FORMAT, request.getDomicileId()));
            } else if (domicile.getCancelDate() == null) {
                emd.addMessage(config.getString("monitoring.not_canceled"));
            }

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }
    }

    @Override
    public ListResponse<MonitoringResponse> get(Calendar startDate,
            Calendar endDate, Long domicileId, int start, int size,
            String orderBy, OrderType type) {
        List<MonitoringResponse> items = new ArrayList<>();
        List<Monitoring> monitorings = controller.findAll(startDate, endDate,
                domicileId, start, size, orderBy, type);

        for (Monitoring monitoring : monitorings)
            items.add(convertToDto(monitoring));

        long count = controller.getCount(startDate, endDate, domicileId);
        return new ListResponse<>(count, items);
    }

    @Override
    public MonitoringResponse get(Long id) {
        Monitoring monitoring = getEntity(id);
        return convertToDto(monitoring);
    }

    @Override
    public MonitoringResponse add(MonitoringRequest request, UserInfo userInfo) {
        validate(request, userInfo);

        Monitoring monitoring = convertToEntity(request);
        monitoring.setUser(new User(userInfo.getId()));

        return convertToDto(controller.save(monitoring));
    }

    @Override
    public MonitoringResponse update(Long id, MonitoringRequest request, UserInfo userInfo) {
        Monitoring monitoring = getEntity(id);
        validate(request, userInfo);

        monitoring.setContent(request.getContent());

        monitoring.setModifierUser(new User(userInfo.getId()));
        monitoring.setUpdatedDate(Calendar.getInstance());

        return convertToDto(controller.save(monitoring));
    }

    @Override
    public MonitoringResponse delete(Long id) {
        MonitoringResponse monitoring = get(id);
        controller.delete(id);
        return monitoring;
    }

    @Override
    public Monitoring convertToEntity(MonitoringRequest d) {
        return new Monitoring(new DomicileExecute(d.getDomicileId()), d.getContent());
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
    public MonitoringResponse convertToDto(Monitoring e) {
        MonitoringResponse monitoring = new MonitoringResponse();

        monitoring.setId(e.getId());
        monitoring.setDomicileId(e.getDomicileId() == 0 ? e.getDomicile().getId() : e.getDomicileId());
        monitoring.setContent(e.getContent());
        monitoring.setUser(convertToDtoUser(e.getUser()));
        monitoring.setModifierUser(convertToDtoUser(e.getModifierUser()));
        monitoring.setRegisterDate(e.getRegisterDate());
        monitoring.setUpdatedDate(e.getUpdatedDate());

        return monitoring;
    }

}
