package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.PeaceAndSafetyRequest;
import com.nativapps.arpia.service.exception.ServiceException;
import static com.nativapps.arpia.service.validator.Validator.missingParameters;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public class PeaceAndSafetyValidator {

    private PeaceAndSafetyValidator() {
    }

    public static void evaluateCreation(PeaceAndSafetyRequest request,
            ErrorMessageData emd, ConfigLanguage config)
            throws BadRequestException, ServiceException {
        missingParameters(config, emd);

        if (request.getRefund() == null)
            emd.addMessage(
                    config.getString("peaceAndSafety.refund_date_required"));
    }

    public static void evaluatePagination(int start, int size,
            ErrorMessageData emd, ConfigLanguage config) {
        if (start < 0) {
            emd.addMessage(config.getString("pagination.start"));
        }
        if (size <= 0) {
            emd.addMessage(config.getString("pagination.size"));
        }
        if (!emd.getMessages().isEmpty()) {
            emd.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
            throw new ServiceException(emd);
        }
    }
}
