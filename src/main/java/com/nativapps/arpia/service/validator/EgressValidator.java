/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.entity.CustomerData;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.Operation;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.util.TextUtil;

import javax.validation.constraints.NotNull;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0.1
 */
public class EgressValidator extends Validator {

    /**
     * Don't let anyone instantiate this class.
     */
    private EgressValidator() { }

    /**
     * Validate if an egress have all the attributes for save to database.
     *
     * @param emd     error message data object
     * @param request egress data object
     * @param config  config language
     */
    public static void validate(@NotNull EgressData request,
                                ErrorMessageData emd,
                                ConfigLanguage config) {
        missingParameters(config, emd);

        //Validate generic properties
        if (request.getValue() == null || request.getValue() <= 0d)
            emd.addMessage(config.getString("egress.value"));
        if (TextUtil.isEmpty(request.getConcept()))
            emd.addMessage(config.getString("egress.concept"));

        //Validate operation
        Long operationId = request.getOperationId();
        if (operationId == null || operationId <= 0) {
            emd.addMessage(config.getString("operation.id_required"));
        } else {
            //Dependency with operation controller
            Operation operation = EntityControllerFactory
                    .getOperationController()
                    .find(operationId);
            if (operation == null) {
                final String FORMAT = config.getString("operation.not_found");
                emd.addMessage(String.format(FORMAT, operationId));
            }
        }

        //Validate depending on the egress type
        if (request instanceof CustomerEgressData) {
            CustomerEgressData customerEgress = (CustomerEgressData) request;
            //Validate if exists the customer
            Long customerId = customerEgress.getCustomerId();
            if (customerId == null || customerId <= 0) {
                emd.addMessage(config.getString("customer.id_required"));
            } else {
                //Dependency with customer controller
                CustomerData customer = EntityControllerFactory
                        .getCustomerDataController()
                        .find(customerId);
                if (customer == null) {
                    final String FORMAT = config.getString("customer.not_found");
                    emd.addMessage(String.format(FORMAT, customerId));
                }
            }
        } else if (request instanceof ManagementEgressData) {
            ManagementEgressData management = (ManagementEgressData) request;
            if (TextUtil.isEmpty(management.getIdentification()))
                emd.addMessage(config.getString("egress_management.identification"));
            if (TextUtil.isEmpty(management.getName()))
                emd.addMessage(config.getString("egress_management.name"));
            if (TextUtil.isEmpty(management.getLastName()))
                emd.addMessage(config.getString("egress_management.last_name"));
        } else if (request instanceof MessengerEgressData) {
            MessengerEgressData messengerEgress = (MessengerEgressData) request;
            if (messengerEgress.getTypeConcept() == null)
                emd.addMessage(config.getString("egress_messenger.type_concept"));

            //Validate if exists the messenger
            Long messengerId = messengerEgress.getMessengerId();
            if (messengerId == null || messengerId <= 0) {
                emd.addMessage(config.getString("messenger.id_required"));
            } else {
                //Dependency with messenger controller
                Messenger messenger = EntityControllerFactory
                        .getMessengerController()
                        .find(messengerId);
                if (messenger == null) {
                    final String FORMAT = config.getString("messenger.not_found");
                    emd.addMessage(String.format(FORMAT, messengerId));
                }
            }
            //Validate fee information
            validateFeeInfo(messengerEgress.getFeeInfo(), emd, config);
        } else if (request instanceof OperatorEgressData) {
            OperatorEgressData operator = (OperatorEgressData) request;
            if (operator.getTypeConcept() == null)
                emd.addMessage(config.getString("egress_operator.type_concept"));

            //Validate if exists the user
            Long operatorId = operator.getOperatorId();
            if (operatorId == null || operatorId <= 0) {
                emd.addMessage(config.getString("egress_operator.operator_id"));
            } else {
                //Dependency with user controller
                User user = EntityControllerFactory
                        .getUserController()
                        .find(operatorId);
                if (user == null) {
                    final String FORMAT = config.getString("user.not_found");
                    emd.addMessage(String.format(FORMAT, operatorId));
                }
            }
            //Validate fee information
            validateFeeInfo(operator.getFeeInfo(), emd, config);
        }
    }

    /**
     * Validate if an egress have all the attributes for save to database.
     *
     * @param feeInfo fee information
     * @param emd     error message data
     * @param config  configuration language object
     */
    private static void validateFeeInfo(FeeInfoData feeInfo, ErrorMessageData emd, ConfigLanguage config) {
        if (feeInfo == null) {
            emd.addMessage(config.getString("egress.fee_info"));
        } else {
            if (feeInfo.getFirstPayment() == null)
                emd.addMessage(config.getString("egress.first_payment"));
            if (feeInfo.getNumberOfFees() == null || feeInfo.getNumberOfFees() <= 0)
                emd.addMessage(config.getString("egress.number_of_fees"));
            if (feeInfo.getPeriod() == null)
                emd.addMessage(config.getString("egressr.period"));
        }
    }
}
