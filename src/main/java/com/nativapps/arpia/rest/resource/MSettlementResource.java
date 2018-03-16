package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MSettlementRequest;
import com.nativapps.arpia.model.dto.MSettlementResponse;
import com.nativapps.arpia.service.MSettlementService;
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
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
@Path("msettlement")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MSettlementResource {

    @Context
    private HttpServletRequest request;

    private final MSettlementService service = ServiceFactory.getMSettlementService();

    @GET
    public ListResponse<MSettlementResponse> getAll(@QueryParam("start") int start,
                                                    @QueryParam("size") Integer size,
                                                    @QueryParam("messengerId") Long messengerId){
        service.configurate(Collections.list(request.getLocales()));
        if (size == null || size <= 0)
            size = 20;
        return service.getAll(start, size, messengerId);
    }

    @GET
    @Path("{id}")
    public MSettlementResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    @Path("pre")
    public MSettlementResponse pre(MSettlementRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.pre(data);
    }

    @POST
    public Response post(MSettlementRequest data, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        MSettlementResponse response = service.add(data);

        URI location = uriInfo.getAbsolutePathBuilder().path(response.getId().toString()).build();
        return Response.created(location).entity(response).build();
    }
}
