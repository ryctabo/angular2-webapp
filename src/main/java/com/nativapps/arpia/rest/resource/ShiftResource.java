package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ShiftRequest;
import com.nativapps.arpia.model.dto.ShiftResponse;
import com.nativapps.arpia.service.ServiceFactory;
import com.nativapps.arpia.service.ShiftService;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
public class ShiftResource {
    
    @Context
    private HttpServletRequest request;
    
    private final ShiftService service = ServiceFactory.getShiftService();

    public ShiftResource(HttpServletRequest request) {
        this.request = request;
    }
    
    @GET
    public List<ShiftResponse> getAll(@PathParam("shiftplanningId") 
            Long shiftplanningId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getAll(shiftplanningId);
    }
    
    @GET
    @Path("{index}")
    public ShiftResponse get(@PathParam("shiftplanningId") Long shiftplanningId, 
            @PathParam("index") Integer index) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(shiftplanningId, index);
    }
    
    @POST
    public Response post(@PathParam("shiftplanningId") Long shiftplanningId, 
            ShiftRequest data, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        ShiftResponse response = service.add(shiftplanningId, data);
        URI location = uriInfo.getAbsolutePathBuilder().path(response.getIndex()
                .toString()).build();
        
        return Response.created(location).entity(response).build();
    }
    
    @PUT
    @Path("{index}")
    public ShiftResponse put(@PathParam("shiftplanningId") Long shiftplanningId, 
            @PathParam("index") Integer index, ShiftRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(shiftplanningId, index, data);
    }
    
    @DELETE
    @Path("{index}")
    public ShiftResponse delete(@PathParam("shiftplanningId") Long shiftplanningId, 
            @PathParam("index") Integer index) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(shiftplanningId, index);
    }
    
    @Path("{index}/assignments")
    public ShiftAssignmentResource assignments() {
        return new ShiftAssignmentResource(request);
    }
    
    @Path("{index}/checks")
    public ShiftCheckResource checks() {
        return new ShiftCheckResource(request);
    }
}
