package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.dao.VehicleDao;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.Vehicle;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.VehicleRequest;
import com.nativapps.arpia.model.dto.VehicleResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.VehicleValidator;
import com.nativapps.arpia.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 2.0
 */
public class VehicleServiceImpl extends GenericService implements VehicleService,
        DtoConverter<Vehicle, VehicleRequest, VehicleResponse> {

    private final VehicleDao controller;

    public VehicleServiceImpl(VehicleDao controller) {
        this.controller = controller;
    }

    /**
     * Validate if the vehicle contains with all attributes for save in the
     * database.
     *
     * @param vehicle the vehicle data
     *
     * @throws ServiceException if vehicle don't have any attributes required
     * @throws BadRequestException if vehicle data is null
     */
    private void validateVehicle(VehicleRequest vehicle) throws ServiceException,
            BadRequestException {
        if (vehicle == null)
            throw new BadRequestException(config.getString("vehicle.is_null"));

        int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        ErrorMessageData emd = new ErrorMessageData(statusCode);
        VehicleValidator.evaluateVehicle(vehicle, emd, config);

        if (!emd.getMessages().isEmpty())
            throw new ServiceException(emd);
    }

    /**
     * Get vehicle entity from the given ID.
     *
     * @param vehicleId the vehicle ID
     * @return vehicle data
     *
     * @throws BadRequestException if vehicle ID is null or less than or equal
     * to 0
     * @throws NotFoundException if the vehicle with the given id is null
     */
    private Vehicle getEntity(Long vehicleId) throws BadRequestException,
            NotFoundException {
        if (vehicleId == null || vehicleId <= 0)
            throw new BadRequestException(config.getString("vehicle.id_required"));
        Vehicle vehicle = controller.find(vehicleId);
        if (vehicle == null) {
            String msg = String.format(config.getString("vehicle.not_found"), vehicleId);
            throw new NotFoundException(msg);
        }
        return vehicle;
    }

    @Override
    public VehicleResponse add(Long messengerId, VehicleRequest vehicle) {
        if (messengerId == null || messengerId <= 0)
            throw new BadRequestException(config.getString("vehicle.messengerId"));
        MessengerDao mController = EntityControllerFactory.getMessengerController();
        if (mController.find(messengerId) == null)
            throw new BadRequestException(config.getString("vehicle.mid_not_exist"));

        validateVehicle(vehicle);

        Vehicle entity = convertToEntity(vehicle);
        entity.setMessenger(new Messenger(messengerId));

        return convertToDto(controller.save(entity));
    }

    @Override
    public VehicleResponse get(Long vehicleId) {
        return convertToDto(getEntity(vehicleId));
    }

    @Override
    public List<VehicleResponse> getAll(Long messengerId) {
        List<VehicleResponse> vehicles = new ArrayList<>();
        for (Vehicle vehicle : controller.findAllByMessengerId(messengerId))
            vehicles.add(convertToDto(vehicle));
        return vehicles;
    }

    @Override
    public List<VehicleResponse> getAll() {
        List<VehicleResponse> vehicles = new ArrayList<>();
        for (Vehicle vehicle : controller.findAll())
            vehicles.add(convertToDto(vehicle));
        return vehicles;
    }

    @Override
    public VehicleResponse update(Long vehicleId, VehicleRequest vehicle) {
        if (vehicle == null)
            throw new BadRequestException(config.getString("vehicle.is_null"));

        Vehicle currentVehicle = getEntity(vehicleId);

        currentVehicle.setColor(vehicle.getColor());

        if (vehicle.getVehicleCondition() != null)
            currentVehicle.setCondition(vehicle.getVehicleCondition());

        String plate = vehicle.getLicensePlate();
        String currentPlate = currentVehicle.getLicensePlate();
        if (!TextUtil.isEmpty(plate) && !plate.equalsIgnoreCase(currentPlate)) {
            if (controller.findByLicensePlate(plate) != null) {
                String msg = config.getString("vehicle.license_plate_exists");
                throw new BadRequestException(msg);
            } else {
                currentVehicle.setLicensePlate(plate);
            }
        }

        if (!TextUtil.isEmpty(vehicle.getModel()))
            currentVehicle.setModel(vehicle.getModel());
        if (!TextUtil.isEmpty(vehicle.getTrademark()))
            currentVehicle.setTrademark(vehicle.getTrademark());
        if (vehicle.getOwnership() != null)
            currentVehicle.setOwnership(vehicle.getOwnership());
        if (vehicle.getSoatExpiration() != null)
            currentVehicle.setSoatExpiration(vehicle.getSoatExpiration());
        if (vehicle.getTecnomecanicaExpiration() != null)
            currentVehicle.setTecnomecanicaExpiration(vehicle
                    .getTecnomecanicaExpiration());

        return convertToDto(controller.save(currentVehicle));
    }

    @Override
    public VehicleResponse delete(Long vehicleId) {
        VehicleResponse vehicle = get(vehicleId);
        controller.delete(vehicleId);
        return vehicle;
    }

    @Override
    public Vehicle convertToEntity(VehicleRequest d) {
        return new Vehicle(d.getOwnership(), d.getVehicleCondition(),
                d.getTrademark(), d.getModel(), d.getColor(), d.getLicensePlate(),
                d.getSoatExpiration(), d.getTecnomecanicaExpiration());
    }

    @Override
    public VehicleResponse convertToDto(Vehicle e) {
        return new VehicleResponse(e.getId(), e.getOwnership(), e.getCondition(),
                e.getTrademark(), e.getModel(), e.getColor(), e.getLicensePlate(),
                e.getTecnomecanicaExpiration(), e.getSoatExpiration());
    }

}
