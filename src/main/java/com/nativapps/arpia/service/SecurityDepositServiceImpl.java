package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.SecurityDepositDao;
import com.nativapps.arpia.database.dao.UserDao;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.SystemConfigManager;
import com.nativapps.arpia.model.dto.SecurityDepositResponse;
import com.nativapps.arpia.model.dto.UserResponse;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class SecurityDepositServiceImpl extends GenericService
        implements SecurityDepositService {

    private final UserDao userDao = EntityControllerFactory
            .getUserController();
    private final SecurityDepositDao depositDao = EntityControllerFactory
            .getSecurityDepositController();

    /**
     * Get user information in the database from the given user ID.
     *
     * @param userId user ID.
     * @return user information.
     */
    private User getUserEntity(Long userId) {
        if (userId == null || userId <= 0)
            throw new BadRequestException(config.getString("user.id_required"));

        User user = userDao.find(userId);
        if (user == null) {
            String m = String.format(config.getString("user.not_found"), userId);
            throw new NotFoundException(m);
        }

        return user;
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
    public List<SecurityDepositResponse> getAll(String search,
            Integer start, Integer size, String orderBy, OrderType orderType) {
        List<SecurityDepositResponse> responses
                = new ArrayList();
        for (User user
                : depositDao.findAll(search, start, size, orderBy, orderType)) {
            double systemDeposit = SystemConfigManager.get().getSecurityDeposit();
            double paidToDate = depositDao.paidToDate(user.getId());
            responses.add(new SecurityDepositResponse(
                    convertToDtoUser(user), paidToDate < 0 ? 0 : paidToDate,
                    systemDeposit));
        }
        return responses;
    }

    @Override
    public SecurityDepositResponse get(Long ownerID) {
        Double paidToDate = depositDao.paidToDate(ownerID);
        User owner = getUserEntity(ownerID);
        Double depositAmount = SystemConfigManager.get().getSecurityDeposit();
        return new SecurityDepositResponse(convertToDtoUser(owner),
                paidToDate < 0 ? 0 : paidToDate,
                depositAmount);
    }
}
