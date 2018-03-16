package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.dao.MobileNumberDao;
import com.nativapps.arpia.database.dao.OperationDao;
import com.nativapps.arpia.database.dao.UserDao;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.MobileNumber;
import com.nativapps.arpia.database.entity.Operation;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.MessengerResponse;
import com.nativapps.arpia.model.dto.MobileNumberRequest;
import com.nativapps.arpia.model.dto.MobileNumberResponse;
import com.nativapps.arpia.service.converter.DtoConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 2.0.1
 */
public class MobileNumberServiceImpl extends GenericService implements MobileNumberService,
        DtoConverter<MobileNumber, MobileNumberRequest, MobileNumberResponse> {

    private final MobileNumberDao controller;

    public MobileNumberServiceImpl(MobileNumberDao controller) {
        this.controller = controller;
    }

    /**
     * Get operation entity from the given ID.
     *
     * @param operationId operation ID
     * @return operation data
     */
    private Operation getOperationEntity(Long operationId) {
        if (operationId == null || operationId <= 0)
            throw new BadRequestException(config.getString("operation.id_required"));

        OperationDao oController = EntityControllerFactory.getOperationController();
        Operation operation = oController.find(operationId);

        if (operation == null) {
            final String FORMAT = config.getString("operation.not_found");
            throw new BadRequestException(String.format(FORMAT, operationId));
        }

        return operation;
    }

    /**
     * Get messenger entity from the given ID.
     *
     * @param messengerId the messenger ID
     * @return messenger data
     */
    private Messenger getMessengerEntity(Long messengerId) {
        if (messengerId == null || messengerId <= 0)
            throw new BadRequestException(config.getString("meesenger.id_required"));

        MessengerDao mController = EntityControllerFactory.getMessengerController();
        Messenger messenger = mController.find(messengerId);

        if (messenger == null) {
            final String FORMAT = config.getString("messenger.not_found");
            throw new BadRequestException(String.format(FORMAT, messengerId));
        }

        return messenger;
    }

    /**
     * Get mobile number entity from the given ID.
     *
     * @param index mobile number ID
     * @return mobile number entity
     *
     * @throws BadRequestException if mobile number ID is null or less than or
     * equal to 0
     * @throws NotFoundException if the mobile number obtained is null
     */
    private MobileNumber getEntity(Long operationId, Integer index)
            throws BadRequestException, NotFoundException {
        if (index == null || index <= 0) {
            String msg = config.getString("mobile_number.index_required");
            throw new BadRequestException(msg);
        }
        if (operationId == null || operationId <= 0) {
            String msg = config.getString("mobile_number.operationid");
            throw new BadRequestException(msg);
        }

        MobileNumber.MobileNumberPK primaryKey
                = new MobileNumber.MobileNumberPK(index, operationId);
        MobileNumber mobileNumber = controller.find(primaryKey);
        if (mobileNumber == null) {
            final String FORMAT = config.getString("mobile_number.not_found");
            throw new NotFoundException(String.format(FORMAT, index, operationId));
        }

        return mobileNumber;
    }

    @Override
    public void add(MobileNumberRequest mNumber) {
        if (mNumber == null) {
            String msg = config.getString("mobile_number.is_required");
            throw new BadRequestException(msg);
        }
        if (mNumber.getQuantity() == null || mNumber.getQuantity() <= 0) {
            String msg = config.getString("mobile_number.count");
            throw new BadRequestException(msg);
        }

        //Get operation from the given ID
        Operation operation = getOperationEntity(mNumber.getOperationId());
        //Get the last mobile number from the given operation ID.
        MobileNumber mn = controller.findLast(mNumber.getOperationId());

        Set<MobileNumber> mobileNumbers = new HashSet<>();
        int index = mn == null ? 1 : mn.getId().getIndex() + 1;
        int lastNumber = index + mNumber.getQuantity();
        while (index < lastNumber) {
            mobileNumbers.add(new MobileNumber(index++, operation));
        }

        //Save all mobile numbers
        controller.save(mobileNumbers.toArray(new MobileNumber[mobileNumbers.size()]));
    }

    @Override
    public List<MobileNumberResponse> getAll(Long operationId, Boolean available,
            int start, int size, String orderBy, OrderType orderType) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<MobileNumberResponse> mobileNumbers = new ArrayList<>();
        List<MobileNumber> eMobileNumbers = controller.findAll(operationId,
                available, start, size, orderBy, orderType);

        for (MobileNumber mobileNumber : eMobileNumbers)
            mobileNumbers.add(convertToDto(mobileNumber));

        return mobileNumbers;
    }

    @Override
    public MobileNumberResponse update(Integer index, MobileNumberRequest data) {
        MobileNumber current = getEntity(data.getOperationId(), index);

        //Asign mobile number to messenger
        Messenger messenger = getMessengerEntity(data.getMessenger());
        current.setMessenger(messenger);
        current.getMessenger().setMobile(current);

        //Change username to messenger
        UserDao uController = EntityControllerFactory.getUserController();
        User user = uController.find(messenger.getUserId());
        Operation o = getOperationEntity(current.getId().getOperationId());
        user.setUsername(String.format("%s-%d", o.getAlias(), current.getId()
                .getIndex()));
        uController.save(user);

        current.setUpdateDate(Calendar.getInstance());
        return convertToDto(controller.save(current));
    }

    @Override
    public MobileNumber convertToEntity(MobileNumberRequest d) {
        return null;
    }

    @Override
    public MobileNumberResponse convertToDto(MobileNumber e) {
        Messenger eMessenger = e.getMessengerId() != null
                ? getMessengerEntity(e.getMessengerId()) : e.getMessenger();
        MessengerResponse messenger = null;
        if (eMessenger != null) {
            messenger = new MessengerResponse();
            messenger.setId(eMessenger.getId());
            messenger.setIdentification(eMessenger.getIdentification());
            messenger.setName(eMessenger.getName());
            messenger.setLastName(eMessenger.getLastName());
            messenger.setGender(eMessenger.getGender());
            messenger.setBirthday(eMessenger.getBirthday());
            messenger.setPhoto(eMessenger.getPhoto());
            messenger.setCategory(eMessenger.getCategory());
        }

        return new MobileNumberResponse(messenger, e.getCreateDate(),
                e.getUpdateDate(), e.getId().getIndex());
    }

}
