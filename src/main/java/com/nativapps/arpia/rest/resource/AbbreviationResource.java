package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.AbbreviationRequest;
import com.nativapps.arpia.model.dto.AbbreviationResponse;
import com.nativapps.arpia.service.AbbreviationService;
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
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
@Path("abbreviations")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class AbbreviationResource {

    @Context
    private HttpServletRequest request;

    private final AbbreviationService service = ServiceFactory.getabAbbreviationService();

    @GET
    public List<AbbreviationResponse> get() {
        service.configurate(Collections.list(request.getLocales()));
        return service.get();
    }

    @GET
    @Path("{id}")
    public AbbreviationResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @GET
    @Path("st/{shortText}")
    public AbbreviationResponse get(@PathParam("shortText") String shortText) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(shortText);
    }

    @POST
    public Response post(AbbreviationRequest data, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        AbbreviationResponse abbreviationResponse = service.add(data);
        String newId = abbreviationResponse.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(location)
                .entity(abbreviationResponse)
                .build();
    }

    @PUT
    @Path("{id}")
    public AbbreviationResponse put(@PathParam("id") Long id, AbbreviationRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(id, data);
    }

    @DELETE
    @Path("{id}")
    public AbbreviationResponse delete(@PathParam("id") Long id) {
        return service.delete(id);
    }
}
