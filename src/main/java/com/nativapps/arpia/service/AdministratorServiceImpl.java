package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.AdministratorDao;
import com.nativapps.arpia.database.dao.EstablishmentDao;
import com.nativapps.arpia.database.dao.NeighborhoodDao;
import com.nativapps.arpia.database.entity.Administrator;
import com.nativapps.arpia.database.entity.Establishment;
import com.nativapps.arpia.model.dto.AdministratorRequest;
import com.nativapps.arpia.model.dto.AdministratorResponse;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.AdministratorValidator;
import com.nativapps.arpia.util.TextUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class AdministratorServiceImpl extends GenericService implements AdministratorService,
        DtoConverter<Administrator, AdministratorRequest, AdministratorResponse> {

    private final AdministratorDao administratorDao = EntityControllerFactory
            .getAdministratorController();

    private final EstablishmentDao establishmentDao = EntityControllerFactory
            .getEstablishmentController();

    private final NeighborhoodDao neighborhoodDao = EntityControllerFactory
            .getNeighborhoodController();

    @Override
    public AdministratorResponse add(Long establishmentId,
            AdministratorRequest data) {
        if (establishmentId == null || establishmentId <= 0)
            throw new BadRequestException(config
                    .getString("establishment.id_required"));

        Establishment establishment = establishmentDao.find(establishmentId);

        if (establishment == null)
            throw new BadRequestException(String.format(config
                    .getString("establishment.not_found"), establishmentId));

        if (data == null)
            throw new BadRequestException(config
                    .getString("administrator.is_null"));
        else {
            ErrorMessageData errorData = new ErrorMessageData();
            AdministratorValidator.evaluateAdministrator(data, errorData, config);

            if (!errorData.getMessages().isEmpty()) {
                errorData.setStatusCode(Response.Status.BAD_REQUEST
                        .getStatusCode());
                throw new ServiceException(errorData);
            }
        }
        Administrator administrator = convertToEntity(data);
        administrator.setEstablishment(establishment);

        return convertToDto(administratorDao.save(administrator));
    }

    @Override
    public AdministratorResponse get(Long establishmentId, Long id) {
        return convertToDto(getEntity(id, establishmentId));
    }

    @Override
    public List<AdministratorResponse> getAll(Long establishmentId) {
        if (establishmentId == null || establishmentId <= 0)
            throw new BadRequestException(config
                    .getString("establishment.id_required"));

        Establishment establishment = establishmentDao.find(establishmentId);
        if (establishment == null)
            throw new NotFoundException(String.format(config
                    .getString("establishment.not_found"), establishmentId));

        List<Administrator> list = administratorDao
                .getAllByEstablishmentId(establishmentId);

        List<AdministratorResponse> result = new ArrayList<>();
        for (Administrator administrator : list)
            result.add(convertToDto(administrator));

        return result;
    }

    /**
     * Returns the administrator entity from id provided
     *
     * @param id Administrator's ID
     * @param establishmentId Establishment's ID
     * @return searched entity
     */
    public Administrator getEntity(Long id, Long establishmentId) {
        ErrorMessageData errorData = new ErrorMessageData();
        if (establishmentId == null || establishmentId <= 0)
            errorData.addMessage(config.getString("establishment.id_required"));
        if (id == null || id <= 0)
            errorData.addMessage(config.getString("administrator.id_required"));

        if (!errorData.getMessages().isEmpty()) {
            errorData.setStatusCode(Response.Status.BAD_REQUEST
                    .getStatusCode());
            throw new ServiceException(errorData);
        }

        Establishment establishment = establishmentDao.find(establishmentId);
        if (establishment == null)
            throw new NotFoundException(String.format(config
                    .getString("establishment.not_found"), establishmentId));

        Administrator administrator = administratorDao.getByEstablishmentId(id,
                establishmentId);

        if (administrator == null)
            throw new NotFoundException(String.format(config
                    .getString("administrator.not_found"), establishmentId, id));

        return administrator;
    }

    @Override
    public AdministratorResponse update(Long establishmentId, Long id,
            AdministratorRequest data) {

        if (data == null)
            throw new BadRequestException(config
                    .getString("administrator.is_null"));

        Administrator entity = getEntity(id, establishmentId);

        if (!TextUtil.isEmpty(data.getIdentification()) && !data
                .getIdentification().equals(entity.getIdentification()))
            entity.setIdentification(data.getIdentification());
        if (!TextUtil.isEmpty(data.getName()) && !data.getName()
                .equalsIgnoreCase(entity.getName()))
            entity.setName(data.getName());
        if (!TextUtil.isEmpty(data.getLastName()) && !data.getLastName()
                .equalsIgnoreCase(entity.getLastName()))
            entity.setLastName(data.getLastName());
        if (!TextUtil.isEmpty(data.getObservations()) && !data.getObservations()
                .equalsIgnoreCase(entity.getObservations()))
            entity.setObservations(data.getObservations());
        if (data.getGender() != null && data.getGender() != entity.getGender())
            entity.setGender(data.getGender());
        if (data.getBirthday() != null && !data.getBirthday().equals(entity
                .getBirthday()))
            entity.setBirthday(data.getBirthday());
        if (data.getnDayReport() != null && !Objects.equals(data
                .getnDayReport(), entity.getnDayReport()))
            entity.setnDayReport(data.getnDayReport());
        if (data.getLinked() != null && data.getLinked() != entity.isLinked())
            entity.setLinked(data.getLinked());

        return convertToDto(administratorDao.save(entity));
    }

    @Override
    public AdministratorResponse delete(Long establishmentId, Long id) {
        AdministratorResponse data = get(id, establishmentId);
        administratorDao.delete(id);
        return data;
    }

    @Override
    public Administrator convertToEntity(AdministratorRequest data) {
        Administrator administrator = new Administrator(
                data.getIdentification(), data.getName(), data.getLastName(),
                data.getGender(), data.getBirthday(), data.getAddress(), 
                data.getEmail(), data.getPhone(), data.getObservations(),
                data.getLinked(), data.getnDayReport());

        return administrator;
    }

    @Override
    public AdministratorResponse convertToDto(Administrator entity) {
        AdministratorResponse data
                = new AdministratorResponse(entity.getId(),
                        entity.getIdentification(), entity.getName(),
                        entity.getLastName(), entity.getGender(),
                        entity.getBirthday(), entity.getAddress(), 
                        entity.getEmail(), entity.getPhone(), entity.isLinked(),
                        entity.getnDayReport(), entity.getObservations());

        return data;
    }

}
