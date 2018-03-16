package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.SpecialDateRequest;
import com.nativapps.arpia.model.dto.SpecialDateResponse;
import com.nativapps.arpia.service.ServiceFactory;
import com.nativapps.arpia.service.SpecialDateService;
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
@Path("specialdates")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class SpecialDateResource {

    @Context
    private HttpServletRequest request;

    private final SpecialDateService service = ServiceFactory
            .getSpecialDateService();

    @GET
    public List<SpecialDateResponse> get() {
        service.configurate(Collections.list(request.getLocales()));
        return service.get();
    }

    @GET
    @Path("{id}")
    public SpecialDateResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public Response post(SpecialDateRequest specialDate, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        SpecialDateResponse newSpecialDate = service.add(specialDate);
        String newID = newSpecialDate.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(location)
                .entity(newSpecialDate)
                .build();
    }

    @PUT
    @Path("{id}")
    public SpecialDateResponse put(@PathParam("id") Long id,
            SpecialDateRequest specialDate) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(id, specialDate);
    }

    @DELETE
    @Path("{id}")
    public SpecialDateResponse delete(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(id);
    }

}
