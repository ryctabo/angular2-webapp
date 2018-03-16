package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.SecurityDepositDao;
import com.nativapps.arpia.database.dao.UserDao;
import com.nativapps.arpia.database.entity.SecurityDeposit;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.SecurityDepositPaymentRequest;
import com.nativapps.arpia.model.dto.SecurityDepositPaymentResponse;
import com.nativapps.arpia.model.dto.UserResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.SecurityDepositPaymentValidator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0.1
 */
public class SecurityDepositPaymentServiceImpl extends GenericService
        implements SecurityDepositPaymentService,
        DtoConverter<SecurityDeposit, SecurityDepositPaymentRequest, SecurityDepositPaymentResponse> {

    private final UserDao userDao = EntityControllerFactory
            .getUserController();
    private final SecurityDepositDao depositDao = EntityControllerFactory
            .getSecurityDepositController();

    /**
     * Validate if security deposit have all attributes for save to database.
     *
     * @param request security deposit to validate
     *
     * @throws BadRequestException if the security deposit data is null
     * @throws ServiceException if the security deposit data don't have any
     * attribute required
     */
    private void validateSecurityDeposit(SecurityDepositPaymentRequest request)
            throws BadRequestException, ServiceException {
        if (request == null) {
            throw new BadRequestException(config.getString("securityDeposit.required"));
        } else {
            ErrorMessageData emd = new ErrorMessageData();
            SecurityDepositPaymentValidator.evaluate(request, emd, config);

            //verify if exists errors
            if (!emd.getMessages().isEmpty()) {
                emd.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
                throw new ServiceException(emd);
            }
        }
    }

    /**
     * Get user information in the database from the given user ID.
     *
     * @param userId user ID.
     * @return user information.
     */
    private User getUserEntity(Long userId) {
        if (userId == null || userId <= 0) {
            throw new BadRequestException(config.getString("user.id_required"));
        }

        User user = userDao.find(userId);
        if (user == null) {
            String m = String.format(config.getString("user.not_found"), userId);
            throw new NotFoundException(m);
        }

        return user;
    }

    /**
     * Get security deposit payment information in the database from the given
     * ID.
     *
     * @param id security deposit ID.
     * @return security deposit payment information.
     */
    private SecurityDeposit getEntity(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException(
                    config.getString("securityDeposit.id"));
        }

        SecurityDeposit deposit = depositDao.find(id);
        if (deposit == null) {
            String m = String.format(
                    config.getString("securityDeposit.not_found"), id);
            throw new NotFoundException(m);
        }

        return deposit;
    }

    @Override
    public SecurityDepositPaymentResponse add(
            SecurityDepositPaymentRequest request, Long receivedId) {
        validateSecurityDeposit(request);
        User received = getUserEntity(receivedId);
        User owner = getUserEntity(request.getOwnerId());

        Double systemDeposit = ServiceFactory
                .getSystemConfigService()
                .get()
                .getSecurityDeposit();
        Double paidToDate = depositDao.paidToDate(owner.getId());

        if (paidToDate >= systemDeposit) {
            throw new BadRequestException(config.getString("securityDeposit.paidOut"));
        }
        if (paidToDate + request.getPayment() > systemDeposit + 50) {
            String msg = String.format(
                    config.getString("securityDeposit.payment_exceeds"),
                    systemDeposit - paidToDate);
            throw new BadRequestException(msg);
        }

        SecurityDeposit deposit = convertToEntity(request);
        deposit.setOwner(owner);
        deposit.setCreated(Calendar.getInstance());
        deposit.setReceivedBy(received);
        deposit = depositDao.save(deposit);
        return convertToDto(deposit);
    }

    @Override
    public ListResponse<SecurityDepositPaymentResponse> getAll(String search,
            Integer start, Integer size, Date from, Date to, String orderBy,
            OrderType orderType) {
        List<SecurityDepositPaymentResponse> responses
                = new ArrayList<>();
        for (SecurityDeposit findAllPayment : depositDao
                .findAllPayments(
                        search, start, size, from, to, orderBy, orderType)) {
            responses.add(convertToDto(findAllPayment));
        }
        return new ListResponse<>(depositDao.getCountPayments(), responses);
    }

    @Override
    public SecurityDepositPaymentResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public SecurityDeposit convertToEntity(SecurityDepositPaymentRequest data) {
        return new SecurityDeposit(data.getPayment());
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
    public SecurityDepositPaymentResponse convertToDto(SecurityDeposit e) {
        return new SecurityDepositPaymentResponse(e.getId(),
                convertToDtoUser(e.getOwner()), e.getCreated(),
                convertToDtoUser(e.getReceivedBy()), e.getPayment());
    }

}
