package com.nativapps.arpia.service.converter;

import com.nativapps.arpia.database.dao.CustomerDataDao;
import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.entity.CustomerData;
import com.nativapps.arpia.database.entity.CustomerType;
import com.nativapps.arpia.database.entity.Establishment;
import com.nativapps.arpia.database.entity.Particular;
import com.nativapps.arpia.model.dto.CustomerDataDto;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class CustomerSimpleConverter implements DtoConverter<CustomerData, CustomerDataDto, CustomerDataDto> {

    private final CustomerDataDao controller = EntityControllerFactory.getCustomerDataController();

    private static final CustomerSimpleConverter INSTANCE = new CustomerSimpleConverter();

    /**
     * Don't let anyone instantiate this class.
     */
    private CustomerSimpleConverter() { }

    public static CustomerSimpleConverter instance() {
        return INSTANCE;
    }

    @Override
    public CustomerData convertToEntity(CustomerDataDto data) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public CustomerDataDto convertToDto(CustomerData entity) {
        CustomerDataDto response = new CustomerDataDto();

        response.setId(entity.getId());
        response.setType(entity.getType());

        if (entity.getType() == CustomerType.PARTICULAR) {
            Particular p = controller.findParticular(entity.getId());
            response.setDisplayName(String.format("%s %s", p.getName(), p.getLastName()));
            response.setIdentification(p.getIdentification());
        } else if (entity.getType() == CustomerType.ESTABLISHMENT) {
            Establishment e = controller.findEstablishment(entity.getId());
            response.setDisplayName(e.getName());
            response.setIdentification(e.getNit());
        }

        response.setBanned(entity.isBanned());
        response.setObservations(entity.getObservations());

        return response;
    }
}
