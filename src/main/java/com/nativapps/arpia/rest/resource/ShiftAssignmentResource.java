package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ShiftAssignRequest;
import com.nativapps.arpia.model.dto.ShiftAssignResponse;
import com.nativapps.arpia.service.ServiceFactory;
import com.nativapps.arpia.service.ShiftAssignmentService;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ShiftAssignmentResource {

    @Context
    private HttpServletRequest request;
    
    private final ShiftAssignmentService service = ServiceFactory
            .getShiftAssignmentService();

    public ShiftAssignmentResource(HttpServletRequest request) {
        this.request = request;
    }
    
    @GET
    public List<ShiftAssignResponse> getAll(@PathParam("shiftplanningId") 
            Long shiftplanningId, @PathParam("index") Integer index,
            @QueryParam("clockin") Boolean clockin, @QueryParam("clockout")
            Boolean clockout) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getAll(shiftplanningId, index, clockin, clockout);
    }
    
    @POST
    public Response assign(@PathParam("shiftplanningId") 
            Long shiftplanningId, @PathParam("index") Integer index, 
            ShiftAssignRequest data, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        ShiftAssignResponse assign = service.assign(shiftplanningId, index, data);
        URI location = uriInfo.getAbsolutePathBuilder().path(data
                .getMessengerId().toString()).build();
        
        return Response.created(location).entity(assign).build();
    }
    
    @DELETE
    @Path("{messengerId}")
    public ShiftAssignResponse delete(@PathParam("shiftplanningId") 
            Long shiftplanningId, @PathParam("index") Integer index,
            @PathParam("messengerId") Long messengerId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(shiftplanningId, index, messengerId);
    }
}
