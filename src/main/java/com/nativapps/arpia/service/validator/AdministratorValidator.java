package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.database.dao.AdministratorDao;
import com.nativapps.arpia.database.dao.EmailDao;
import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.PhoneDao;
import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.AdministratorRequest;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import static com.nativapps.arpia.service.validator.Validator.missingParameters;
import com.nativapps.arpia.util.TextUtil;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class AdministratorValidator extends Validator {

    /**
     * Don't let anyone instantiate this class.
     */
    private AdministratorValidator() {
    }

    /**
     * Evaluate if the administrator object contains errors or missing
     * requeriments to meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param admin administrator information to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateAdministrator(AdministratorRequest admin,
            ErrorMessageData emd, ConfigLanguage config) {

        AdministratorDao adminDao = EntityControllerFactory
                .getAdministratorController();
        if (TextUtil.isEmpty(admin.getIdentification()))
            emd.addMessage(config.getString("person.identification"));
        else if (adminDao.findByIdentification(admin.getIdentification()) != null)
            emd.addMessage(config.getString("person.identification_exist"));

        if (TextUtil.isEmpty(admin.getName()))
            emd.addMessage(config.getString("person.name"));
        if (TextUtil.isEmpty(admin.getLastName()))
            emd.addMessage(config.getString("person.last_name"));
        if (admin.getBirthday() == null)
            emd.addMessage(config.getString("person.birthday"));
        if (admin.getGender() == null)
            emd.addMessage(config.getString("person.gender"));
        
        //Evaluate email address
        evaluateEmail(admin.getEmail(), emd, config);
        //Evaluate phone number
        evaluatePhones(admin.getPhone(), emd, config);

        if (admin.getLinked() == null)
            emd.addMessage(config
                    .getString("administrator.state_required"));
    }

    /**
     * Evaluate if administrator's email contains errors or missing requeriments 
     * to meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param emails administrator's email to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    private static void evaluateEmail(String email,
            ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);
        
        if (TextUtil.isEmpty(email))
            emd.addMessage(config.getString("email.address_null"));

        if (!TextUtil.isEmail(email)) {
            String msg = String.format(config.getString("email.address_valid"),
                    email);
            emd.addMessage(msg);
        }

        EmailDao controller = EntityControllerFactory.getEmailController();
        if (controller.exists(email)) {
            String msg = String.format(config.getString("email.a_exists"),
                    email);
            emd.addMessage(msg);
        }
    }

    /**
     * Evaluate if the administrator's phone contains errors or missing 
     * requeriments to meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param phones administrator's phone to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    private static void evaluatePhones(String phone,
            ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        if (TextUtil.isEmpty(phone))
            emd.addMessage(config.getString("phone.number_null"));

        if (!TextUtil.isPhone(phone)) {
            String msg = String.format(config.getString("phone.is_valid"),
                    phone);
            emd.addMessage(msg);
        }

        PhoneDao controller = EntityControllerFactory.getPhoneController();
        if (controller.exists(phone)) {
            String msg = String.format(config.getString("phone.n_exists"),
                    phone);
            emd.addMessage(msg);
        }
    }

}
