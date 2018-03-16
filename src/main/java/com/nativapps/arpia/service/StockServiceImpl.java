package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.InventoryDao;
import com.nativapps.arpia.database.dao.StockDao;
import com.nativapps.arpia.database.entity.Inventory;
import com.nativapps.arpia.database.entity.Stock;
import com.nativapps.arpia.database.entity.StockType;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.StockRequest;
import com.nativapps.arpia.model.dto.StockResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.TextUtil;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class StockServiceImpl extends GenericService implements StockService,
        DtoConverter<Stock, StockRequest, StockResponse> {

    private final StockDao sController;

    private final InventoryDao iController;

    StockServiceImpl(StockDao sController, InventoryDao iController) {
        this.sController = sController;
        this.iController = iController;
    }

    @Override
    public ListResponse<StockResponse> get(Long inventoryId, StockType type, Calendar startDate,
                                           Calendar endDate, int start, int size) {
        List<StockResponse> stocks = new ArrayList<>();
        for (Stock stock : sController.findAll(inventoryId, type, startDate, endDate, start, size))
            stocks.add(convertToDto(stock));
        long total = sController.count(inventoryId, type, startDate, endDate);
        return new ListResponse<>(total, stocks);
    }

    @Override
    public StockResponse get(Long inventoryId, Integer index) {
        if (inventoryId == null || inventoryId <= 0)
            throw new BadRequestException(config.getString("inventory.id"));
        if (index == null || index <= 0)
            throw new BadRequestException(config.getString("stock.index"));

        Stock.StockPK id = new Stock.StockPK(index, inventoryId);
        Stock stock = sController.find(id);
        if (stock == null) {
            final String FORMAT = config.getString("stock.not_found");
            throw new NotFoundException(String.format(FORMAT, inventoryId, index));
        }

        return convertToDto(stock);
    }

    @Override
    public StockResponse add(Long inventoryId, StockRequest data) {
        //validate the inventory element
        if (inventoryId == null || inventoryId <= 0)
            throw new BadRequestException(config.getString("inventory.id"));
        Inventory inventory = iController.find(inventoryId);
        if (inventory == null) {
            final String FORMAT = config.getString("inventory.not_found");
            throw new NotFoundException(String.format(FORMAT, inventoryId));
        }

        //validate stock data request
        if (data == null)
            throw new BadRequestException(config.getString("stock.is_null"));

        int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        ErrorMessageData emd = new ErrorMessageData(statusCode);

        if (data.getCount() == null || data.getCount() <= 0)
            emd.addMessage(config.getString("stock.count"));
        if (data.getType() == null)
            emd.addMessage(config.getString("stock.type"));
        if (TextUtil.isEmpty(data.getReason()) || data.getReason().length() > 200)
            emd.addMessage(config.getString("stock.reason"));

        //verify if exists error messages
        if (!emd.getMessages().isEmpty())
            throw new ServiceException(emd);

        //change the available count of inventory elements
        int count = inventory.getCount();
        count += data.getType() == StockType.INPUT ? data.getCount() : -data.getCount();
        inventory.setCount(count);

        Stock stock = convertToEntity(data);
        stock.setInventory(inventory);
        stock.setRegisterDate(Calendar.getInstance());

        //Create a new index for this stock
        int index = (int) sController.count(inventoryId, null, null, null) + 1;
        stock.getId().setIndex(index);

        return convertToDto(sController.save(stock));
    }

    @Override
    public Stock convertToEntity(StockRequest data) {
        Stock stock = new Stock();

        stock.setReason(data.getReason());
        stock.setCount(data.getCount());
        stock.setType(data.getType());

        return stock;
    }

    @Override
    public StockResponse convertToDto(Stock entity) {
        StockResponse stock = new StockResponse();

        stock.setIndex(entity.getId().getIndex());
        stock.setReason(entity.getReason());
        stock.setCount(entity.getCount());
        stock.setType(entity.getType());
        stock.setRegisterDate(entity.getRegisterDate());

        return stock;
    }

}
