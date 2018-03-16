package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ReferenceRequest;
import com.nativapps.arpia.model.dto.ReferenceResponse;
import com.nativapps.arpia.service.ReferenceService;
import com.nativapps.arpia.service.ServiceFactory;

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
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ReferenceResource {

    @Context
    private HttpServletRequest request;

    private final ReferenceService service = ServiceFactory.getReferenceService();

    public ReferenceResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    public List<ReferenceResponse> getAll(@PathParam("cvId") Long cvId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(cvId);
    }

    @GET
    @Path("{index}")
    public ReferenceResponse get(@PathParam("cvId") Long cvId, @PathParam("index") Integer index) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(cvId, index);
    }

    @POST
    public Response post(@PathParam("cvId") Long cvId, ReferenceRequest reference, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        ReferenceResponse newReference = service.add(cvId, reference);
        String newId = newReference.getIndex().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(location)
                .entity(reference)
                .build();
    }

    @PUT
    @Path("{index}")
    public ReferenceResponse update(@PathParam("index") Integer index,
                                    @PathParam("cvId") Long cvId,
                                    ReferenceRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(cvId, index, data);
    }

    @DELETE
    @Path("{index}")
    public ReferenceResponse delete(@PathParam("cvId") Long cvId, @PathParam("index") Integer index) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(cvId, index);
    }

}
