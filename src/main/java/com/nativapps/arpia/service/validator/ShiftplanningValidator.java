package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ShiftRequest;
import com.nativapps.arpia.model.dto.ShiftplanningRequest;
import com.nativapps.arpia.util.DateUtil;
import java.util.Calendar;
import javax.ws.rs.BadRequestException;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftplanningValidator extends Validator {

    private ShiftplanningValidator() {
    }

    /**
     * Evaluate if a shiftplanning object contains errors or missing
     * requirements to meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param request shiftplanning information to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateShiftplanning(ShiftplanningRequest request,
            ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        if (request == null)
            throw new BadRequestException(config
                    .getString("shiftplanning.is_null"));
        else {
            if (request.getInitialDate() == null)
                emd.addMessage(config.getString("shiftplanning.idate_required"));
            else if (DateUtil.isBeforeDay(request.getInitialDate(), Calendar.getInstance()))
                emd.addMessage(config.getString("shiftplanning.idate_earlier"));
            if (request.getFinalDate() == null)
                emd.addMessage(config.getString("shiftplanning.fdate_required"));
            else if (request.getInitialDate() != null && DateUtil
                    .isBeforeDay(request.getFinalDate(), request.getInitialDate()))
                emd.addMessage(config.getString("shiftplanning.fdate_earlier"));
            if (EntityControllerFactory.getShiftplanningController()
                    .findByDate(request.getInitialDate()) != null)
                emd.addMessage(config.getString("shiftplanning.date_scheduled"));
            if (request.getOperationId() == null || request.getOperationId() <= 0)
                emd.addMessage(config.getString("operation.id_required"));
            if (request.getShifts() == null)
                emd.addMessage(config.getString("shiftplanning.shifts_null"));
            else {
                for (ShiftRequest shift : request.getShifts()) {
                    ShiftValidator.evaluateShift(shift, request.getInitialDate(), 
                            request.getFinalDate(),  emd, config);
                }
            }
        }
    }
}
