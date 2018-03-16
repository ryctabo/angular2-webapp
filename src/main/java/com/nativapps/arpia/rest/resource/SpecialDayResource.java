package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.SpecialDayRequest;
import com.nativapps.arpia.model.dto.SpecialDayResponse;
import com.nativapps.arpia.service.ServiceFactory;
import com.nativapps.arpia.service.SpecialDayService;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Path("specialsdays")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class SpecialDayResource {

    @Context
    private HttpServletRequest request;

    private final SpecialDayService service = ServiceFactory.getSpecialDayService();

    @GET
    public List<SpecialDayResponse> getAll() {
        service.configurate(Collections.list(request.getLocales()));
        return service.getAll();
    }

    @GET
    @Path("{id}")
    public SpecialDayResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public Response post(SpecialDayRequest data, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        SpecialDayResponse shortcut = service.add(data);
        String newId = shortcut.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(location)
                .entity(shortcut)
                .build();
    }

    @PUT
    @Path("{id}")
    public SpecialDayResponse put(@PathParam("id") Long messengerId,
            SpecialDayRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(messengerId, data);
    }

    @DELETE
    @Path("{id}")
    public SpecialDayResponse delete(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(id);
    }

}
