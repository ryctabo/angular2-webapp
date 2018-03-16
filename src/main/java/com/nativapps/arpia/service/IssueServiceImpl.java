package com.nativapps.arpia.service;

import com.nativapps.arpia.database.entity.Issue;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.IssueRequest;
import com.nativapps.arpia.model.dto.IssueResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import com.nativapps.arpia.database.dao.IssueDao;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class IssueServiceImpl extends GenericService implements IssueService,
        DtoConverter<Issue, IssueRequest, IssueResponse> {

    private final IssueDao dao;

    public IssueServiceImpl(IssueDao dao) {
        this.dao = dao;
    }

    @Override
    public ListResponse<IssueResponse> getAll(int start, int size) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<IssueResponse> response = new ArrayList<>();
        for (Issue novelty : dao.findAll(start, size)) {
            response.add(convertToDto(novelty));
        }

        return new ListResponse<>(dao.findAllCount(), response);
    }

    @Override
    public IssueResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    private Issue getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("issue.id_required"));

        Issue entity = dao.find(id);
        if (entity == null)
            throw new NotFoundException(String.format(config
                    .getString("issue.not_found"), id));

        return entity;
    }

    @Override
    public IssueResponse add(IssueRequest data) {
        if (data == null)
            throw new BadRequestException(config.getString("issue.is_null"));
        else {
            ErrorMessageData errors = new ErrorMessageData(Response.Status.BAD_REQUEST
                    .getStatusCode());
            if (data.getName() == null)
                errors.addMessage(config.getString("issue.name_required"));
            if (data.getShortName() == null || data.getShortName().length() > 3)
                errors.addMessage(config.getString("issue.short_name_required"));
            
            if (!errors.getMessages().isEmpty())
                throw new ServiceException(errors);
        }
        
        return convertToDto(dao.save(convertToEntity(data)));
    }

    @Override
    public IssueResponse update(Long id, IssueRequest data) {
        Issue entity = getEntity(id);
        
        if (data.getName() != null)
            entity.setName(data.getName());
        if (data.getShortName() != null)
            entity.setShortName(data.getShortName());
        
        return convertToDto(dao.save(entity));
    }

    @Override
    public IssueResponse delete(Long id) {
        IssueResponse response = get(id);
        dao.delete(id);
        return response;
    }

    @Override
    public Issue convertToEntity(IssueRequest data) {
        return new Issue(data.getName(), data.getShortName());
    }

    @Override
    public IssueResponse convertToDto(Issue entity) {
        return new IssueResponse(entity.getId(), entity.getName(), 
                entity.getShortName());
    }

}
