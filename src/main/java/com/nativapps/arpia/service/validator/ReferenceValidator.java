package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ReferenceRequest;
import com.nativapps.arpia.util.TextUtil;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1
 */
public class ReferenceValidator extends Validator {

    /**
     * Don't let anyone instantiate this class.
     */
    private ReferenceValidator() { }

    /**
     * Evaluate if the reference object contains errors or missing requeriments
     * to meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param reference reference to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateReference(ReferenceRequest reference,
            ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        if (TextUtil.isEmpty(reference.getName()))
            emd.addMessage(config.getString("reference.name"));
        if (TextUtil.isEmpty(reference.getLastName()))
            emd.addMessage(config.getString("reference.lastname"));
        if (TextUtil.isEmpty(reference.getAddress()))
            emd.addMessage(config.getString("reference.address"));

        if (TextUtil.isEmpty(reference.getPhoneNumber()))
            emd.addMessage(config.getString("reference.phone"));
        else if (!TextUtil.isPhone(reference.getPhoneNumber()))
            emd.addMessage(config.getString("reference.is_phone"));

        if (reference.getType() == null)
            emd.addMessage(config.getString("reference.type"));
        if (reference.getRelationship() == null)
            emd.addMessage(config.getString("reference.relationship"));
    }

}
