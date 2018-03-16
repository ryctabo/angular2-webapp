package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Vehicle;
import java.util.List;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 1.3.0
 */
public interface VehicleDao extends DataAccessObject<Vehicle, Long> {

    /**
     * Gets all vehicle information by messenger id provided.
     *
     * @param messengerId
     * @return vehicle list from messenger
     */
    List<Vehicle> findAllByMessengerId(Long messengerId);

    /**
     * Get a vehicle from the given license plate.
     *
     * @param plate license plate to search
     * @return vehicle data
     */
    Vehicle findByLicensePlate(String plate);

}
