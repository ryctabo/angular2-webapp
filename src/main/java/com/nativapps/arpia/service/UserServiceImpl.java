package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.OperationDao;
import com.nativapps.arpia.database.dao.RoleDao;
import com.nativapps.arpia.database.dao.UserDao;
import com.nativapps.arpia.database.entity.Operation;
import com.nativapps.arpia.database.entity.Role;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.database.entity.UserType;
import com.nativapps.arpia.database.exception.IncorrectCredentialsException;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.TextUtil;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.1.0
 */
public class UserServiceImpl extends GenericService implements UserService,
        DtoConverter<User, UserRequest, UserResponse> {

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class.getName());

    private final UserDao controller;

    public UserServiceImpl(UserDao controller) {
        this.controller = controller;
    }

    /**
     * Get user information in the database from the given user ID.
     *
     * @param userId user ID.
     * @return user information.
     * @throws BadRequestException if the user ID is null or less than or equal to 0.
     * @throws NotFoundException   if the user obtained is null
     */
    private User getUserEntity(Long userId) throws BadRequestException, NotFoundException {
        if (userId == null || userId <= 0)
            throw new BadRequestException(config.getString("user.id_required"));

        User user = controller.find(userId);
        if (user == null) {
            final String FORMAT = config.getString("user.not_found");
            throw new NotFoundException(String.format(FORMAT, userId));
        }

        return user;
    }

    /**
     * Validate if the user data contains all properties for save.
     *
     * @param user             user data
     * @param validatePassword true, if you need validate password
     * @param email            email if it exists
     * @param username         username if it exists
     * @throws BadRequestException if the user data is null
     * @throws ServiceException    if the user data does contain all the necessary properties
     */
    private void validate(UserRequest user, boolean validatePassword, String email, String username)
            throws BadRequestException, ServiceException {
        if (user == null) {
            throw new BadRequestException(config.getString("user.is_null"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);

            //validate email
            if (TextUtil.isEmpty(user.getEmail())) {
                emd.addMessage(config.getString("user.email"));
            } else if (!TextUtil.isEmail(user.getEmail())) {
                emd.addMessage(config.getString("user.e_format"));
            } else if (!user.getEmail().equalsIgnoreCase(email)
                    && controller.findByEmail(user.getEmail()) != null) {
                final String FORMAT = config.getString("user.e_exists");
                emd.addMessage(String.format(FORMAT, user.getEmail()));
            }

            //validate username if this exists
            if (!TextUtil.isEmpty(user.getUsername())) {
                if (!TextUtil.isUsername(user.getUsername())) {
                    emd.addMessage(config.getString("user.username"));
                } else if (!user.getUsername().equalsIgnoreCase(username)
                        && controller.findByUsername(user.getUsername()) != null) {
                    final String FORMAT = config.getString("user.u_exists");
                    emd.addMessage(String.format(FORMAT, user.getUsername()));
                }
            }

            //validate password
            if (validatePassword && TextUtil.isEmpty(user.getPassword())
                    || user.getPassword().length() < 6)
                emd.addMessage(config.getString("user.pwd"));

            //validate display name
            if (TextUtil.isEmpty(user.getDisplayName()))
                emd.addMessage(config.getString("user.dname"));

            //validate operations
            if (user.getOperations() == null || user.getOperations().isEmpty()) {
                emd.addMessage(config.getString("user.operation"));
            } else {
                OperationDao oController = EntityControllerFactory.getOperationController();
                for (Long operationId : user.getOperations()) {
                    if (operationId == null || operationId <= 0) {
                        emd.addMessage(config.getString("operation.id_required"));
                    } else if (oController.find(operationId) == null) {
                        final String FORMAT = config.getString("operation.not_found");
                        emd.addMessage(String.format(FORMAT, operationId));
                    }
                }
            }

            //validate user type
            if (user.getType() == null) {
                emd.addMessage(config.getString("user.type"));
            } else if (user.getType() == UserType.OPERATOR) {
                //validate role ID if user type is operator
                RoleDao rController = EntityControllerFactory.getRoleController();
                if (user.getRoleId() == null || user.getRoleId() <= 0) {
                    emd.addMessage(config.getString("user.role"));
                } else if (rController.find(user.getRoleId()) == null) {
                    final String FORMAT = config.getString("role.not_found");
                    emd.addMessage(String.format(FORMAT, user.getRoleId()));
                }
            } else if (user.getType().equals(UserType.SUPER_USER)) {
                emd.addMessage(config.getString("user.nsuper"));
            }

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }
    }

    @Override
    public ListResponse<UserResponse> getAll(UserType type, String search, int start, int size,
                                             String orderBy, OrderType orderType) {
        List<UserResponse> users = new ArrayList<>();
        List<User> response = this.controller.findAll(type, search, start, size, orderBy, orderType);

        for (User user : response)
            users.add(convertToDto(user));

        long count = this.controller.count(type, search);
        return new ListResponse<>(count, users);
    }

    @Override
    public UserResponse getUser(Long userId) {
        return convertToDto(getUserEntity(userId));
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        if (TextUtil.isEmpty(email))
            throw new BadRequestException(config.getString("user.email"));
        User user = controller.findByEmail(email);
        if (user == null) {
            final String FORMAT = config.getString("user.not_found1");
            throw new BadRequestException(String.format(FORMAT, email));
        }
        return convertToDto(user);
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        if (TextUtil.isEmpty(username))
            throw new BadRequestException(config.getString("user.username"));
        User user = controller.findByUsername(username);
        if (user == null) {
            final String FORMAT = config.getString("user.not_found2");
            throw new BadRequestException(String.format(FORMAT, username));
        }
        return convertToDto(user);
    }

    @Override
    public UserResponse getUserSearch(String data) {
        if (TextUtil.isEmpty(data))
            throw new BadRequestException(config.getString("user.search"));
        User user = controller.search(data);
        if (user == null) {
            final String FORMAT = config.getString("user.not_found3");
            throw new NotFoundException(String.format(FORMAT, data));
        }
        return convertToDto(user);
    }

    @Override
    public UserResponse addNewUser(UserRequest user) {
        validate(user, true, null, null);
        return convertToDto(controller.save(convertToEntity(user)));
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest user) {
        User currentUser = getUserEntity(userId);
        this.validate(user, false, currentUser.getEmail(), currentUser.getUsername());

        if (userId == 1L)
            throw new BadRequestException(config.getString("user.unmodifiable"));

        currentUser.setDisplayName(user.getDisplayName());
        currentUser.setEmail(user.getEmail());
        currentUser.setRole(new Role(user.getRoleId()));
        currentUser.setUrlPic(user.getUrlPic());
        currentUser.setUsername(user.getUsername());

        List<Operation> operations = new ArrayList<>();
        for (Long operationId : user.getOperations())
            operations.add(new Operation(operationId));

        currentUser.setOperations(operations);

        currentUser.setUpdated(Calendar.getInstance());

        return convertToDto(controller.save(currentUser));
    }

    @Override
    public UserResponse deleteUser(Long userId) {
        UserResponse user = getUser(userId);

        if (user.getType() == UserType.ADMINISTRATOR || user.getType() == UserType.SUPER_USER)
            throw new BadRequestException(config.getString("user.delete"));

        controller.delete(userId);
        return user;
    }

    @Override
    public UserResponse changeStateUser(Long userId, boolean enable) {
        User user = getUserEntity(userId);
        user.setEnabled(enable);
        return convertToDto(controller.save(user));
    }

    @Override
    public TokenData login(String userData, String password) {
        int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        ErrorMessageData emd = new ErrorMessageData(statusCode);

        if (TextUtil.isEmpty(userData))
            emd.addMessage(config.getString("login.username"));
        if (TextUtil.isEmpty(password))
            emd.addMessage(config.getString("login.password"));

        if (!emd.getMessages().isEmpty())
            throw new ServiceException(emd);

        try {
            User userLogged = controller.login(userData, password);
            String token = TokenUtil.generateToken(convertToDto(userLogged));
            return new TokenData(token);
        } catch (IncorrectCredentialsException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new WebApplicationException(ex.getMessage(), Response.Status.UNAUTHORIZED);
        } catch (TokenException ex) {
            LOG.log(Level.SEVERE, "Error generating token.", ex);
            throw new WebApplicationException(ex.getMessage(), ex);
        }
    }

    @Override
    public User convertToEntity(UserRequest request) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setDisplayName(request.getDisplayName());
        user.setUrlPic(request.getUrlPic());
        user.setType(request.getType());

        if (request.getRoleId() != null)
            user.setRole(new Role(request.getRoleId()));

        List<Operation> operations = new ArrayList<>();
        if (request.getOperations() != null && !request.getOperations().isEmpty()) {
            for (Long operationId : request.getOperations())
                operations.add(new Operation(operationId));
        }
        user.setOperations(operations);

        return user;
    }

    @Override
    public UserResponse convertToDto(User entity) {
        UserResponse user = new UserResponse();

        user.setId(entity.getId());
        user.setCreated(entity.getCreated());
        user.setEnabled(entity.isEnabled());
        user.setUpdated(entity.getUpdated());
        user.setDisplayName(entity.getDisplayName());
        user.setEmail(entity.getEmail());

        List<Long> operations = new ArrayList<>();
        if (entity.getOperations() != null) {
            for (Operation operation : entity.getOperations())
                operations.add(operation.getId());
        }
        user.setOperations(operations);

        user.setRoleId(entity.getRoleId());
        user.setType(entity.getType());
        user.setUrlPic(entity.getUrlPic());
        user.setUsername(entity.getUsername());

        return user;
    }
}
