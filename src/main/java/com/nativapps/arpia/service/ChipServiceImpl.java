package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.ChipDao;
import com.nativapps.arpia.database.dao.InventoryDao;
import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.entity.*;
import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.converter.MessengerSimpleConverter;
import com.nativapps.arpia.service.exception.ServiceException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class ChipServiceImpl extends GenericService implements ChipService,
        DtoConverter<Chip, ChipRequest, ChipResponse> {

    private final ChipDao cController;

    private final MessengerDao mController;

    private final InventoryDao iController;

    public ChipServiceImpl(ChipDao cController, MessengerDao mController, InventoryDao iController) {
        this.cController = cController;
        this.mController = mController;
        this.iController = iController;
    }

    @Override
    public ListResponse<ChipResponse> get(Long messengerId, ChipType type, int start, int size) {
        List<ChipResponse> chips = new ArrayList<>();
        for (Chip chip : cController.findAll(messengerId, type, start, size))
            chips.add(convertToDto(chip));
        long total = cController.count(messengerId, type);
        return new ListResponse<>(total, chips);
    }

    @Override
    public ChipResponse get(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("chip.id"));
        Chip chip = cController.find(id);
        if (chip == null) {
            final String FORMAT = config.getString("chip.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return convertToDto(chip);
    }

    @Override
    public ChipResponse add(ChipRequest data) {
        if (data == null)
            throw new BadRequestException(config.getString("chip.is_null"));

        int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        ErrorMessageData emd = new ErrorMessageData(statusCode);

        //validate if messenger exists
        if (data.getMessenger() == null || data.getMessenger() <= 0)
            emd.addMessage(config.getString("messenger.id_required"));
        Messenger messenger = mController.find(data.getMessenger());
        if (messenger == null) {
            final String FORMAT = config.getString("messenger.not_found");
            emd.addMessage(String.format(FORMAT, data.getMessenger()));
        }
        // validate if chip have a type defined
        if (data.getType() == null)
            emd.addMessage(config.getString("chip.type"));

        //validate chip items
        List<Long> Ids = new ArrayList<>();
        for (ChipItemRequest item : data.getItems()) {
            if (Ids.contains(item.getElement())) {
                final String FORMAT = config.getString("chipitem.element_repeat");
                emd.addMessage(String.format(FORMAT, item.getElement()));
                continue;
            }

            //verify if count of element is valid
            if (item.getCount() == null || item.getCount() <= 0)
                emd.addMessage(config.getString("chipitem.count"));
            //validate element
            if (item.getElement() == null || item.getElement() <= 0) {
                emd.addMessage(config.getString("inventory.id"));
            } else {
                Ids.add(item.getElement());
                Inventory element = iController.find(item.getElement());
                if (element == null) {
                    final String FORMAT = config.getString("inventory.not_found");
                    emd.addMessage(String.format(FORMAT, item.getElement()));
                } else {
                    ChipInfo chipInfo = iController.getChipInfo(item.getElement());
                    int difference = chipInfo.getLoans() - chipInfo.getReturns();
                    if (data.getType() == ChipType.RETURN && difference < item.getCount()) {
                        final String FORMAT = config.getString("chipitem.return_not_available");
                        emd.addMessage(String.format(FORMAT, item.getElement()));
                    } else if (data.getType() == ChipType.LOAN && element.getCount() - difference < item.getCount()) {
                        final String FORMAT = config.getString("chipitem.loan_not_available");
                        emd.addMessage(String.format(FORMAT, item.getElement()));
                    }
                }
            }
        }

        if (!emd.getMessages().isEmpty())
            throw new ServiceException(emd);

        Chip chip = convertToEntity(data);
        chip.setCreated(Calendar.getInstance());

        return convertToDto(cController.save(chip));
    }

    @Override
    public Chip convertToEntity(ChipRequest data) {
        Chip chip = new Chip();

        chip.setMessenger(new Messenger(data.getMessenger()));
        chip.setType(data.getType());

        for (ChipItemRequest i : data.getItems())
            chip.addItem(new ChipItem(new Inventory(i.getElement()), i.getCount()));

        return chip;
    }

    @Override
    public ChipResponse convertToDto(Chip entity) {
        ChipResponse chip = new ChipResponse();

        chip.setId(entity.getId());
        chip.setMessenger(MessengerSimpleConverter.instance()
                .convertToDto(entity.getMessenger()));
        chip.setCreated(entity.getCreated());
        chip.setType(entity.getType());

        List<ChipItemResponse> items = new ArrayList<>();
        for (ChipItem i : entity.getItems()) {
            ChipItemResponse item = new ChipItemResponse();

            InventoryResponse element = new InventoryResponse();
            element.setId(i.getElement().getId());
            element.setName(i.getElement().getName());

            item.setElement(element);
            item.setCount(i.getCount());
            items.add(item);
        }
        chip.setItems(items);

        return chip;
    }
}
