package com.nativapps.arpia.service.validator;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.VehicleDao;
import com.nativapps.arpia.model.ConfigLanguage;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.VehicleRequest;
import com.nativapps.arpia.util.DateUtil;
import com.nativapps.arpia.util.TextUtil;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 1.0.1
 */
public class VehicleValidator extends Validator {

    /**
     * Don't let anyone instantiate this class.
     */
    private VehicleValidator() {
    }

    /**
     * Evaluate if the vehicle object contains errors or missing requeriments to
     * meet.
     * <p>
     * If this is the case, then the method add messages to
     * {@link ErrorMessageData} object using the language configuration.
     *
     * @param vehicle vehicle to evaluate
     * @param emd error message data
     * @param config language configuration
     *
     * @throws IllegalArgumentException if any of the parameters is missing
     */
    public static void evaluateVehicle(VehicleRequest vehicle,
            ErrorMessageData emd, ConfigLanguage config) {
        missingParameters(config, emd);

        VehicleDao controller = EntityControllerFactory.getVehicleController();
        if (TextUtil.isEmpty(vehicle.getLicensePlate()))
            emd.addMessage(config.getString("vehicle.license_plate"));
        else if (controller.findByLicensePlate(vehicle.getLicensePlate()) != null)
            emd.addMessage(config.getString("vehicle.license_plate_exists"));

        if (TextUtil.isEmpty(vehicle.getModel()))
            emd.addMessage(config.getString("vehicle.model"));
        if (TextUtil.isEmpty(vehicle.getTrademark()))
            emd.addMessage(config.getString("vehicle.trademark"));
        if (vehicle.getOwnership() == null)
            emd.addMessage(config.getString("vehicle.ownership"));
        if (vehicle.getVehicleCondition() == null)
            emd.addMessage(config.getString("vehicle.condition"));

        if (vehicle.getTecnomecanicaExpiration() == null)
            emd.addMessage(config.getString("vehicle.technical_mechanics"));
        else if (DateUtil.isBeforeToday(vehicle.getTecnomecanicaExpiration()))
            emd.addMessage(config.getString("vehicle.technical_mechanics_today"));

        if (vehicle.getSoatExpiration() == null)
            emd.addMessage(config.getString("vehicle.soat"));
        else if (DateUtil.isBeforeToday(vehicle.getSoatExpiration()))
            emd.addMessage(config.getString("vehicle.soat_today"));
    }

}
