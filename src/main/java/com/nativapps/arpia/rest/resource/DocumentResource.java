package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.DocumentRequest;
import com.nativapps.arpia.model.dto.DocumentResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.service.DocumentService;
import com.nativapps.arpia.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Path("documents")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DocumentResource {

    @Context
    private HttpServletRequest request;

    private final DocumentService service = ServiceFactory.getDocumentService();

    @GET
    public ListResponse<DocumentResponse> getAll(@QueryParam("start") int start,
            @QueryParam("size") Integer size, @QueryParam("search") String search) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null || size < 0)
            size = 20;
        return service.getAll(start, size, search);
    }

    @GET
    @Path("{id}")
    public DocumentResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public Response post(DocumentRequest data, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        DocumentResponse document = service.add(data);
        URI location = uriInfo.getAbsolutePathBuilder().path(document.getId()
                .toString()).build();
        return Response.created(location).entity(document).build();
    }

    @PUT
    @Path("{id}")
    public DocumentResponse put(@PathParam("id") Long id, DocumentRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(id, data);
    }

    @DELETE
    @Path("{id}")
    public DocumentResponse delete(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(id);
    }
}
