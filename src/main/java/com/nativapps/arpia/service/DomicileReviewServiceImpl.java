package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.DomicileExecuteDao;
import com.nativapps.arpia.database.dao.DomicileReviewDao;
import com.nativapps.arpia.database.entity.DomicileExecute;
import com.nativapps.arpia.database.entity.DomicileReview;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.dto.DomicileReviewRequest;
import com.nativapps.arpia.model.dto.DomicileReviewResponse;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.UserResponse;
import com.nativapps.arpia.rest.UserInfo;
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
 * @version 1.0.1
 */
public class DomicileReviewServiceImpl extends GenericService
        implements DomicileReviewService, DtoConverter<DomicileReview, DomicileReviewRequest, DomicileReviewResponse> {

    private final DomicileReviewDao domicileReviewDao;

    private final DomicileExecuteDao domicileExecuteDao;

    public DomicileReviewServiceImpl(DomicileReviewDao domicileReviewDao,
            DomicileExecuteDao domicileExecuteDao) {
        this.domicileReviewDao = domicileReviewDao;
        this.domicileExecuteDao = domicileExecuteDao;
    }

    @Override
    public ListResponse<DomicileReviewResponse> getAll(int start, int size, Long dexecId) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<DomicileReviewResponse> response = new ArrayList<>();
        for (DomicileReview domicileReview : domicileReviewDao.findAll(start, size, dexecId)) {
            response.add(convertToDto(domicileReview));
        }

        return new ListResponse<>(domicileReviewDao.findAllCount(dexecId), response);
    }

    @Override
    public DomicileReviewResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    /**
     * Search a domicile review by domicile execute ID
     *
     * @param id Search ID
     * @return Searched review
     */
    private DomicileReview getEntity(Long id) {
        if (id == null)
            throw new BadRequestException(config
                    .getString("domicile_review.id_required"));

        DomicileReview entity = domicileReviewDao.find(id);
        if (entity == null) {
            String error = String.format(config
                    .getString("domicile_review.not_found"), id);
            throw new NotFoundException(error);
        }

        return entity;
    }

    @Override
    public DomicileReviewResponse add(DomicileReviewRequest data, UserInfo userInfo) {
        if (data == null)
            throw new BadRequestException(config.getString("domicile_review.is_null"));
        else {
            ErrorMessageData emd = new ErrorMessageData(Response.Status.BAD_REQUEST
                    .getStatusCode());
            if (TextUtil.isEmpty(data.getObservations()))
                emd.addMessage(config.getString("domicile_review.observations_"
                        + "required"));
            if (data.getDomicileExecuteId() == null)
                emd.addMessage(config.getString("domicile_review.domicile_"
                        + "execute_id_required"));

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }

        DomicileReview entity = convertToEntity(data);
        DomicileExecute dExec = domicileExecuteDao.find(data.getDomicileExecuteId());
        if (dExec == null) {
            String error = String.format(config
                    .getString("domicile_execute.not_found"), data
                    .getDomicileExecuteId());
            throw new BadRequestException(error);
        }

        entity.setDomicileExecute(dExec);
        entity.setRegisterDate(Calendar.getInstance());
        entity.setUser(new User(userInfo.getId()));

        return convertToDto(domicileReviewDao.save(entity));
    }

    @Override
    public DomicileReviewResponse update(Long domicileExecuteId, DomicileReviewRequest data) {
        DomicileReview entity = getEntity(domicileExecuteId);

        if (data != null) {
            if (data.getObservations() != null)
                entity.setObservations(data.getObservations());
            if (data.getDomicileExecuteId() != null) {
                DomicileExecute dExec = domicileExecuteDao.find(data
                        .getDomicileExecuteId());
                if (dExec != null)
                    entity.setDomicileExecute(dExec);
            }
        }

        return convertToDto(domicileReviewDao.save(entity));
    }

    @Override
    public DomicileReview convertToEntity(DomicileReviewRequest data) {
        DomicileReview entity = new DomicileReview();

        entity.setObservations(data.getObservations());

        return entity;
    }

    /**
     * Convert from user entity to user response.
     *
     * @param user user entity
     * @return user response
     */
    private UserResponse convertToDtoUser(User user) {
        if (user == null)
            return null;

        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setDisplayName(user.getDisplayName());
        response.setEmail(user.getEmail());
        response.setUrlPic(user.getUrlPic());
        response.setUsername(user.getUsername());

        return response;
    }

    @Override
    public DomicileReviewResponse convertToDto(DomicileReview entity) {
        DomicileReviewResponse response = new DomicileReviewResponse();

        response.setId(entity.getId());
        response.setDomicileExecuteId(entity.getDomicileExecute().getId());
        response.setObservations(entity.getObservations());
        response.setRegisterDate(entity.getRegisterDate());
        response.setUser(convertToDtoUser(entity.getUser()));

        return response;
    }

}
