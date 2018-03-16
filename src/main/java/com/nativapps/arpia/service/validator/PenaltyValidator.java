package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.PenaltyRequest;
import com.nativapps.arpia.service.exception.ServiceException;
import static com.nativapps.arpia.service.validator.Validator.missingParameters;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class PenaltyValidator {

    private PenaltyValidator() {
    }

    public static void evaluateCreation(PenaltyRequest request,
            ErrorMessageData emd, ConfigLanguage config)
            throws BadRequestException, ServiceException {
        missingParameters(config, emd);

        if (request.getDescription() == null)
            emd.addMessage(config.getString("penalty.description"));
        if (request.getMessengerId() == null || request.getMessengerId() <= 0)
            emd.addMessage(config.getString("penalty.messengerId"));
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
