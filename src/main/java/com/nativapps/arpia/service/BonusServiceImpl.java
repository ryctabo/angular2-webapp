package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.BonusDao;
import com.nativapps.arpia.database.dao.CustomerDataDao;
import com.nativapps.arpia.database.entity.Bonus;
import com.nativapps.arpia.database.entity.CustomerData;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.dto.BonusRequest;
import com.nativapps.arpia.model.dto.BonusResponse;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.DateUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

public class BonusServiceImpl extends GenericService implements BonusService,
        DtoConverter<Bonus, BonusRequest, BonusResponse> {

    private final BonusDao bonusDao = EntityControllerFactory
            .getBonusController();

    private final CustomerDataDao customerDataDao = EntityControllerFactory
            .getCustomerDataController();

    @Override
    public ListResponse<BonusResponse> getAll(int start, int size, Long customerId) {
        ErrorMessageData errors = new ErrorMessageData(
                Response.Status.BAD_REQUEST.getStatusCode());

        if (customerId == null)
            errors.addMessage(config.getString("customer.id_required"));
        if (start < 0)
            errors.addMessage(config.getString("pagination.start"));
        if (size <= 0)
            errors.addMessage(config.getString("pagination.size"));

        if (!errors.getMessages().isEmpty())
            throw new ServiceException(errors);

        CustomerData customerData = customerDataDao.find(customerId);

        if (customerData == null)
            throw new NotFoundException(String.format(config
                    .getString("customer.not_found"), customerId));

        List<BonusResponse> response = new ArrayList<>();
        List<Bonus> result = bonusDao.findAllByCustomerIdPag(start, size,
                customerId);
        for (Bonus bonus : result) {
            response.add(convertToDto(bonus));
        }

        return new ListResponse<>(bonusDao.findAllCount(customerId), response);
    }

    public Bonus getEntity(Long customerId, Long bonusId) {
        if (customerId == null)
            throw new BadRequestException(config
                    .getString("customer.id_required"));
        if (bonusId == null)
            throw new BadRequestException(config
                    .getString("bonus.id_required"));

        CustomerData customerData = customerDataDao.find(customerId);

        if (customerData == null)
            throw new NotFoundException(String.format(config
                    .getString("customer.not_found"), customerId));

        Bonus bonus = bonusDao.findByCustomerId(customerId, bonusId);

        if (bonus == null)
            throw new NotFoundException(String.format(config
                    .getString("bonus.not_found"), customerId, bonusId));

        return bonus;
    }

    @Override
    public BonusResponse get(Long customerId, Long bonusId) {
        return convertToDto(getEntity(customerId, bonusId));
    }

    @Override
    public BonusResponse add(Long customerId, Long authorizedBy,
            BonusRequest bonus) {
        ErrorMessageData errors = new ErrorMessageData(
                Response.Status.BAD_REQUEST.getStatusCode());

        if (customerId == null)
            throw new BadRequestException(config
                    .getString("customer.id_required"));
        if (bonus == null)
            throw new BadRequestException(config
                    .getString("bonus.is_null"));
        else {
            if (bonus.getCountFreeService() == null)
                errors.addMessage(config
                        .getString("bonus.count_free_service_null"));
            if (bonus.getCountFreeService() == null)
                errors.addMessage(config
                        .getString("bonus.count_free_service_used_null"));
            if (bonus.getReason() == null)
                errors.addMessage(config.getString("bonus.reason_required"));
            if (bonus.getExpiryDate() == null || DateUtil.isBeforeToday(bonus
                    .getExpiryDate()))
                errors.addMessage(config.getString("bonus.expiry_date_required"));
        }

        if (!errors.getMessages().isEmpty())
            throw new ServiceException(errors);

        CustomerData customerData = customerDataDao.find(customerId);

        if (customerData == null)
            throw new NotFoundException(String.format(config
                    .getString("customer.not_found"), customerId));

        Bonus entity = convertToEntity(bonus);
        entity.setRegisterDate(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        entity.setAvailable(true);
        entity.setAuthorizedBy(new User(authorizedBy));

        Bonus lastBonus = bonusDao.findLastBonusByCustomerId(customerId);
        if (lastBonus != null) {
            lastBonus.setAvailable(false);
            bonusDao.save(lastBonus);
        }

        entity.setCustomerData(customerData);

        return convertToDto(bonusDao.save(entity));
    }

    @Override
    public Bonus convertToEntity(BonusRequest data) {
        Bonus bonus = new Bonus();
        bonus.setReason(data.getReason());
        bonus.setCountFreeService(data.getCountFreeService());
        bonus.setExpiryDate(data.getExpiryDate());

        return bonus;
    }

    @Override
    public BonusResponse convertToDto(Bonus entity) {
        BonusResponse response = new BonusResponse();

        response.setId(entity.getId());
        response.setAuthorizedBy(entity.getAuthorizedBy().getId());
        response.setAvailable(entity.isAvailable());
        response.setCountFreeServiceAvailable(entity.getCountFreeService()
                - entity.getCountFreeServiceUsed());
        response.setCountFreeService(entity.getCountFreeService());
        response.setCountFreeServiceUsed(entity.getCountFreeServiceUsed());
        response.setExpiryDate(entity.getExpiryDate());
        response.setReason(entity.getReason());
        response.setRegisterDate(entity.getRegisterDate());

        return response;
    }
}
