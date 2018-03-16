/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.PaymentData;
import com.nativapps.arpia.rest.UserInfo;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class PaymentValidator extends Validator {

    /**
     * Don't let anyone instantiate this class.
     */
    private PaymentValidator() {
    }

    /**
     * Validate if an payment have all the attibutes for save to database.
     *
     * @param request payment request data
     * @param userInfo user information
     * @param config The language configuration
     */
    public static void validate(PaymentData request, UserInfo userInfo, ConfigLanguage config) {
        if (userInfo == null)
            throw new InternalServerErrorException(config.getString("payment.user"));
        if (request == null) {
            throw new BadRequestException(config.getString("payment.is_null"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);

            if (request.getPayment() == null || request.getPayment() <= 0.0)
                emd.addMessage(config.getString("payment.payment"));
        }
    }
}
