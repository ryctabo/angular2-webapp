package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.CustomerDataDao;
import com.nativapps.arpia.database.dao.DomicileExecuteDao;
import com.nativapps.arpia.database.dao.NeighborhoodDao;
import com.nativapps.arpia.database.entity.CustomerData;
import com.nativapps.arpia.database.entity.DomicileExecute;
import com.nativapps.arpia.database.entity.Neighborhood;
import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.service.converter.CustomerSimpleConverter;
import com.nativapps.arpia.service.converter.DomicileExecuteSimpleConverter;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.util.TextUtil;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.2.0
 */
public class NeighborhoodServiceImpl extends GenericService implements NeighborhoodService,
        DtoConverter<Neighborhood, NeighborhoodRequest, NeighborhoodResponse> {

    private final NeighborhoodDao nController;

    private final CustomerDataDao cController;

    private final DomicileExecuteDao dController;

    public NeighborhoodServiceImpl(NeighborhoodDao nController,
                                   CustomerDataDao cController,
                                   DomicileExecuteDao dController) {
        this.nController = nController;
        this.cController = cController;
        this.dController = dController;
    }

    @Override
    public ListResponse<NeighborhoodResponse> getAll(int start, int size, String search) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<NeighborhoodResponse> response = new ArrayList<>();
        for (Neighborhood neighborhood : nController.findAll(start, size, search))
            response.add(convertToDto(neighborhood));

        return new ListResponse<>(nController.findAllCount(search), response);
    }

    /**
     * Return a neighborhood entity by id
     *
     * @param id neighborhood ID
     * @return neighborhood entity
     */
    private Neighborhood getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("person.id_required"));

        Neighborhood neighborhood = nController.find(id);
        if (neighborhood == null)
            throw new NotFoundException(String.format(config
                    .getString("neighborhood.not_found"), id));

        return neighborhood;
    }

    @Override
    public NeighborhoodResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public NeighborhoodResponse add(NeighborhoodRequest data) {
        if (data == null)
            throw new BadRequestException(config.getString("neighborhood.is_null"));
        if (TextUtil.isEmpty(data.getName()))
            throw new BadRequestException(config.getString("neighborhood.name_null"));
        if (nController.findByName(data.getName()) != null)
            throw new BadRequestException(String.format(config
                    .getString("neighborhood.exists"), data.getName()));

        return convertToDto(nController.save(convertToEntity(data)));
    }

    @Override
    public NeighborhoodResponse update(Long id, NeighborhoodRequest data) {
        Neighborhood entity = getEntity(id);

        if (data == null)
            throw new BadRequestException(config.getString("neighborhood.is_null"));
        if (TextUtil.isEmpty(data.getName()))
            throw new BadRequestException(config.getString("neighborhood.name_null"));
        if (!entity.getName().equalsIgnoreCase(data.getName()))
            entity.setName(data.getName());

        return convertToDto(nController.save(entity));
    }

    @Override
    public NeighborhoodResponse delete(Long id) {
        NeighborhoodResponse response = get(id);
        nController.delete(id);
        return response;
    }

    @Override
    public ListResponse<DomicileExecuteResponse> getDomiciles(long neighborhood, int start, int size) {
        List<DomicileExecuteResponse> domiciles = new ArrayList<>();

        for (DomicileExecute de : dController.findByNeighborhood(neighborhood, start, size))
            domiciles.add(DomicileExecuteSimpleConverter.instance().convertToDto(de));

        long total = dController.countByNeighborhood(neighborhood);
        return new ListResponse<>(total, domiciles);
    }

    @Override
    public ListResponse<CustomerDataDto> getCustomers(long neighborhood, int start, int size) {
        List<CustomerDataDto> customers = new ArrayList<>();

        for (CustomerData cu : cController.findByNeighborhood(neighborhood, start, size))
            customers.add(CustomerSimpleConverter.instance().convertToDto(cu));

        long total = cController.countByNeighborhood(neighborhood);
        return new ListResponse<>(total, customers);
    }

    @Override
    public Neighborhood convertToEntity(NeighborhoodRequest data) {
        return new Neighborhood(data.getName());
    }

    @Override
    public NeighborhoodResponse convertToDto(Neighborhood entity) {
        return new NeighborhoodResponse(entity.getId(), entity.getName());
    }

}
