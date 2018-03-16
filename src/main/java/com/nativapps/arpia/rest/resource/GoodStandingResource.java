package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.GoodStandingRequest;
import com.nativapps.arpia.model.dto.GoodStandingResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.service.GoodStandingService;
import com.nativapps.arpia.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("goodstanding")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class GoodStandingResource {

    @Context
    private HttpServletRequest request;

    private final GoodStandingService service = ServiceFactory
            .getGoodStandingService();
    
    @GET
    public ListResponse<GoodStandingResponse> getAll(@QueryParam("start") 
            int start, @QueryParam("size") Integer size, 
            @QueryParam("messengerId") Long messengerId) {
        if (size == null)
            size = 20;
        service.configurate(Collections.list(request.getLocales()));
        return service.getAll(start, size, messengerId);
    }
    
    @POST
    public Response post(GoodStandingRequest data, 
            @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        GoodStandingResponse response = service.add(data);
        URI location = uriInfo.getAbsolutePathBuilder().path(response
                    .getId().toString()).build();
        return Response.created(location).entity(response).build();
    }
    
    @GET
    @Path("{id}")
    public GoodStandingResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }
    
    @PUT
    @Path("{id}/approve")
    public GoodStandingResponse approve(@PathParam("id") Long id, GoodStandingRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.approve(id, data);
    }
    
    @PUT
    @Path("{id}/deny")
    public GoodStandingResponse deny(@PathParam("id") Long id, GoodStandingRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.deny(id, data);
    }
    
    @Path("{id}/report")
    public GSReportResource gsReport() {
        return new GSReportResource(request);
    }
}
