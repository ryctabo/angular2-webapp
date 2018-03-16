package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.RestrictionRequest;
import com.nativapps.arpia.model.dto.RestrictionResponse;
import com.nativapps.arpia.service.RestrictionService;
import com.nativapps.arpia.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RestrictionResource {

    @Context
    private final HttpServletRequest request;

    private final RestrictionService service
            = ServiceFactory.getRestrictionService();

    public RestrictionResource(HttpServletRequest request) {
        this.request = request;
    }

    @POST
    public Response post(@PathParam("messengerId") Long messengerId,
            RestrictionRequest restriction, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        RestrictionResponse response = service.add(messengerId, restriction);
        String newId = response.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(location)
                .entity(response)
                .build();
    }

    @GET
    public RestrictionResponse get(@PathParam("messengerId") Long messengerId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(messengerId);
    }

    @PUT
    public RestrictionResponse update(@PathParam("messengerId") Long messengerId,
            RestrictionRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(messengerId, data);
    }
}
