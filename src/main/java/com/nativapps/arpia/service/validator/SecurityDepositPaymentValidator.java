package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.SecurityDepositPaymentRequest;
import static com.nativapps.arpia.service.validator.Validator.missingParameters;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class SecurityDepositPaymentValidator {

    private SecurityDepositPaymentValidator() {
    }

    /**
     * Evaluate if the Security deposit object contains errors or missing
     * requeriments to meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param request Security deposit to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluate(SecurityDepositPaymentRequest request,
            ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        if (request.getPayment() == null || request.getPayment() <= 0)
            emd.addMessage(config.getString("securityDeposit.payment"));
    }
}
