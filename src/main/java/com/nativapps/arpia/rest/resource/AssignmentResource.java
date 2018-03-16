package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.AssignmentRequest;
import com.nativapps.arpia.model.dto.AssignmentResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.AssignmentService;
import com.nativapps.arpia.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class AssignmentResource {

    private static final Logger LOG = Logger.getLogger(AssignmentResource.class.getName());

    @Context
    private HttpServletRequest request;

    private final AssignmentService service = ServiceFactory.getAssignmentService();

    public AssignmentResource() {
    }

    public AssignmentResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    public List<AssignmentResponse> get(@PathParam("id") Long domicileExeId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getAll(domicileExeId);
    }

    @GET
    @Path("{index}")
    public AssignmentResponse get(@PathParam("id") Long domicileExeId, @PathParam("index") Integer index) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(domicileExeId, index);
    }

    @POST
    public Response post(@PathParam("id") Long domicileExeId, AssignmentRequest data,
                         @Context UriInfo uriInfo, @HeaderParam("Authorization") String authHeader) {
        service.configurate(Collections.list(request.getLocales()));

        //get user information
        String token = authHeader.replaceAll("Bearer ", "");
        UserInfo userInfo = null;
        try {
            userInfo = TokenUtil.validateToken(token);
        } catch (TokenException | UnauthorizedException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }

        AssignmentResponse response = service.assign(domicileExeId, data, userInfo);
        String newId = response.getIndex().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(location)
                .entity(response)
                .build();
    }

}
