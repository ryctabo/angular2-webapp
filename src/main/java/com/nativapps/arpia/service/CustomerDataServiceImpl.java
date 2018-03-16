package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.CustomerDataDao;
import com.nativapps.arpia.database.entity.CustomerData;
import com.nativapps.arpia.database.entity.CustomerType;
import com.nativapps.arpia.database.entity.Establishment;
import com.nativapps.arpia.database.entity.Particular;
import com.nativapps.arpia.model.dto.CustomerDataDto;
import com.nativapps.arpia.model.dto.CustomerParamData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.service.converter.DtoConverter;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class CustomerDataServiceImpl extends GenericService implements CustomerDataService,
        DtoConverter<CustomerData, CustomerDataDto, CustomerDataDto> {

    private final CustomerDataDao controller;

    public CustomerDataServiceImpl(CustomerDataDao controller) {
        this.controller = controller;
    }

    @Override
    public ListResponse<CustomerDataDto> getAll(int start, int size, String search) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<CustomerDataDto> response = new ArrayList<>();
        for (CustomerData customerData : controller.findAllPaginated(start, size, search))
            response.add(convertToDto(customerData));

        return new ListResponse<>(controller.findAllCount(search), response);
    }

    @Override
    public CustomerDataDto get(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("customer.id_required"));

        CustomerData customer = controller.find(id);
        if (customer == null) {
            final String FORMAT = config.getString("customer.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return convertToDto(customer);
    }

    @Override
    public ListResponse<CustomerDataDto> getAllByNotCommunication(int start, int size, Calendar date) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        if (date == null)
            throw new BadRequestException(config.getString("customer.not_com_date_required"));

        List<CustomerDataDto> response = new ArrayList<>();
        for (CustomerData customerData : controller.findByNotCommunication(start, size, date))
            response.add(convertToDto(customerData));

        return new ListResponse<>(controller.countByNotCommunication(date), response);
    }

    @Override
    public CustomerData convertToEntity(CustomerDataDto data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CustomerDataDto convertToDto(CustomerData entity) {
        CustomerDataDto dto = new CustomerDataDto();

        dto.setId(entity.getId());
        dto.setObservations(entity.getObservations());
        dto.setType(entity.getType());
        if (entity.getType() == CustomerType.PARTICULAR) {
            Particular p = controller.findParticular(entity.getId());
            dto.setDisplayName(p.getName() + " " + p.getLastName());
            dto.setIdentification(p.getIdentification());
        } else {
            Establishment e = controller.findEstablishment(entity.getId());
            dto.setDisplayName(e.getName());
            dto.setIdentification(e.getNit());
        }
        dto.setBanned(entity.isBanned());
        dto.setParam(new CustomerParamData(entity.getParam()));

        return dto;
    }

}
