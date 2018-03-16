package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.ShortcutDao;
import com.nativapps.arpia.database.entity.Shortcut;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ShortcutRequest;
import com.nativapps.arpia.model.dto.ShortcutResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.TextUtil;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public class ShortcutServiceImpl extends GenericService implements ShortcutService,
        DtoConverter<Shortcut, ShortcutRequest, ShortcutResponse> {

    private final ShortcutDao controller;

    public ShortcutServiceImpl(ShortcutDao controller) {
        this.controller = controller;
    }

    /**
     * Get shortcut from the given ID.
     *
     * @param id shortcut ID
     * @return shortcut data
     */
    private Shortcut getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("shortcut.id"));

        Shortcut shortcut = controller.find(id);
        if (shortcut == null) {
            final String FORMAT = config.getString("shortcut.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }

        return shortcut;
    }

    /**
     * Validate if shortcut have all attributes necessary for save.
     *
     * @param data shortcut data
     * @param id   shortcut ID
     */
    private void validate(ShortcutRequest data, Long id) {
        if (data == null) {
            throw new BadRequestException(config.getString("shortcut.is_null"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);

            if (TextUtil.isEmpty(data.getKey())) {
                emd.addMessage(config.getString("shortcut.key"));
            } else {
                Shortcut shortcut = controller.findByKey(data.getKey());
                if (id == null && shortcut != null || id != null && shortcut != null && !shortcut.getId().equals(id)) {
                    final String FORMAT = config.getString("shortcut.key_existing");
                    throw new BadRequestException(String.format(FORMAT, data.getKey()));
                }
            }

            if (TextUtil.isEmpty(data.getCommand()))
                emd.addMessage(config.getString("shortcut.command"));
            if (TextUtil.isEmpty(data.getWhen()))
                emd.addMessage(config.getString("shortcut.when"));

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }
    }

    @Override
    public List<ShortcutResponse> get() {
        List<ShortcutResponse> shortcuts = new ArrayList<>();
        for (Shortcut shortcut : controller.findAll()) {
            shortcuts.add(convertToDto(shortcut));
        }
        return shortcuts;
    }

    @Override
    public ShortcutResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public ShortcutResponse getByKey(String key) {
        if (key == null)
            throw new BadRequestException(config.getString("shortcut.key_find"));
        Shortcut shortcut = controller.findByKey(key);
        if (shortcut == null) {
            final String FORMAT = config.getString("shortcut.not_found_key");
            throw new NotFoundException(String.format(FORMAT, key));
        }
        return convertToDto(shortcut);
    }

    @Override
    public ShortcutResponse add(ShortcutRequest data) {
        validate(data, null);
        return convertToDto(controller.save(convertToEntity(data)));
    }

    @Override
    public ShortcutResponse update(Long id, ShortcutRequest data) {
        validate(data, id);
        Shortcut shortcut = getEntity(id);

        shortcut.setKey(data.getKey());
        shortcut.setCommand(data.getCommand());
        shortcut.setWhen(data.getWhen());
        shortcut.setDescription(data.getDescription());

        return convertToDto(controller.save(shortcut));
    }

    @Override
    public ShortcutResponse delete(Long id) {
        ShortcutResponse shortcut = get(id);
        controller.delete(id);
        return shortcut;
    }

    @Override
    public Shortcut convertToEntity(ShortcutRequest data) {
        return new Shortcut(
                data.getKey(),
                data.getCommand(),
                data.getWhen(),
                data.getDescription()
        );
    }

    @Override
    public ShortcutResponse convertToDto(Shortcut entity) {
        return new ShortcutResponse(
                entity.getId(),
                entity.getKey(),
                entity.getCommand(),
                entity.getWhen(),
                entity.getDescription()
        );
    }

}
