package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.GoodStandingDao;
import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.dao.OperationDao;
import com.nativapps.arpia.database.entity.GoodStanding;
import com.nativapps.arpia.database.entity.InventoryInfo;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.Operation;
import com.nativapps.arpia.mail.MailMessage;
import com.nativapps.arpia.mail.MailSender;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.GoodStandingRequest;
import com.nativapps.arpia.model.dto.GoodStandingResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MessengerResponse;
import com.nativapps.arpia.model.dto.OperationResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class GoodStandingServiceImpl extends GenericService implements
        GoodStandingService, DtoConverter<GoodStanding, GoodStandingRequest, GoodStandingResponse> {

    private final GoodStandingDao dao;

    private final OperationDao operationDao;

    private final MessengerDao messengerDao;

    public GoodStandingServiceImpl(GoodStandingDao dao, OperationDao operationDao,
            MessengerDao messengerDao) {
        this.dao = dao;
        this.operationDao = operationDao;
        this.messengerDao = messengerDao;
    }

    @Override
    public ListResponse<GoodStandingResponse> getAll(int start, int size,
            Long messengerId) {
        if (start < 0) {
            throw new BadRequestException(config.getString("pagination.start"));
        }
        if (size <= 0) {
            throw new BadRequestException(config.getString("pagination.size"));
        }

        List<GoodStandingResponse> response = new ArrayList<>();
        for (GoodStanding goodStanding : dao.findAll(start, size, messengerId)) {
            response.add(convertToDto(goodStanding));
        }

        return new ListResponse<>(dao.findAllCount(messengerId), response);
    }

    @Override
    public GoodStandingResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    private GoodStanding getEntity(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException(config.getString("good_standing.id_required"));
        }

        GoodStanding entity = dao.find(id);
        if (entity == null) {
            String err = String.format(config.getString("good_standing.not_found"), id);
            throw new NotFoundException(err);
        }

        return entity;
    }

    @Override
    public GoodStandingResponse add(GoodStandingRequest data) {
        if (data == null) {
            throw new BadRequestException(config.getString("good_standing.is_null"));
        } else {
            ErrorMessageData emd = new ErrorMessageData(Response.Status.BAD_REQUEST
                    .getStatusCode());
            if (data.getMessenger() == null) {
                emd.addMessage(config.getString("messenger.id_required"));
            }
            if (data.getOperation() == null) {
                emd.addMessage(config.getString("operation.id_required"));
            }
            if (data.getReason() == null) {
                emd.addMessage(config.getString("good_standing.reason_required"));
            }

            if (!emd.getMessages().isEmpty()) {
                throw new ServiceException(emd);
            }
        }

        Operation operation = operationDao.find(data.getOperation());
        if (operation == null) {
            String err = String.format(config.getString("operation.not_found"),
                    data.getOperation());
            throw new BadRequestException(err);
        }

        Messenger messenger = messengerDao.find(data.getMessenger());
        if (messenger == null) {
            String err = String.format(config.getString("messenger.not_found"),
                    data.getMessenger());
            throw new BadRequestException(err);
        }

        List<InventoryInfo> inventory = messengerDao
                .getInventoryInfo(data.getMessenger());
        for (InventoryInfo inv : inventory) {
            if (inv.getLoans() != inv.getReturns()) {
                throw new BadRequestException(config
                        .getString("good_standing.inventory"));
            }
        }

        GoodStanding entity = new GoodStanding();
        entity.setReason(data.getReason());
        entity.setOperation(operation);
        entity.setMessenger(messenger);
        entity.setRegisterDate(Calendar.getInstance());
        entity = dao.save(entity);
        
        MailMessage.Builder builder = new MailMessage.Builder("gs-req-template");
        builder.replace("messenger_name", entity.getMessenger().getName());
        builder.replace("reason", entity.getReason());
        builder.replace("admin_email", entity.getOperation().getAdminUser().getEmail());
        builder.replace("operation_name", entity.getOperation().getName());
        builder.replace("year", Calendar.getInstance().get(Calendar.YEAR));
        
        try {
            MailSender.getInstance().body(builder.build())
                    .subject("Solicitud paz y salvo")
                    .to(entity.getMessenger().getEmails().get(0).getAddress())
                    .send();
        } catch (MessagingException ex) {
            throw new InternalServerErrorException("Error while the mail is sending: " + ex
                    .getCause().getMessage());
        }

        return convertToDto(entity);
    }

    @Override
    public GoodStandingResponse approve(Long id, GoodStandingRequest data) {
        if (data == null) {
            throw new BadRequestException(config.getString("good_standing.is_null"));
        } else if (data.getRefundDate() == null) {
            throw new BadRequestException(config.getString("good_standing.refund_date_required"));
        }

        GoodStanding entity = getEntity(id);
        if (entity.getApproved() != null) {
            throw new BadRequestException(config.getString("good_standing.evaluation_performed"));
        }

        entity.setRefundDate(data.getRefundDate());
        entity.setObservations(data.getObservations());
        entity.setMessage(data.getMessage());
        entity.setApproved(true);
        entity.setApprovalDate(Calendar.getInstance());
        entity = dao.save(entity);
        
        MailMessage.Builder builder = new MailMessage.Builder("gs-res-template");
        builder.replace("messenger_name", entity.getMessenger().getName());
        builder.replace("state", "aprobado");
        builder.replace("message", entity.getMessage());
        builder.replace("admin_email", entity.getOperation().getAdminUser().getEmail());
        builder.replace("operation_name", entity.getOperation().getName());
        builder.replace("year", Calendar.getInstance().get(Calendar.YEAR));
        
        try {
            MailSender.getInstance().body(builder.build())
                    .subject("Solicitud paz y salvo")
                    .to(entity.getMessenger().getEmails().get(0).getAddress())
                    .send();
        } catch (MessagingException ex) {
            throw new InternalServerErrorException("Error while the mail is sending: " + ex
                    .getCause().getMessage());
        }

        return convertToDto(entity);
    }

    @Override
    public GoodStandingResponse deny(Long id, GoodStandingRequest data) {
        if (data == null) {
            throw new BadRequestException(config.getString("good_standing.is_null"));
        }

        GoodStanding entity = getEntity(id);
        if (entity.getApproved() != null) {
            throw new BadRequestException(config.getString("good_standing.evaluation_permormed"));
        }

        entity.setObservations(data.getObservations());
        entity.setMessage(data.getMessage());
        entity.setApproved(false);
        entity.setApprovalDate(Calendar.getInstance());
        
        MailMessage.Builder builder = new MailMessage.Builder("gs-res-template");
        builder.replace("messenger_name", entity.getMessenger().getName());
        builder.replace("state", "denegado");
        builder.replace("message", entity.getMessage());
        builder.replace("admin_email", entity.getOperation().getAdminUser().getEmail());
        builder.replace("operation_name", entity.getOperation().getName());
        builder.replace("year", Calendar.getInstance().get(Calendar.YEAR));
        
        try {
            MailSender.getInstance().body(builder.build())
                    .subject("Solicitud paz y salvo")
                    .to(entity.getMessenger().getEmails().get(0).getAddress())
                    .send();
        } catch (MessagingException ex) {
            throw new InternalServerErrorException("Error while the mail is sending: " + ex
                    .getCause().getMessage());
        }

        return convertToDto(dao.save(entity));
    }

    @Override
    public GoodStanding convertToEntity(GoodStandingRequest data) {
        throw new UnsupportedOperationException("Unsupported Operation");
    }

    @Override
    public GoodStandingResponse convertToDto(GoodStanding entity) {
        GoodStandingResponse response = new GoodStandingResponse();

        response.setId(entity.getId());
        response.setObservations(entity.getObservations());
        response.setMessage(entity.getMessage());
        response.setReason(entity.getReason());
        response.setRefundDate(entity.getRefundDate());
        response.setRegisterDate(entity.getRegisterDate());
        response.setApproved(entity.getApproved());
        response.setApprovalDate(entity.getApprovalDate());

        Messenger messenger = entity.getMessenger();
        response.setMessenger(new MessengerResponse(messenger.getId(),
                messenger.getIdentification(), messenger.getName(),
                messenger.getLastName(), messenger.getGender(),
                messenger.getBirthday()));
        response.setOperation(new OperationResponse(entity.getOperation().getId(),
                entity.getOperation().getName()));

        return response;
    }
}
