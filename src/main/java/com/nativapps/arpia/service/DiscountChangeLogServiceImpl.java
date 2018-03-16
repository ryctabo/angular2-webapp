package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.DiscountChangeLogDao;
import com.nativapps.arpia.database.entity.DiscountChangeLog;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.dto.DiscountChangeLogResponse;
import com.nativapps.arpia.model.dto.UserResponse;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class DiscountChangeLogServiceImpl extends GenericService implements DiscountChangeLogService {

    private final DiscountChangeLogDao controller;

    public DiscountChangeLogServiceImpl(DiscountChangeLogDao controller) {
        this.controller = controller;
    }

    @Override
    public List<DiscountChangeLogResponse> getLogs(Long discountId) {

        if (discountId == null || discountId <= 0)
            throw new BadRequestException(config.getString("discount.id"));

        List<DiscountChangeLogResponse> changeLogs = new ArrayList<>();
        for (DiscountChangeLog change : controller.findAll(discountId)) {
            changeLogs.add(convertLogToDto(change));
        }
        return changeLogs;
    }

    @Override
    public DiscountChangeLogResponse getLog(Long discountId, Integer logIndex) {
        DiscountChangeLog log = null;

        if (discountId == null || discountId <= 0)
            throw new BadRequestException(config.getString("discount.id"));

        for (DiscountChangeLog change : controller.findAll(discountId)) {
            if (change.getId().getIndex() == logIndex) {
                log = change;
                break;
            }
        }

        if (logIndex == null || logIndex <= 0)
            throw new BadRequestException(config.getString("discount_log.index"));

        return convertLogToDto(log);
    }

    /**
     * Convert discount change log entity to discount change log response dto.
     *
     * @param log discount change log entity
     * @return discount change log response
     */
    public DiscountChangeLogResponse convertLogToDto(DiscountChangeLog log) {
        DiscountChangeLogResponse response = new DiscountChangeLogResponse();

        response.setIndex(log.getId().getIndex());
        response.setDate(log.getRegisterDate());
        response.setUser(convertToDtoUser(log.getUser()));

        return response;
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

}
