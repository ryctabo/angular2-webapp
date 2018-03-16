package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.IssueRequest;
import com.nativapps.arpia.model.dto.IssueResponse;
import com.nativapps.arpia.service.ServiceFactory;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import com.nativapps.arpia.service.IssueService;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Path("issues")
public class IssueResource {

    @Context
    private HttpServletRequest request;

    private final IssueService service = ServiceFactory.getIssueService();

    @GET
    public ListResponse<IssueResponse> getAll(@QueryParam("start") int start,
            @QueryParam("size") Integer size) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null)
            size = 20;

        return service.getAll(start, size);
    }

    @GET
    @Path("{id}")
    public IssueResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public IssueResponse add(IssueRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.add(data);
    }

    @PUT
    @Path("{id}")
    public IssueResponse put(@PathParam("id") Long id, IssueRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(id, data);
    }
    
    @DELETE
    @Path("{id}")
    public IssueResponse delete(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(id);
    }
}
