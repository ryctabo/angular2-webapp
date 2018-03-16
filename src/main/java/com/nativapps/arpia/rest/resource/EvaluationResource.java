package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.EvaluationRequest;
import com.nativapps.arpia.model.dto.EvaluationResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.EvaluationService;
import com.nativapps.arpia.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Path("evaluations")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class EvaluationResource {

    private static final Logger LOG = Logger.getLogger(EvaluationResource.class.getName());

    @Context
    private HttpServletRequest request;

    private final EvaluationService service = ServiceFactory.getMessengerEvalService();

    @GET
    public ListResponse<EvaluationResponse> get(@QueryParam("start") int start,
                                                @QueryParam("size") Integer size,
                                                @QueryParam("messengerId") Long messengerId) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null)
            size = 25;
        return service.getAll(messengerId, start, size);
    }

    @GET
    @Path("{id}")
    public EvaluationResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public Response post(EvaluationRequest data,
                         @HeaderParam("Authorization") String authHeader,
                         @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        UserInfo userInfo = getUserInfo(authHeader);
        EvaluationResponse response = service.add(data, userInfo);
        String newID = response.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(location)
                .entity(response)
                .build();
    }

    /**
     * Get user information from the given {@code Authorization} header.
     *
     * @param authHeader the authorization header
     * @return user information
     */
    private UserInfo getUserInfo(String authHeader) {
        try {
            String authToken = authHeader.replaceAll("Bearer ", "");
            return TokenUtil.validateToken(authToken);
        } catch (TokenException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            throw new WebApplicationException(ex.getMessage(), ex);
        } catch (UnauthorizedException ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.UNAUTHORIZED);
        }
    }
}
