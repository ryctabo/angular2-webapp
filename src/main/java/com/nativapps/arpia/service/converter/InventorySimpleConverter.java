package com.nativapps.arpia.service.converter;

import com.nativapps.arpia.database.entity.Inventory;
import com.nativapps.arpia.model.dto.InventoryRequest;
import com.nativapps.arpia.model.dto.InventoryResponse;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class InventorySimpleConverter implements DtoConverter<Inventory, InventoryRequest, InventoryResponse> {

    private static final InventorySimpleConverter INSTANCE = new InventorySimpleConverter();

    /**
     * Don't let anyone instantiate this class.
     */
    private InventorySimpleConverter() { }

    public static InventorySimpleConverter instance() {
        return INSTANCE;
    }

    @Override
    public Inventory convertToEntity(InventoryRequest data) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public InventoryResponse convertToDto(Inventory entity) {
        InventoryResponse inventory = new InventoryResponse();

        inventory.setId(entity.getId());
        inventory.setName(entity.getName());
        inventory.setCount(entity.getCount());
        inventory.setRegisterDate(entity.getRegisterDate());
        inventory.setUpdatedDate(entity.getUpdateDate());

        return inventory;
    }
}
