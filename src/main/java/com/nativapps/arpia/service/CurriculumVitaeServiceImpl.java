package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.CurriculumVitaeDao;
import com.nativapps.arpia.database.entity.CurriculumVitae;
import com.nativapps.arpia.database.entity.Home;
import com.nativapps.arpia.database.entity.SocialSecurity;
import com.nativapps.arpia.model.dto.CurriculumVitaeRequest;
import com.nativapps.arpia.model.dto.CurriculumVitaeResponse;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.HomeData;
import com.nativapps.arpia.model.dto.SocialSecurityData;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.MessengerValidator;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class CurriculumVitaeServiceImpl extends GenericService implements CurriculumVitaeService,
        DtoConverter<CurriculumVitae, CurriculumVitaeRequest, CurriculumVitaeResponse> {

    private final CurriculumVitaeDao controller;

    public CurriculumVitaeServiceImpl(CurriculumVitaeDao controller) {
        this.controller = controller;
    }

    /**
     * Get the curriculum vitae entity from the given ID.
     *
     * @param id the curriculum vitae ID
     * @return the curriculum vitae data
     *
     * @throws BadRequestException if the curriculum vitae ID is null or less
     * than or equal to 0.
     * @throws NotFoundException if the curriculum vitae obtained is null.
     */
    private CurriculumVitae getEntity(Long id) throws NotFoundException,
            BadRequestException {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("curriculum_vitae.id"));
        CurriculumVitae cv = controller.find(id);
        if (cv == null) {
            final String FORMAT = config.getString("curriculum_vitae.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return cv;
    }

    /**
     * Validate if the curriculum vitae have all attributes for save to
     * database.
     *
     * @param messenger messenger to validate
     *
     * @throws BadRequestException if the CV data is null
     * @throws ServiceException if the CV data don't have any attribute required
     */
    private void validate(CurriculumVitaeRequest cv) throws BadRequestException,
            ServiceException {
        if (cv == null) {
            throw new BadRequestException("curriculum_vitae.required");
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);

            MessengerValidator.evaluateCurriculumVitaeData(cv, emd, config);

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }
    }

    @Override
    public CurriculumVitaeResponse getByMessengerId(Long messengerId) {
        if (messengerId == null || messengerId <= 0)
            throw new BadRequestException(config.getString("messenger.id"));

        CurriculumVitae cv = controller.findByMessengerId(messengerId);
        if (cv == null) {
            final String FORMAT = config.getString("curriculum_vitae.mnot_found");
            throw new NotFoundException(String.format(FORMAT, messengerId));
        }

        return convertToDto(cv);
    }

    @Override
    public CurriculumVitaeResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public CurriculumVitaeResponse update(Long id, CurriculumVitaeRequest cv) {
        //verify if exists a CV with ID provided and get the current CV
        CurriculumVitae currentCV = getEntity(id);
        //validate the data provided.
        validate(cv);

        //change curriculim vitae
        currentCV.setAcademicLevel(cv.getAcademicLevel());
        currentCV.setBirthCity(cv.getBirthCity());
        currentCV.setDistrict(cv.getDistrict());
        currentCV.setMilitaryCard(cv.getMilitaryCard());
        currentCV.setCivilStatus(cv.getCivilStatus());

        //change home data
        HomeData home = cv.getHome();
        currentCV.getHome().setHomeownership(home.getHomeownership());
        currentCV.getHome().setMonthlyExpenses(home.getMonthlyExpenses());
        currentCV.getHome().setSonsNumber(home.getSonsNumber());
        currentCV.getHome().setStratum(home.getStratum());

        //change social security data
        SocialSecurityData ss = cv.getSocialSecurity();
        currentCV.getSocialSecurity().setNameARL(ss.getNameARL());
        currentCV.getSocialSecurity().setNameEPS(ss.getNameEPS());
        currentCV.getSocialSecurity().setPensionEntity(ss.getPensionEntity());

        return convertToDto(controller.save(currentCV));
    }

    @Override
    public CurriculumVitae convertToEntity(CurriculumVitaeRequest data) {
        return null;
    }

    @Override
    public CurriculumVitaeResponse convertToDto(CurriculumVitae entity) {
        //convert social security
        SocialSecurity ss = entity.getSocialSecurity();
        SocialSecurityData ssd = new SocialSecurityData(ss.getNameEPS(),
                ss.getNameARL(), ss.getPensionEntity());

        //convert home
        Home h = entity.getHome();
        HomeData hd = new HomeData(h.getStratum(), h.getSonsNumber(),
                h.getHomeownership(), h.getMonthlyExpenses());

        //convert curriculum vitae
        CurriculumVitaeResponse response = new CurriculumVitaeResponse();
        response.setId(entity.getId());
        response.setAcademicLevel(entity.getAcademicLevel());
        response.setBirthCity(entity.getBirthCity());
        response.setCivilStatus(entity.getCivilStatus());
        response.setDistrict(entity.getDistrict());
        response.setHome(hd);
        response.setMilitaryCard(entity.getMilitaryCard());
        response.setSocialSecurity(ssd);

        return response;
    }

}
