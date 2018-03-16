package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ShortcutRequest;
import com.nativapps.arpia.model.dto.ShortcutResponse;
import com.nativapps.arpia.service.ServiceFactory;
import com.nativapps.arpia.service.ShortcutService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
@Path("shortcuts")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ShortcutResource {

    @Context
    private HttpServletRequest request;

    private final ShortcutService service = ServiceFactory.getShortcutService();

    @GET
    public List<ShortcutResponse> getAll() {
        service.configurate(Collections.list(request.getLocales()));
        return service.get();
    }

    @GET
    @Path("{id}")
    public ShortcutResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @GET
    @Path("key/{key}")
    public ShortcutResponse get(@PathParam("key") String key) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getByKey(key);
    }

    @POST
    public Response post(ShortcutRequest data, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        ShortcutResponse shortcut = service.add(data);
        String newId = shortcut.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(location)
                .entity(shortcut)
                .build();
    }

    @PUT
    @Path("{id}")
    public ShortcutResponse put(@PathParam("id") Long messengerId, ShortcutRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(messengerId, data);
    }

    @DELETE
    @Path("{id}")
    public ShortcutResponse delete(@PathParam("id") Long id) {
        return service.delete(id);
    }
}
