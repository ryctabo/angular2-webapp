/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.DebtRequest;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.FeeInfoData;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.TextUtil;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class DebtValidator extends Validator {

    /**
     * Don't let anyone instantiate this class.
     */
    private DebtValidator() {
    }

    /**
     * Evaluate if the debt object contains errors or missing requeriments to
     * meet.
     *
     * @param request the debt request object
     * @param userInfo the user information
     * @param config the language configutarion
     */
    public static void validate(DebtRequest request, UserInfo userInfo, ConfigLanguage config) {
        if (userInfo == null)
            throw new InternalServerErrorException(config.getString("debt.user"));
        if (request == null) {
            throw new BadRequestException(config.getString("debt.is_null"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);

            if (request.getValue() == null || request.getValue() <= 0.0)
                emd.addMessage(config.getString("debt.value"));
            if (TextUtil.isEmpty(request.getConcept()))
                emd.addMessage(config.getString("debt.concept"));
            if (request.getOwnerId() == null || request.getOwnerId() <= 0)
                emd.addMessage(config.getString("debt.owner_id"));

            if (request.getFeeInfo() == null) {
                emd.addMessage(config.getString("debt.fee_info"));
            } else {
                FeeInfoData fee = request.getFeeInfo();

                if (fee.getNumberOfFees() == null || fee.getNumberOfFees() <= 0)
                    emd.addMessage(config.getString("debt_fee_info.number_of_fees"));
                if (fee.getPeriod() == null)
                    emd.addMessage(config.getString("debt_fee_info.period"));
                if (fee.getFirstPayment() == null)
                    emd.addMessage(config.getString("debt_fee_info.first_payment"));
            }

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }
    }
}
