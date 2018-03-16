package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.database.dao.CustomerDataDao;
import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.OperationDao;
import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.util.TextUtil;

import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.3
 */
public class DomicileValidator extends Validator {

    /**
     * Don't let anyone instantiate this class.
     */
    private DomicileValidator() { }

    /**
     * Evaluate if the domicile object contains errors or missing requeriments
     * to meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param domicile domicile to evaluate
     * @param emd      error message data
     * @param config   language configuration
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateDomicile(DomicileRequest domicile, ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        Long customerId = domicile.getCustomer();
        if (customerId == null || customerId <= 0) {
            emd.addMessage(config.getString("customer.id_required"));
        } else {
            CustomerDataDao controller = EntityControllerFactory
                    .getCustomerDataController();
            if (controller.find(customerId) == null) {
                final String FORMAT = config.getString("customer.not_found");
                emd.addMessage(String.format(FORMAT, customerId));
            }
        }

        Long operationId = domicile.getOperation();
        if (operationId == null || operationId <= 0) {
            emd.addMessage(config.getString("domicile.operationId"));
        } else {
            OperationDao controller = EntityControllerFactory
                    .getOperationController();
            if (controller.find(operationId) == null) {
                final String FORMAT = config.getString("operation.not_found");
                emd.addMessage(String.format(FORMAT, operationId));
            }
        }

        // Validate price, messenger gain and domicile type
        if (domicile.getPrice() == null || domicile.getPrice() < 0)
            emd.addMessage(config.getString("domicile.price"));
        if (domicile.getMessengerGain() == null || domicile.getMessengerGain() < 0)
            emd.addMessage(config.getString("domicile.messenger_gain"));
        if (domicile.getType() == null)
            emd.addMessage(config.getString("domicile.type"));

        // Validate locations list
        List<LocationRequest> locations = domicile.getLocations();
        if (locations == null) {
            emd.addMessage(config.getString("domicile.location"));
        } else {
            for (LocationData location : locations)
                evaluateLocation(location, emd, config);
        }

        // Validate rating data
        if (domicile.getRating() == null)
            emd.addMessage(config.getString("domicile.rating"));
        else
            evaluateRating(domicile.getRating(), emd, config);

        if (domicile.getRequirements() == null)
            emd.addMessage(config.getString("domicile.requirements"));
        else
            evaluateRequirements(domicile.getRequirements(), emd, config);

        if (domicile.getPeriodicity() != null)
            RepeatValidator.evaluateRepeat(domicile.getPeriodicity(), emd, config);
    }

    /**
     * Evaluate if the location object contains errors or missing requeriments
     * to meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param location location to evaluate
     * @param emd      error message data
     * @param config   language configuration
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateLocation(LocationData location, ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        if (TextUtil.isEmpty(location.getAddress()))
            emd.addMessage(config.getString("location.address"));

        if (location.getCoordinates() != null) {
            LatLngData coordinates = location.getCoordinates();
            if (coordinates.getLatitude() == null)
                emd.addMessage(config.getString("location.latitude"));
            if (coordinates.getLongitude() == null)
                emd.addMessage(config.getString("location.longitude"));
        }

        if (TextUtil.isEmpty(location.getTask()))
            emd.addMessage(config.getString("location.task"));
    }

    /**
     * Evaluate if the rating object contains errors or missing requirements to
     * meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param rating rating to evaluate
     * @param emd    error message data
     * @param config language configuration
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateRating(DomicileRatingData rating, ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        if (rating.getTrustLevel() == null || rating.getTrustLevel() < 0)
            emd.addMessage(config.getString("rating.trust_level"));
        if (rating.getPriority() == null)
            emd.addMessage(config.getString("rating.priority"));
        if (rating.getComplexity() == null)
            emd.addMessage(config.getString("rating.complexity"));
        if (rating.getVelocity() == null)
            emd.addMessage(config.getString("rating.velocity"));
    }

    /**
     * Evaluate if the requirement object contains errors or missing
     * requirements to meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param requirement requirement to evaluate
     * @param emd         error message data
     * @param config      language configuration
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateRequirements(DomicileRequirementData requirement, ErrorMessageData emd,
                                            ConfigLanguage config) {
        missingParameters(config, emd);

        Float cashRequired = requirement.getCashRequired();
        if (cashRequired == null || cashRequired < 0)
            emd.addMessage(config.getString("requirement.cash_required"));
        if (requirement.getMonitoring() == null)
            emd.addMessage(config.getString("requirement.monitoring"));
        if (requirement.getEnrutable() == null)
            emd.addMessage(config.getString("requirement.enrutable"));
        if (requirement.getHamper() == null)
            emd.addMessage(config.getString("requirement.hamper"));
        if (requirement.getPriceSmart() == null)
            emd.addMessage(config.getString("requirement.price_smart"));
        if (requirement.getWineCellar() == null)
            emd.addMessage(config.getString("requirement.wine_cellar"));
        if (requirement.getIdentification() == null)
            emd.addMessage(config.getString("requirement.identification"));
        if (requirement.getEasyCheck() == null)
            emd.addMessage(config.getString("requirement.easy_check"));
        if (requirement.getTicket() == null)
            emd.addMessage(config.getString("requirement.ticket"));
    }

}
