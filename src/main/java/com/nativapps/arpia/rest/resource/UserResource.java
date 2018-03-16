package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.UserRequest;
import com.nativapps.arpia.model.dto.UserResponse;
import com.nativapps.arpia.rest.bean.UserBean;
import com.nativapps.arpia.service.ServiceFactory;
import com.nativapps.arpia.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collections;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
@Path("users")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UserResource {

    @Context
    private HttpServletRequest request;

    private final UserService service = ServiceFactory.getUserService();

    @GET
    public ListResponse<UserResponse> getAllUsers(@BeanParam UserBean bean) {
        service.configurate(Collections.list(request.getLocales()));
        if (bean.getSize() == null) {
            bean.setSize(25);
        }
        return service.getAll(bean.getType(), bean.getSearch(), bean.getStart(),
                bean.getSize(), bean.getOrderBy(), bean.getOrderType());
    }

    @GET
    @Path("{userId}")
    public UserResponse getUser(@PathParam("userId") Long userId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getUser(userId);
    }

    @GET
    @Path("username")
    public UserResponse getByUsername(@QueryParam("q") String username) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getUserByUsername(username);
    }

    @GET
    @Path("email")
    public UserResponse getByEmail(@QueryParam("q") String email) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getUserByEmail(email);
    }

    @POST
    public Response addNewUser(UserRequest userData, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        UserResponse newUser = service.addNewUser(userData);
        String newId = newUser.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(location)
                .entity(newUser)
                .build();
    }

    @PUT
    @Path("{userId}")
    public UserResponse updateUser(@PathParam("userId") Long userId,
            UserRequest user) {
        service.configurate(Collections.list(request.getLocales()));
        return service.updateUser(userId, user);
    }

    @PUT
    @Path("{userId}/disable")
    public UserResponse disableUser(@PathParam("userId") Long userId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.changeStateUser(userId, false);
    }

    @PUT
    @Path("{userId}/enable")
    public UserResponse enableUser(@PathParam("userId") Long userId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.changeStateUser(userId, true);
    }

    @DELETE
    @Path("{userId}")
    public UserResponse deleteUser(@PathParam("userId") Long userId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.deleteUser(userId);
    }

    @Path("{userId}/deposits")
    public SecurityDepositParamResource depositsParam() {
        return new SecurityDepositParamResource(request);
    }

}
