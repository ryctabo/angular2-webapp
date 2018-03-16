package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.DomicileExecuteDao;
import com.nativapps.arpia.database.dao.MSettlementDao;
import com.nativapps.arpia.database.dao.ShiftCheckDao;
import com.nativapps.arpia.database.entity.*;
import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.service.converter.DomicileExecuteSimpleConverter;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.converter.MessengerSimpleConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.MSettlementValidator;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1
 */
public class MSettlementServiceImpl extends GenericService
        implements MSettlementService, DtoConverter<MSettlement, MSettlementRequest, MSettlementResponse> {

    private final MSettlementDao msController;

    private final DomicileExecuteDao deController;

    private final ShiftCheckDao chController;

    public MSettlementServiceImpl(MSettlementDao msController,
                                  DomicileExecuteDao deController,
                                  ShiftCheckDao chController) {
        this.msController = msController;
        this.deController = deController;
        this.chController = chController;
    }

    /**
     * Validate if <strong>MSettlementRequest</strong> contains all the necessary attributes.
     *
     * @param request request data
     */
    private void validateRequest(MSettlementRequest request) {
        int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        ErrorMessageData emd = new ErrorMessageData(statusCode);

        MSettlementValidator.evaluateSettlement(request, emd, config);

        if (!emd.getMessages().isEmpty())
            throw new ServiceException(emd);
    }

    /**
     * Returns all the domiciles execute in a shift check.
     *
     * @param clockIn     the check in date
     * @param clockOut    the check out date
     * @param messengerId the messenger ID
     * @return domicile execute list
     */
    private List<DomicileExecuteResponse> getDomicileExec(Calendar clockIn, Calendar clockOut, Long messengerId) {
        List<DomicileExecute> domiciles = deController.findAll(clockIn, clockOut, messengerId);

        List<DomicileExecuteResponse> responses = new ArrayList<>();
        for (DomicileExecute de : domiciles)
            responses.add(DomicileExecuteSimpleConverter.instance().convertToDto(de));

        return responses;
    }

    /**
     * Returns the shift check entity
     *
     * @param messengerId     messenger ID who belongs the shift
     * @param shiftIndex      shift index
     * @param shiftplanningId planning ID
     * @return shift check
     */
    private ShiftCheck getCheckEntity(Long messengerId, Integer shiftIndex, Long shiftplanningId) {
        Messenger messenger = new Messenger(messengerId);
        Shift shift = new Shift(shiftIndex, shiftplanningId);
        ShiftAssignment.ShiftAssignmentPK assignmentPK = new ShiftAssignment.ShiftAssignmentPK(messenger, shift);

        ShiftCheck check = chController.find(assignmentPK);
        if (check == null) {
            final String FORMAT = config.getString("shift_check.not_found");
            throw new BadRequestException(String.format(FORMAT, messengerId, shiftIndex, shiftplanningId));
        }

        return check;
    }

    @Override
    public ListResponse<MSettlementResponse> getAll(int start, int size, Long messengerId) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<MSettlementResponse> response = new ArrayList<>();
        for (MSettlement ms : msController.findAll(start, size, messengerId))
            response.add(convertToDto(ms));

        return new ListResponse<>(msController.count(messengerId), response);
    }

    @Override
    public MSettlementResponse get(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("msettle.id_required"));

        MSettlement mSettlement = msController.find(id);
        if (mSettlement == null) {
            String msg = String.format(config.getString("msettle.not_found"), id);
            throw new NotFoundException(msg);
        }

        ShiftCheck check = mSettlement.getCheck();
        Calendar clockIn = check.getClockIn();
        Calendar clockOut = check.getClockOut();
        long messengerId = check.getAssignment().getId().getMessenger().getId();

        List<DomicileExecuteResponse> deResponse = getDomicileExec(clockIn, clockOut, messengerId);

        MSettlementResponse response = convertToDto(mSettlement);
        response.setDomiciles(deResponse);

        return response;
    }

    @Override
    public MSettlementResponse add(MSettlementRequest request) {
        validateRequest(request);

        MSettlement mSettlement = convertToEntity(request);
        ShiftCheck check = getCheckEntity(request.getMessengerId(),
                request.getShiftIndex(),
                request.getShiftplanningId());

        if (check.getClockOut() == null)
            throw new BadRequestException(config.getString("msettle.not_clockout"));

        mSettlement.setCheck(check);

        MSettlementResponse response = convertToDto(msController.save(mSettlement));
        response.setDomiciles(getDomicileExec(check.getClockIn(), check.getClockOut(), request.getMessengerId()));

        return response;
    }

    @Override
    public MSettlementResponse pre(MSettlementRequest request) {
        validateRequest(request);

        MSettlementResponse response = convertToDto(convertToEntity(request));

        ShiftCheck check = getCheckEntity(request.getMessengerId(),
                request.getShiftIndex(),
                request.getShiftplanningId());

        Calendar clockIn = check.getClockIn();
        Calendar clockOut = check.getClockOut() == null ? Calendar.getInstance() : check.getClockOut();
        Long messengerId = request.getMessengerId();

        response.setDomiciles(getDomicileExec(clockIn, clockOut, messengerId));

        return response;
    }

    @Override
    public MSettlement convertToEntity(MSettlementRequest data) {
        MSettlement mSettlement = new MSettlement();
        mSettlement.setObservations(data.getObservations());
        mSettlement.setRegisterDate(Calendar.getInstance());
        return mSettlement;
    }

    @Override
    public MSettlementResponse convertToDto(MSettlement entity) {
        MSettlementResponse response = new MSettlementResponse();
        response.setId(entity.getId());

        ShiftCheck check = entity.getCheck();
        if (check != null) {
            Messenger messenger = check.getAssignment().getId().getMessenger();
            response.setMessenger(MessengerSimpleConverter.instance()
                    .convertToDto(messenger));
        }
        response.setObservations(entity.getObservations());

        return response;
    }
}
