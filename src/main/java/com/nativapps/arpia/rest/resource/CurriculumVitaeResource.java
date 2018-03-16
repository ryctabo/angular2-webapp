package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.CurriculumVitaeRequest;
import com.nativapps.arpia.model.dto.CurriculumVitaeResponse;
import com.nativapps.arpia.service.CurriculumVitaeService;
import com.nativapps.arpia.service.ServiceFactory;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Path("cvs")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class CurriculumVitaeResource {

    @Context
    private HttpServletRequest request;

    private final CurriculumVitaeService service
            = ServiceFactory.getCurriculumVitaeService();

    @GET
    public CurriculumVitaeResponse getByMessengerId(@QueryParam("messenger") Long mID) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getByMessengerId(mID);
    }

    @GET
    @Path("{cvId}")
    public CurriculumVitaeResponse get(@PathParam("cvId") Long cvId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(cvId);
    }

    @PUT
    @Path("{cvId}")
    public CurriculumVitaeResponse update(@PathParam("cvId") Long cvId,
            CurriculumVitaeRequest cv) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(cvId, cv);
    }

    @Path("{cvId}/references")
    public ReferenceResource getReferences() {
        return new ReferenceResource(request);
    }

}
