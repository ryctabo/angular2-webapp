package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MessengerPlanningResponse;
import com.nativapps.arpia.model.dto.ShiftplanningRequest;
import com.nativapps.arpia.model.dto.ShiftplanningResponse;
import com.nativapps.arpia.service.ServiceFactory;
import com.nativapps.arpia.service.ShiftplanningService;
import java.net.URI;
import java.util.Collections;
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
@Path("shiftplanning")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ShiftplanningResource {

    @Context
    private HttpServletRequest request;

    private final ShiftplanningService service = ServiceFactory
            .getShiftplanningService();

    @GET
    public ListResponse<ShiftplanningResponse> getAll(@QueryParam("start") 
            int start, @QueryParam("size") Integer size,
            @QueryParam("operationId") Long operationId) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null)
            size = 20;
        return service.getAll(start, size, operationId);
    }

    @GET
    @Path("{id}")
    public ShiftplanningResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public Response post(ShiftplanningRequest data, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        ShiftplanningResponse response = service.add(data);
        URI location = uriInfo.getAbsolutePathBuilder().path(response
                .getId().toString()).build();

        return Response.created(location).entity(response).build();
    }
    
    @DELETE
    @Path("{id}")
    public ShiftplanningResponse delete(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(id);
    }
    
    @Path("{shiftplanningId}/shifts")
    public ShiftResource shifts() {
        return new ShiftResource(request);
    }
    
    @GET
    @Path("{id}/messengers")
    public ListResponse<MessengerPlanningResponse> messengerPlanning(@QueryParam("start") 
            int start, @QueryParam("size") Integer size, @PathParam("id") Long id,
            @QueryParam("assign") Boolean assign) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null)
            size = 20;
        return service.getMessengerPlanning(start, size, id, assign);
    }
}