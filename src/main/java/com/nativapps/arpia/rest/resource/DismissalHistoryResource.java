package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.DismissalHistoryRequest;
import com.nativapps.arpia.model.dto.DismissalHistoryResponse;
import com.nativapps.arpia.service.DismissalHistoryService;
import com.nativapps.arpia.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Path("dismissals")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DismissalHistoryResource {

    @Context
    private HttpServletRequest request;

    private final DismissalHistoryService service = ServiceFactory
            .getDismissalHistoryService();

    @GET
    public List<DismissalHistoryResponse> getAll(@QueryParam("messengerId") Long messengerId) {
        service.configurate(Collections.list(request.getLocales()));
        if (messengerId != null)
            return service.getAll(messengerId);
        return service.getAll();
    }

    @GET
    @Path("{dismissalId}")
    public DismissalHistoryResponse get(@PathParam("dismissalId") Long dismissalId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(dismissalId);
    }

    @POST
    public Response post(DismissalHistoryRequest dismissal, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        DismissalHistoryResponse newDismissal = service.add(dismissal);
        String newId = newDismissal.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(location)
                .entity(newDismissal)
                .build();
    }

}
