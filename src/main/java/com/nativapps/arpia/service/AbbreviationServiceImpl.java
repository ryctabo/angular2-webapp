package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.AbbreviationDao;
import com.nativapps.arpia.database.entity.Abbreviation;
import com.nativapps.arpia.model.dto.AbbreviationRequest;
import com.nativapps.arpia.model.dto.AbbreviationResponse;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.TextUtil;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public class AbbreviationServiceImpl extends GenericService implements AbbreviationService,
        DtoConverter<Abbreviation, AbbreviationRequest, AbbreviationResponse> {

    private final AbbreviationDao controller;

    public AbbreviationServiceImpl(AbbreviationDao controller) {
        this.controller = controller;
    }

    /**
     * Get abbreviation entity from the given ID.
     *
     * @param id the abbreviation ID
     * @return the abbreviation data.
     */
    private Abbreviation getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("abbreviation.id"));
        Abbreviation abbreviation = controller.find(id);
        if (abbreviation == null) {
            final String FORMAT = config.getString("abbreviation.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return abbreviation;
    }

    /**
     * Validate if abbreviation data contains all attributes required.
     *
     * @param data abbreviation data
     * @param id   the abbreviation ID
     */
    private void validate(AbbreviationRequest data, Long id) {
        if (data == null) {
            throw new BadRequestException(config.getString("abbreviation.required"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);

            String shortText = data.getShortText();
            if (TextUtil.isEmpty(shortText) || shortText.trim().length() > 5) {
                emd.addMessage(config.getString("abbreviation.short_text"));
            } else {
                Abbreviation a = controller.findByShortText(shortText);
                if (id == null && a != null || id != null && a != null && !id.equals(a.getId()))
                    emd.addMessage(config.getString("abbreviation.already_exists"));
            }

            String content = data.getContent();
            if (TextUtil.isEmpty(content) || content.trim().length() > 200)
                emd.addMessage(config.getString("abbreviation.content"));

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }
    }

    @Override
    public List<AbbreviationResponse> get() {
        List<AbbreviationResponse> abbreviations = new ArrayList<>();
        for (Abbreviation abbreviation : controller.findAll())
            abbreviations.add(convertToDto(abbreviation));
        return abbreviations;
    }

    @Override
    public AbbreviationResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public AbbreviationResponse get(String shortText) {
        if (TextUtil.isEmpty(shortText))
            throw new BadRequestException(config.getString("abbreviation.short_text"));
        Abbreviation abbreviation = controller.findByShortText(shortText);
        if (abbreviation == null) {
            final String FORMAT = config.getString("abbreviation.not_found_st");
            throw new NotFoundException(String.format(FORMAT, shortText));
        }
        return convertToDto(abbreviation);
    }

    @Override
    public AbbreviationResponse add(AbbreviationRequest data) {
        validate(data, null);
        return convertToDto(controller.save(convertToEntity(data)));
    }

    @Override
    public AbbreviationResponse update(Long id, AbbreviationRequest data) {
        validate(data, id);
        Abbreviation abbreviation = getEntity(id);

        abbreviation.setShortText(data.getShortText());
        abbreviation.setContent(data.getContent());

        return convertToDto(controller.save(abbreviation));
    }

    @Override
    public AbbreviationResponse delete(Long id) {
        AbbreviationResponse abbreviation = get(id);
        controller.delete(id);
        return abbreviation;
    }

    @Override
    public Abbreviation convertToEntity(AbbreviationRequest d) {
        return new Abbreviation(d.getShortText().trim(), d.getContent().trim());
    }

    @Override
    public AbbreviationResponse convertToDto(Abbreviation e) {
        return new AbbreviationResponse(e.getId(), e.getShortText(), e.getContent());
    }
}
