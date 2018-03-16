package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.LoginData;
import com.nativapps.arpia.model.dto.TokenData;
import com.nativapps.arpia.model.dto.UserResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.AccountService;
import com.nativapps.arpia.service.ServiceFactory;
import com.nativapps.arpia.service.UserService;
import com.nativapps.arpia.util.TextUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.1
 */
@Path("accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    private static final Logger LOG = Logger.getLogger(AccountResource.class.getName());

    @Context
    private HttpServletRequest request;

    private final AccountService service = ServiceFactory.getUserService();

    @GET
    public UserResponse getUser(@QueryParam("q") String userData) {
        service.configurate(Collections.list(request.getLocales()));
        UserResponse user = ((UserService) service).getUserSearch(userData);
        cleanUnusedProperties(user);
        return user;
    }

    /**
     * Delete properties unused in this endpoint.
     *
     * @param user user information
     */
    private void cleanUnusedProperties(UserResponse user) {
        user.setCreated(null);
        user.setEnabled(null);
        user.setId(null);
        user.setUpdated(null);
        user.setOperations(null);
        user.setRoleId(null);
        user.setType(null);
    }

    @POST
    @Path("authenticate")
    public TokenData login(LoginData login) {
        service.configurate(Collections.list(request.getLocales()));
        if (login == null)
            throw new BadRequestException("The login information is required.");
        return service.login(login.getUsername(), login.getPassword());
    }

    @POST
    @Path("refresh")
    public TokenData refresh(@HeaderParam("Authorization") String authHeader) {
        try {
            if (TextUtil.isEmpty(authHeader))
                throw new BadRequestException("The Authorization header param is required.");

            String authToken = authHeader.replaceAll("Bearer ", "");
            UserInfo userInfo = TokenUtil.validateToken(authToken);

            return userInfo != null ? new TokenData(TokenUtil.generateToken(userInfo)) : null;
        } catch (TokenException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new WebApplicationException(ex.getMessage(), ex);
        } catch (UnauthorizedException ex) {
            throw new WebApplicationException(ex.getMessage(),
                    Response.Status.UNAUTHORIZED);
        }
    }

}
