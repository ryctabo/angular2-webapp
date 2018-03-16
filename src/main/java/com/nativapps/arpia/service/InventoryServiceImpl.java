package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.InventoryDao;
import com.nativapps.arpia.database.entity.*;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.converter.InventorySimpleConverter;
import com.nativapps.arpia.service.converter.MessengerSimpleConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.InventoryValidator;
import com.nativapps.arpia.util.TextUtil;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.1.0
 */
public class InventoryServiceImpl extends GenericService implements InventoryService,
        DtoConverter<Inventory, InventoryRequest, InventoryResponse> {

    private final InventoryDao controller;

    InventoryServiceImpl(InventoryDao controller) {
        this.controller = controller;
    }

    /**
     * Validate if inventory have all attributes to save in the database.
     *
     * @param inventory inventory to validate
     * @throws BadRequestException if inventory is null
     * @throws ServiceException    if inventory data don't have any attributes required
     */
    private void validateInventory(InventoryRequest inventory)
            throws BadRequestException, ServiceException {
        if (inventory == null)
            throw new BadRequestException(config.getString("inventory.is_null"));

        ErrorMessageData emd = new ErrorMessageData();
        InventoryValidator.evaluateInventory(inventory, emd, config);

        //verify if exist error messages
        if (!emd.getMessages().isEmpty()) {
            emd.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
            throw new ServiceException(emd);
        }
    }

    /**
     * It lets get to the corresponding inventory entity id provided.
     *
     * @param id inventory index to search
     * @return search inventory
     */
    private Inventory getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("inventory.id"));

        Inventory inventory = controller.find(id);
        if (inventory == null) {
            final String FORMAT = config.getString("inventory.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }

        return inventory;
    }

    @Override
    public ListResponse<InventoryResponse> getAll(String search, String orderBy,
                                                  OrderType orderType, int start, int size) {
        List<InventoryResponse> items = new ArrayList<>();

        for (Inventory item : controller.findAll(search, orderBy, orderType, start, size))
            items.add(convertToDto(item));

        long count = controller.count(search);
        return new ListResponse<>(count, items);
    }

    @Override
    public InventoryResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public InventoryResponse add(InventoryRequest data) {
        validateInventory(data);

        Calendar registerDate = Calendar.getInstance();
        Inventory inventory = convertToEntity(data);
        inventory.setRegisterDate(registerDate);

        //create a new stock item
        Stock stock = new Stock();
        stock.setReason("Entrada por creación del elemento.");
        stock.setType(StockType.INPUT);
        stock.setCount(data.getCount());
        stock.setRegisterDate(registerDate);
        stock.getId().setIndex(1);

        //Add stock into inventory element
        inventory.addStock(stock);

        return convertToDto(controller.save(inventory));
    }

    @Override
    public InventoryResponse update(Long id, InventoryRequest data) {
        if (data == null)
            throw new BadRequestException(config.getString("inventory.is_null"));

        Inventory inventory = getEntity(id);

        if (!TextUtil.isEmpty(data.getName()) && !data.getName().equals(inventory.getName()))
            inventory.setName(data.getName());

        inventory.setUpdateDate(Calendar.getInstance());
        return convertToDto(controller.save(inventory));
    }

    @Override
    public InventoryResponse delete(Long id) {
        InventoryResponse inventory = get(id);
        controller.delete(id);
        return inventory;
    }

    @Override
    public List<MessengerInfoResponse> getMessengers(Long inventoryId) {
        getEntity(inventoryId);
        List<MessengerInfoResponse> messengers = new ArrayList<>();
        for (MessengerInfo mInfo : controller.getMessengers(inventoryId)) {
            MessengerInfoResponse mir = new MessengerInfoResponse();
            mir.setMessenger(MessengerSimpleConverter.instance()
                    .convertToDto(mInfo.getMessenger()));
            mir.setLoans(mInfo.getLoans());
            mir.setReturns(mInfo.getReturns());
            messengers.add(mir);
        }
        return messengers;
    }

    @Override
    public ChipInfoResponse getChipInfo(Long inventoryId) {
        getEntity(inventoryId);
        ChipInfo chipInfo = controller.getChipInfo(inventoryId);
        return new ChipInfoResponse(chipInfo.getLoans(), chipInfo.getReturns());
    }

    @Override
    public InventoryResponse convertToDto(Inventory entity) {
        return InventorySimpleConverter.instance().convertToDto(entity);
    }

    @Override
    public Inventory convertToEntity(InventoryRequest d) {
        return new Inventory(d.getName(), d.getCount());
    }

}
