package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.CurriculumVitaeDao;
import com.nativapps.arpia.database.dao.ReferenceDao;
import com.nativapps.arpia.database.entity.CurriculumVitae;
import com.nativapps.arpia.database.entity.Reference;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ReferenceRequest;
import com.nativapps.arpia.model.dto.ReferenceResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.ReferenceValidator;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 3.0
 */
public class ReferenceServiceImpl extends GenericService implements ReferenceService,
        DtoConverter<Reference, ReferenceRequest, ReferenceResponse> {

    private final ReferenceDao rfController;

    private final CurriculumVitaeDao cvController;

    public ReferenceServiceImpl(ReferenceDao rfController, CurriculumVitaeDao cvController) {
        this.rfController = rfController;
        this.cvController = cvController;
    }

    /**
     * Get reference entity from the given parameters.
     *
     * @param curriculumVitaeId the curriculum vitae ID
     * @param index             the INDEX of element
     * @return reference entity
     */
    private Reference getEntity(Long curriculumVitaeId, Integer index) {
        if (curriculumVitaeId == null || curriculumVitaeId <= 0)
            throw new BadRequestException(config.getString("curriculum_vitae.id"));
        if (index == null || index <= 0)
            throw new BadRequestException(config.getString("reference.index"));

        Reference.ReferencePK primaryKey = new Reference.ReferencePK(index, curriculumVitaeId);
        Reference reference = rfController.find(primaryKey);
        if (reference == null) {
            final String FORMAT = config.getString("reference.not_found");
            throw new NotFoundException(String.format(FORMAT, index, curriculumVitaeId));
        }

        return reference;
    }

    /**
     * Validate if messenger contains all attributes for save to database.
     *
     * @param reference the reference request.
     */
    private void validate(ReferenceRequest reference) {
        if (reference == null)
            throw new BadRequestException(config.getString("reference.is_null"));

        int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        ErrorMessageData emd = new ErrorMessageData(statusCode);
        ReferenceValidator.evaluateReference(reference, emd, config);

        if (!emd.getMessages().isEmpty())
            throw new ServiceException(emd);
    }

    @Override
    public List<ReferenceResponse> get(Long curriculumVitaeId) {
        if (curriculumVitaeId == null || curriculumVitaeId <= 0)
            throw new BadRequestException(config.getString("curriculum_vitae.id"));

        List<ReferenceResponse> references = new ArrayList<>();
        for (Reference reference : rfController.findAll(curriculumVitaeId))
            references.add(convertToDto(reference));

        return references;
    }

    @Override
    public ReferenceResponse get(Long curriculumVitaeId, Integer index) {
        return convertToDto(getEntity(curriculumVitaeId, index));
    }

    @Override
    public ReferenceResponse add(Long curriculumVitaeId, ReferenceRequest data) {
        validate(data); // validate reference data

        if (curriculumVitaeId == null || curriculumVitaeId <= 0)
            throw new BadRequestException(config.getString("curriculum_vitae.id"));
        CurriculumVitae cv = cvController.find(curriculumVitaeId);
        if (cv == null) {
            final String FORMAT = config.getString("curriculum_vitae.not_found");
            throw new NotFoundException(String.format(FORMAT, curriculumVitaeId));
        }

        Reference reference = convertToEntity(data);

        int index = ((int) rfController.count(curriculumVitaeId)) + 1;
        reference.setId(new Reference.ReferencePK(index, curriculumVitaeId));
        reference.setCurriculumVitae(cv);

        return convertToDto(rfController.save(reference));
    }

    @Override
    public ReferenceResponse update(Long curriculumVitaeId, Integer index, ReferenceRequest data) {
        validate(data);
        Reference reference = getEntity(curriculumVitaeId, index);

        reference.setName(data.getName().trim());
        reference.setLastName(data.getLastName().trim());
        reference.setAddress(data.getAddress().trim());
        reference.setPhone(data.getPhoneNumber().trim());
        reference.setRelationship(data.getRelationship());
        reference.setType(data.getType());

        return convertToDto(rfController.save(reference));
    }

    @Override
    public ReferenceResponse delete(Long curriculumVitaeId, Integer index) {
        ReferenceResponse reference = get(curriculumVitaeId, index);
        rfController.delete(new Reference.ReferencePK(index, curriculumVitaeId));
        return reference;
    }

    @Override
    public Reference convertToEntity(ReferenceRequest data) {
        Reference reference = new Reference();

        reference.setName(data.getName().trim());
        reference.setLastName(data.getLastName().trim());
        reference.setAddress(data.getAddress().trim());
        reference.setPhone(data.getPhoneNumber().trim());
        reference.setRelationship(data.getRelationship());
        reference.setType(data.getType());

        return reference;
    }

    @Override
    public ReferenceResponse convertToDto(Reference entity) {
        ReferenceResponse reference = new ReferenceResponse();

        reference.setIndex(entity.getId().getIndex());
        reference.setName(entity.getName());
        reference.setLastName(entity.getLastName());
        reference.setAddress(entity.getAddress());
        reference.setPhoneNumber(entity.getPhone());
        reference.setType(entity.getType());
        reference.setRelationship(entity.getRelationship());

        return reference;
    }
}
