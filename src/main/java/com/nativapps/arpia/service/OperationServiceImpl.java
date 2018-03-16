package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.OperationDao;
import com.nativapps.arpia.database.dao.UserDao;
import com.nativapps.arpia.database.entity.Operation;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.database.entity.UserType;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.OperationRequest;
import com.nativapps.arpia.model.dto.OperationResponse;
import com.nativapps.arpia.model.dto.UserResponse;
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
 * @version 1.3.0
 */
public class OperationServiceImpl extends GenericService
        implements OperationService,
        DtoConverter<Operation, OperationRequest, OperationResponse> {

    private final OperationDao controller;

    public OperationServiceImpl(OperationDao controller) {
        this.controller = controller;
    }

    /**
     * Get information of operation entity from the given operation ID.
     *
     * @param id operation ID.
     * @return operation entity information.
     */
    private Operation getEntityOperation(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("operation.id_required"));
        Operation operation = controller.find(id);
        if (operation == null) {
            String m = String.format(config.getString("operation.not_found"), id);
            throw new NotFoundException(m);
        }
        return operation;
    }

    @Override
    public List<OperationResponse> getAll() {
        List<OperationResponse> companies = new ArrayList<>();
        for (Operation operation : controller.findAll())
            companies.add(convertToDto(operation));
        return companies;
    }

    @Override
    public OperationResponse get(Long id) {
        return convertToDto(getEntityOperation(id));
    }

    @Override
    public OperationResponse getOperationByName(String name) {
        if (TextUtil.isEmpty(name))
            throw new BadRequestException(config.getString("operation.name"));
        Operation operation = controller.findByName(name);
        if (operation == null) {
            String message = String.format(config.getString("operation.nnf"), name);
            throw new NotFoundException(message);
        }
        return convertToDto(operation);
    }

    @Override
    public OperationResponse add(OperationRequest operation) {
        if (operation == null)
            throw new BadRequestException(config.getString("operation.is_null"));

        int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        ErrorMessageData emd = new ErrorMessageData(statusCode);

        if (TextUtil.isEmpty(operation.getName()))
            emd.addMessage(config.getString("operation.name"));
        else if (controller.findByName(operation.getName()) != null)
            emd.addMessage(String.format(config.getString("operation.ne"),
                    operation.getName()));

        if (TextUtil.isEmpty(operation.getAlias()))
            emd.addMessage(config.getString("operation.alias"));
        else if (controller.findByAlias(operation.getAlias()) != null)
            emd.addMessage(String.format(config.getString("operation.ae"),
                    operation.getAlias()));

        if (TextUtil.isEmpty(operation.getAdministrator()))
            emd.addMessage(config.getString("operation.admin"));

        UserDao uController = EntityControllerFactory.getUserController();
        if (TextUtil.isEmpty(operation.getEmail()))
            emd.addMessage(config.getString("operation.email"));
        else if (!TextUtil.isEmail(operation.getEmail()))
            emd.addMessage(config.getString("operation.e_format"));
        else if (uController.findByEmail(operation.getEmail()) != null)
            emd.addMessage(String.format(config.getString("operation.e_exists"),
                    operation.getEmail()));

        if (TextUtil.isEmpty(operation.getPassword()))
            emd.addMessage("operation.pass");

        if (!emd.getMessages().isEmpty())
            throw new ServiceException(emd);

        //create a new user
        User adminUser = new User(operation.getEmail(), operation.getPassword(),
                operation.getAdministrator(), UserType.ADMINISTRATOR);

        Operation eOperation = convertToEntity(operation);
        eOperation.setAdminUser(adminUser);
        adminUser.getOperations().add(eOperation);

        return convertToDto(controller.save(eOperation));
    }

    @Override
    public OperationResponse update(Long id, OperationRequest operation) {
        if (operation == null)
            throw new BadRequestException(config.getString("operation.is_null"));

        Operation currentOperation = getEntityOperation(id);

        if (!TextUtil.isEmpty(operation.getName())
                && !currentOperation.getName().equals(operation.getName())) {
            if (controller.findByName(operation.getName()) != null) {
                String message = String.format(config.getString("operation.ne"),
                        operation.getName());
                throw new BadRequestException(message);
            }
            currentOperation.setName(operation.getName());
        }

        if (!TextUtil.isEmpty(operation.getAlias())
                && operation.getAlias().equals(currentOperation.getAlias())) {
            if (controller.findByAlias(operation.getAlias()) != null) {
                String msg = String.format(config.getString("operation.ae"),
                        operation.getAlias());
                throw new BadRequestException(msg);
            }
        }

        if (!TextUtil.isEmpty(operation.getDescription()))
            currentOperation.setDescription(operation.getDescription());
        if (!TextUtil.isEmpty(operation.getImageUrl()))
            currentOperation.setPicUrl(operation.getImageUrl());

        return convertToDto(controller.save(currentOperation));
    }

    @Override
    public OperationResponse delete(Long id) {
        OperationResponse operation = get(id);
        controller.delete(id);
        return operation;
    }

    @Override
    public Operation convertToEntity(OperationRequest d) {
        return new Operation(d.getName(), d.getAlias(), d.getDescription(), d.getImageUrl());
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
    public OperationResponse convertToDto(Operation entity) {
        OperationResponse operation = new OperationResponse();

        operation.setId(entity.getId());
        operation.setName(entity.getName());
        operation.setAlias(entity.getAlias());
        operation.setDescription(entity.getDescription());
        operation.setImageUrl(entity.getPicUrl());
        operation.setAdminUser(convertToDtoUser(entity.getAdminUser()));

        return operation;
    }

}
