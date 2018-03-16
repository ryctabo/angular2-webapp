package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.AssignmentDao;
import com.nativapps.arpia.database.dao.DomicileExecuteDao;
import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.entity.*;
import com.nativapps.arpia.model.dto.AssignmentRequest;
import com.nativapps.arpia.model.dto.AssignmentResponse;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.converter.MessengerSimpleConverter;
import com.nativapps.arpia.service.converter.UserSimpleConverter;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class AssignmentServiceImpl extends GenericService implements AssignmentService,
        DtoConverter<Assignment, AssignmentRequest, AssignmentResponse> {

    private final AssignmentDao controller;

    public AssignmentServiceImpl(AssignmentDao controller) {
        this.controller = controller;
    }

    @Override
    public List<AssignmentResponse> getAll(long domicileExeId) {
        List<AssignmentResponse> assignments = new ArrayList<>();
        for (Assignment assignment : controller.getAll(domicileExeId))
            assignments.add(convertToDto(assignment));
        return assignments;
    }

    @Override
    public AssignmentResponse get(Long domicileExeId, Integer index) {
        if (domicileExeId == null || domicileExeId <= 0)
            throw new BadRequestException(config.getString("dexecute.id"));

        if (index == null || index <= 0)
            throw new BadRequestException(config.getString("assignment.index"));

        Assignment assignment = controller.find(new Assignment.AssignmentPK(index, domicileExeId));
        if (assignment == null) {
            final String FORMAT = config.getString("assignment.not_found");
            throw new NotFoundException(String.format(FORMAT, index, domicileExeId));
        }

        return convertToDto(assignment);
    }

    @Override
    public AssignmentResponse assign(Long domicileExeId, AssignmentRequest data, UserInfo userInfo) {
        if (userInfo == null)
            throw new InternalServerErrorException(config.getString("user.info"));

        // validate domicile execute
        if (domicileExeId == null || domicileExeId <= 0)
            throw new BadRequestException(config.getString("dexecute.id"));

        DomicileExecuteDao dController = EntityControllerFactory.getDomicileExecuteController();
        DomicileExecute domicile = dController.find(domicileExeId);
        if (domicile == null) {
            final String FORMAT = config.getString("dexecute.not_found");
            throw new NotFoundException(String.format(FORMAT, domicileExeId));
        } else if (domicile.getStatus() == DomicileStatus.FINALIZED
                || domicile.getStatus() == DomicileStatus.CANCELLED) {
            throw new BadRequestException(config.getString("dexecute.candf"));
        }

        // validate messenger
        Long messengerId = data.getMessengerId();
        if (messengerId == null || messengerId <= 0)
            throw new BadRequestException(config.getString("messenger.id"));

        MessengerDao mController = EntityControllerFactory.getMessengerController();
        if (mController.find(messengerId) == null) {
            final String FORMAT = config.getString("messenger.not_found");
            throw new NotFoundException(String.format(FORMAT, messengerId));
        }

        Assignment assignment = convertToEntity(data);

        int index = controller.count(domicileExeId) + 1;
        if (index == 1) {
            domicile.setStartDate(Calendar.getInstance());
            domicile.setStatus(DomicileStatus.DISPATCHED);
        }

        assignment.setDomicile(domicile);
        assignment.setUser(new User(userInfo.getId()));
        assignment.getId().setIndex(index);

        return convertToDto(controller.save(assignment));
    }

    @Override
    public Assignment convertToEntity(AssignmentRequest data) {
        Assignment assignment = new Assignment();
        assignment.setMessenger(new Messenger(data.getMessengerId()));
        assignment.setMessengerId(data.getMessengerId());
        assignment.setRegisterDate(Calendar.getInstance());
        return assignment;
    }

    @Override
    public AssignmentResponse convertToDto(Assignment entity) {
        AssignmentResponse assignment = new AssignmentResponse();

        assignment.setIndex(entity.getId().getIndex());
        assignment.setRegisterDate(entity.getRegisterDate());

        MessengerDao mController = EntityControllerFactory.getMessengerController();
        assignment.setMessenger(MessengerSimpleConverter.instance()
                .convertToDto(mController.find(entity.getMessengerId())));

        assignment.setUser(UserSimpleConverter.instance()
                .convertToDto(entity.getUser()));

        return assignment;
    }
}
