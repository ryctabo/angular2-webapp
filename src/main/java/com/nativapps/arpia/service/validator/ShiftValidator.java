package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ShiftRequest;
import static com.nativapps.arpia.service.validator.Validator.missingParameters;
import java.util.Calendar;
import javax.ws.rs.BadRequestException;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftValidator extends Validator {

    private ShiftValidator() {
    }

    /**
     * Evaluate if a shift object contains errors or missing requirements to
     * meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param request shift information to evaluate
     * @param idate initial date of shiftplanning
     * @param fdate final date of shiftplanning
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateShift(ShiftRequest request, Calendar idate,
            Calendar fdate, ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        if (request == null)
            throw new BadRequestException(config
                    .getString("shift.is_null"));
        else {
            if (request.getStartTime() == null)
                emd.addMessage(config.getString("shift.start_time_required"));
            if (request.getEndTime() == null)
                emd.addMessage(config.getString("shift.end_time_required"));
            else if (request.getStartTime() != null && idate != null && 
                    fdate != null && (request.getStartTime().before(idate) || 
                    request.getEndTime().after(fdate)))
                emd.addMessage(config.getString("shift.err_range"));
            if (request.getStartTime() != null && request.getEndTime() != null
                    && request.getEndTime().before(request.getStartTime()))
                emd.addMessage(config.getString("shift.incorrect_format"));
            if (request.getCount() == null)
                emd.addMessage(config.getString("shift.count_required"));
        }
    }
}
