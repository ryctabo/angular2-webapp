package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.WorkShiftPointRequest;
import com.nativapps.arpia.model.dto.WorkShiftPointResponse;
import com.nativapps.arpia.service.ServiceFactory;
import com.nativapps.arpia.service.WorkShiftPointService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collections;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Path("workshiftpoints")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class WorkShiftPoint {

    @Context
    private HttpServletRequest request;

    private final WorkShiftPointService service = ServiceFactory.getWorkShiftPointService();

    @GET
    public ListResponse<WorkShiftPointResponse> get(@QueryParam("mapPoint") Long mapPointId,
                                                    @QueryParam("start") int start,
                                                    @QueryParam("size") Integer size) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null)
            size = 25;
        return this.service.get(mapPointId, start, size);
    }

    @POST
    public Response post(WorkShiftPointRequest workShiftPoint, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        WorkShiftPointResponse response = this.service.add(workShiftPoint);
        String newID = response.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(location)
                .entity(response)
                .build();
    }

    @GET
    @Path("{id}")
    public WorkShiftPointResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return this.service.get(id);
    }

    @PUT
    @Path("{id}")
    public WorkShiftPointResponse put(@PathParam("id") Long id, WorkShiftPointRequest workShiftPoint) {
        service.configurate(Collections.list(request.getLocales()));
        return this.service.update(id, workShiftPoint);
    }

}
