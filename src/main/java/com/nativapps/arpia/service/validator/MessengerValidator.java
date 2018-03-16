package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.OperationDao;
import com.nativapps.arpia.database.dao.PersonDao;
import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.AddressRequest;
import com.nativapps.arpia.model.dto.CurriculumVitaeRequest;
import com.nativapps.arpia.model.dto.EmailRequest;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.HomeData;
import com.nativapps.arpia.model.dto.MessengerRequest;
import com.nativapps.arpia.model.dto.PhoneRequest;
import com.nativapps.arpia.model.dto.ReferenceRequest;
import com.nativapps.arpia.model.dto.SocialSecurityData;
import com.nativapps.arpia.util.TextUtil;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.1
 */
public class MessengerValidator extends Validator {

    /**
     * Don't let anyone instantiate this class.
     */
    private MessengerValidator() {}

    /**
     * Evaluate if the messenger object contains errors or missing requeriments
     * to meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param messenger messenger to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateMessenger(MessengerRequest messenger,
            ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        //evaluate person data
        PersonValidator.evaluatePerson(messenger, emd, config);

        //evaluate addresses data
        List<AddressRequest> addresses = messenger.getAddresses();
        evaluateAddresses(addresses, emd, config);

        //evaluate emails data
        List<EmailRequest> emails = messenger.getEmails();
        evaluateEmails(emails, emd, config);

        //evaluate phones data
        List<PhoneRequest> phones = messenger.getPhones();
        evaluatePhones(phones, emd, config);

        //evaluate messenger data
        if (TextUtil.isEmpty(messenger.getPhoto()))
            emd.addMessage(config.getString("messenger.photo"));
        if (TextUtil.isEmpty(messenger.getPassword()))
            emd.addMessage(config.getString("messenger.password"));

        OperationDao controller = EntityControllerFactory.getOperationController();
        if (messenger.getOperationId() == null || messenger.getOperationId() <= 0) {
            emd.addMessage(config.getString("messenger.operation_id"));
        } else if (controller.find(messenger.getOperationId()) == null) {
            String msg = String.format(config.getString("operation.not_found"),
                    messenger.getOperationId());
            emd.addMessage(msg);
        }

        //evaluate curriculum vitae data
        CurriculumVitaeRequest cv = messenger.getCurriculumVitae();
        evaluateCurriculumVitaeData(cv, emd, config);

        //Missing validate the references
    }

    /**
     * Evaluate if the addresses list contains errors or missing requeriments to
     * meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param addresses addresses list to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    private static void evaluateAddresses(List<AddressRequest> addresses,
            ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        if (addresses != null && !addresses.isEmpty()) {
            for (AddressRequest address : addresses) {
                PersonValidator.evaluateAddress(address, emd, config);
            }
        } else {
            emd.addMessage(config.getString("addres.at_least"));
        }
    }

    /**
     * Evaluate if the list of emails contains errors or missing requeriments to
     * meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param emails list of emails to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    private static void evaluateEmails(List<EmailRequest> emails,
            ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        if (emails != null && !emails.isEmpty()) {
            for (EmailRequest email : emails) {
                PersonValidator.evaluateEmail(email, emd, config);
            }
        } else {
            emd.addMessage(config.getString("email.address_required"));
        }
    }

    /**
     * Evaluate if the list of phones contains errors or missing requeriments to
     * meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param phones list of phones to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    private static void evaluatePhones(List<PhoneRequest> phones,
            ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        if (phones != null && !phones.isEmpty()) {
            for (PhoneRequest phone : phones) {
                PersonValidator.evaluatePhone(phone, emd, config);
            }
        } else {
            emd.addMessage(config.getString("phone.at_least"));
        }
    }

    /**
     * Evaluate if the curriculum vitae object contains errors or missing
     * requeriments to meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param cv curriculum vitae to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateCurriculumVitaeData(CurriculumVitaeRequest cv,
            ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        if (cv != null) {
            if (cv.getAcademicLevel() == null)
                emd.addMessage(config.getString("curriculum_vitae.academy"));
            if (TextUtil.isEmpty(cv.getBirthCity()))
                emd.addMessage(config.getString("curriculum_vitae.birthcity"));
            if (cv.getCivilStatus() == null)
                emd.addMessage(config.getString("curriculum_vitae.civil_status"));

            if (cv.getReferences() != null && !cv.getReferences().isEmpty())
                evaluateReferences(cv.getReferences(), emd, config);

            HomeData home = cv.getHome();
            evaluateHomeData(home, emd, config);

            SocialSecurityData socialSecurity = cv.getSocialSecurity();
            evaluateSocialSecurityData(socialSecurity, emd, config);
        } else {
            emd.addMessage(config.getString("curriculum_vitae.required"));
        }
    }

    /**
     * Evaluate if the list of references contains errors or missing
     * requeriments to meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param references list of references to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateReferences(List<ReferenceRequest> references,
            ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        for (ReferenceRequest reference : references) {
            ReferenceValidator.evaluateReference(reference, emd, config);
        }
    }

    /**
     * Evaluate if the social security object contains errors or missing
     * requeriments to meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param socialSecurity social security to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateSocialSecurityData(SocialSecurityData socialSecurity,
            ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        if (socialSecurity != null) {
            if (TextUtil.isEmpty(socialSecurity.getNameARL()))
                emd.addMessage(config.getString("social_security.name_arl"));
            if (TextUtil.isEmpty(socialSecurity.getNameEPS()))
                emd.addMessage(config.getString("social_security.name_eps"));
            if (TextUtil.isEmpty(socialSecurity.getPensionEntity()))
                emd.addMessage(config.getString("social_security.pension_entity"));
        } else {
            emd.addMessage(config.getString("social_security.required"));
        }
    }

    /**
     * Evaluate if the home object contains errors or missing requeriments to
     * meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param home home to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateHomeData(HomeData home, ErrorMessageData emd,
            ConfigLanguage config) {
        missingParameters(config, emd);

        if (home != null) {
            if (home.getHomeownership() == null)
                emd.addMessage(config.getString("home.homeownership"));
            if (home.getMonthlyExpenses() == null)
                emd.addMessage(config.getString("home.monthly_expenses"));
            if (home.getSonsNumber() < 0 && home.getSonsNumber() == null)
                emd.addMessage(config.getString("home.sons_number"));
            if (home.getStratum() < 0 && home.getStratum() == null)
                emd.addMessage(config.getString("home.stratum"));
        } else {
            emd.addMessage(config.getString("home.required"));
        }
    }

}
