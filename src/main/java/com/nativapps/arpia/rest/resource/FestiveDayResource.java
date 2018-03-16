package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.FestiveDayRequest;
import com.nativapps.arpia.model.dto.FestiveDayResponse;
import com.nativapps.arpia.service.FestiveDayService;
import com.nativapps.arpia.service.ServiceFactory;
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
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Path("festives")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class FestiveDayResource {

    @Context
    private HttpServletRequest request;

    private final FestiveDayService service = ServiceFactory.getFestiveDayService();

    @GET
    public List<FestiveDayResponse> get() {
        service.configurate(Collections.list(request.getLocales()));
        return service.get();
    }

    @GET
    @Path("{id}")
    public FestiveDayResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public Response post(FestiveDayRequest festiveDay, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        FestiveDayResponse newFestiveDay = service.add(festiveDay);
        String newID = newFestiveDay.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(location)
                .entity(newFestiveDay)
                .build();
    }

    @PUT
    @Path("{id}")
    public FestiveDayResponse put(@PathParam("id") Long id,
            FestiveDayRequest festiveDay) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(id, festiveDay);
    }

    @DELETE
    @Path("{id}")
    public FestiveDayResponse delete(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(id);
    }

}
