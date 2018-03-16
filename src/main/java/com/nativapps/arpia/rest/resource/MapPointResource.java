package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MapPointRequest;
import com.nativapps.arpia.model.dto.MapPointResponse;
import com.nativapps.arpia.rest.bean.GenericFilterBean;
import com.nativapps.arpia.service.MapPointService;
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
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Path("points")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MapPointResource {

    @Context
    private HttpServletRequest request;

    private final MapPointService service = ServiceFactory.getMapPointService();

    @GET
    public ListResponse<MapPointResponse> get(@BeanParam GenericFilterBean bean) {
        service.configurate(Collections.list(request.getLocales()));
        if (bean.getSize() == null)
            bean.setSize(50);
        return service.get(bean.getSearch(), bean.getStart(), bean.getSize(),
                bean.getOrderBy(), bean.getOrderType());
    }

    @POST
    public Response post(MapPointRequest mapPoint, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        MapPointResponse newPoint = service.add(mapPoint);
        String newId = newPoint.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(location)
                .entity(newPoint)
                .build();
    }

    @GET
    @Path("{id}")
    public MapPointResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @PUT
    @Path("{id}")
    public MapPointResponse put(@PathParam("id") Long id, MapPointRequest mapPoint) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(id, mapPoint);
    }

    @DELETE
    @Path("{id}")
    public MapPointResponse delete(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(id);
    }

}
