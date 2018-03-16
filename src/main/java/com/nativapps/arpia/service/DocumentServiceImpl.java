package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.DocumentDao;
import com.nativapps.arpia.database.entity.Document;
import com.nativapps.arpia.database.entity.DocumentLog;
import com.nativapps.arpia.model.dto.DocumentLogData;
import com.nativapps.arpia.model.dto.DocumentRequest;
import com.nativapps.arpia.model.dto.DocumentResponse;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.TextUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class DocumentServiceImpl extends GenericService
        implements DocumentService, DtoConverter<Document, DocumentRequest, DocumentResponse> {

    private final DocumentDao controller;

    public DocumentServiceImpl(DocumentDao controller) {
        this.controller = controller;
    }

    @Override
    public ListResponse<DocumentResponse> getAll(int start, int size, String search) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<DocumentResponse> response = new ArrayList<>();
        for (Document document : controller.findAll(start, size, search)) {
            response.add(convertToDto(document));
        }

        return new ListResponse<>(controller.findAllCount(search), response);
    }

    @Override
    public DocumentResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    /**
     * Returns a document entity by provided ID
     *
     * @param id Document ID
     * @return Searched document entity
     */
    public Document getEntity(Long id) {
        if (id == null || id < 0)
            throw new BadRequestException(config
                    .getString("document.id_required"));

        Document document = controller.find(id);

        if (document == null)
            throw new NotFoundException(String.format(config
                    .getString("document.not_found"), id));

        return document;
    }

    @Override
    public DocumentResponse add(DocumentRequest data) {
        if (data == null)
            throw new BadRequestException(config.getString("document.is_null"));

        ErrorMessageData errors = new ErrorMessageData(Response.Status.BAD_REQUEST.getStatusCode());
        if (!TextUtil.isEmpty(data.getName())) {
            if (controller.findByName(data.getName()) != null)
                errors.addMessage(String.format(config
                        .getString("document.name_exists"), data.getName()));
        } else
            errors.addMessage(config.getString("document.name_required"));

        if (!TextUtil.isEmpty(data.getUrl())) {
            if (controller.findByUrl(data.getUrl()) != null)
                errors.addMessage(String.format(config
                        .getString("document.url_exists"), data.getUrl()));
        } else
            errors.addMessage(config.getString("document.url_required"));

        if (!errors.getMessages().isEmpty())
            throw new ServiceException(errors);

        return convertToDto(controller.save(convertToEntity(data)));
    }

    @Override
    public DocumentResponse update(Long id, DocumentRequest data) {
        Document entity = getEntity(id);

        if (!TextUtil.isEmpty(data.getName()))
            entity.setName(data.getName());
        if (!TextUtil.isEmpty(data.getDescription()))
            entity.setDescription(data.getDescription());
        if (!TextUtil.isEmpty(data.getUrl()) && !data.getUrl().equals(entity
                .getUrl())) {
            entity.setUrl(data.getUrl());
            entity.addLog(new DocumentLog(data.getUrl(), Calendar.getInstance()));
        }
        if (data.getExpirationDate() != null)
            entity.setExpirationDate(data.getExpirationDate());
        if (data.getVisible() != null)
            entity.setVisible(data.getVisible());

        return convertToDto(controller.save(entity));
    }

    @Override
    public DocumentResponse delete(Long id) {
        DocumentResponse response = get(id);
        controller.delete(id);
        return response;
    }

    @Override
    public Document convertToEntity(DocumentRequest data) {
        Document document = new Document(data.getName(), data.getDescription(),
                data.getUrl(), data.getExpirationDate(), data.getVisible());
        document.addLog(new DocumentLog(data.getUrl(), Calendar.getInstance()));

        return document;
    }

    @Override
    public DocumentResponse convertToDto(Document entity) {
        DocumentResponse response = new DocumentResponse(entity.getId(),
                entity.getName(), entity.getDescription(), entity.getUrl(),
                entity.getExpirationDate(), entity.isVisible());
        for (DocumentLog documentLog : entity.getLog()) {
            response.getLog().add(new DocumentLogData(documentLog.getId(),
                    documentLog.getUrl(), documentLog.getRegisterDate()));
        }

        return response;
    }

}
