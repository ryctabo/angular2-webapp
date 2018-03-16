package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Vehicle;
import java.util.List;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 1.2.0
 */
public class VehicleDaoController extends EntityDao<Vehicle, Long>
        implements VehicleDao {

    private static final VehicleDaoController INSTANCE = new VehicleDaoController();

    private VehicleDaoController() {
        super(Vehicle.class);
    }

    public static VehicleDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Vehicle> findAllByMessengerId(Long messengerId) {
        return executeNamedQueryForList("vehicle.findByMessengerId",
                new Parameter("id", messengerId));
    }

    @Override
    public Vehicle findByLicensePlate(String plate) {
        return executeNamedQuery("vehicle.findByLicensePlate",
                new Parameter("plate", plate));
    }

}
