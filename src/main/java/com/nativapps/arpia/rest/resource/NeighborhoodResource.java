package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.service.NeighborhoodService;
import com.nativapps.arpia.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collections;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.2.0
 */
@Path("neighborhoods")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class NeighborhoodResource {

    @Context
    private HttpServletRequest request;

    private final NeighborhoodService service = ServiceFactory.getNeighborhoodService();

    @GET
    public ListResponse<NeighborhoodResponse> getAll(@QueryParam("start") int start,
                                                     @QueryParam("size") Integer size,
                                                     @QueryParam("search") String search) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null)
            size = 20;
        return service.getAll(start, size, search);
    }

    @POST
    public Response post(NeighborhoodRequest data, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        NeighborhoodResponse response = service.add(data);
        String newID = response.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(location)
                .entity(response).build();
    }

    @GET
    @Path("{id}")
    public NeighborhoodResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @PUT
    @Path("{id}")
    public NeighborhoodResponse put(@PathParam("id") Long id, NeighborhoodRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(id, data);
    }

    @DELETE
    @Path("{id}")
    public NeighborhoodResponse delete(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(id);
    }

    @GET
    @Path("{id}/domiciles")
    public ListResponse<DomicileExecuteResponse> getDomiciles(@PathParam("id") Long id,
                                                              @QueryParam("start") int start,
                                                              @QueryParam("size") Integer size) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null)
            size = 25;
        return service.getDomiciles(id, start, size);
    }

    @GET
    @Path("{id}/customers")
    public ListResponse<CustomerDataDto> getCustomers(@PathParam("id") Long id,
                                                      @QueryParam("start") int start,
                                                      @QueryParam("size") Integer size) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null)
            size = 25;
        return service.getCustomers(id, start, size);
    }

}
