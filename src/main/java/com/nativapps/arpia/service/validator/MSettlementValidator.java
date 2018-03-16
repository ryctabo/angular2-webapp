package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.MSettlementRequest;

import javax.ws.rs.BadRequestException;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class MSettlementValidator extends Validator {

    /**
     * Don't let anyone instantiate this class.
     */
    private MSettlementValidator() { }

    /**
     * Evaluate if the messenger settlement object contains errors or missing
     * requeriments to meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param request messenger settlement to evaluate
     * @param emd     error message data
     * @param config  language configuration
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateSettlement(MSettlementRequest request, ErrorMessageData emd, ConfigLanguage config) {
        if (request == null)
            throw new BadRequestException(config.getString("msettle.is_null"));
        else {
            if (request.getMessengerId() == null || request.getMessengerId() <= 0)
                emd.addMessage(config.getString("messenger.id_required"));
            if (request.getShiftplanningId() == null || request.getShiftplanningId() <= 0)
                emd.addMessage(config.getString("shiftplanning.id_required"));
            if (request.getShiftIndex() == null || request.getShiftIndex() <= 0)
                emd.addMessage(config.getString("shift.index_required"));
        }
    }
}
