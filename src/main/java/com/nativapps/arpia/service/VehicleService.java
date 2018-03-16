package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.VehicleRequest;
import com.nativapps.arpia.model.dto.VehicleResponse;
import java.util.List;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0.1
 */
public interface VehicleService extends Service {

    /**
     * Create new vehicle from the given data.
     *
     * @param messengerId the messenger ID
     * @param data vehicle information
     * @return vehicle saved
     */
    public VehicleResponse add(Long messengerId, VehicleRequest data);

    /**
     * Get vehicle information by vehicle id provided.
     *
     * @param id vehicle ID
     * @return vehicle data
     */
    public VehicleResponse get(Long id);

    /**
     * Get all vehicle by messenger id provided.
     *
     * @param messengerId the messenger ID
     * @return vehicles list
     */
    public List<VehicleResponse> getAll(Long messengerId);

    /**
     * Get all vehicles.
     *
     * @return vehicles list
     */
    public List<VehicleResponse> getAll();

    /**
     * Updates the vehicle information by vehicle id provided.
     *
     * @param vehicleId the vehicle ID
     * @param vehicle vehicle data
     * @return vehicle updated
     */
    public VehicleResponse update(Long vehicleId, VehicleRequest vehicle);

    /**
     * Removes the vehicle information by vehicle id provided.
     *
     * @param vehicleId the vehicle ID
     * @return vehicle deleted
     */
    public VehicleResponse delete(Long vehicleId);

}
