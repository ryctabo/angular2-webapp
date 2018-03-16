package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.DomicileReviewRequest;
import com.nativapps.arpia.model.dto.DomicileReviewResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.DomicileReviewService;
import com.nativapps.arpia.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Path("domicilereview")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DomicileReviewResource {

    @Context
    private HttpServletRequest request;
    
    private final DomicileReviewService service = ServiceFactory
            .getDomicileReviewService();
    
    @GET
    public ListResponse<DomicileReviewResponse> getAll(@QueryParam("start")
            int start, @QueryParam("size") Integer size,
            @QueryParam("dexecId") Long dexecId){
        service.configurate(Collections.list(request.getLocales()));
        if (size == null)
            size = 20;
        
        return service.getAll(start, size, dexecId);
    }
    
    @GET
    @Path("{domicileExecuteId}")
    public DomicileReviewResponse get(@PathParam("domicileExecuteId") 
            Long domicileExecuteId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(domicileExecuteId);
    }
    
    @POST
    public Response post(DomicileReviewRequest data,
            @HeaderParam("Authorization") String authHeader,
            @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        try {
            String authToken = authHeader.replaceAll("Bearer ", "");
            DomicileReviewResponse response = service.add(data, TokenUtil
                    .validateToken(authToken));
            URI location = uriInfo.getAbsolutePathBuilder().path(response
                    .getId().toString()).build();
            
            return Response.created(location).entity(response).build();
        } catch (TokenException ex) {
            throw new WebApplicationException(ex.getMessage(), ex);
        } catch (UnauthorizedException ex) {
            throw new WebApplicationException(ex.getMessage(),
                    Response.Status.UNAUTHORIZED);
        }
    }
    
    @PUT
    @Path("{domicileExecuteId}")
    public DomicileReviewResponse put(@PathParam("domicileExecuteId") 
            Long domicileExecuteId, DomicileReviewRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(domicileExecuteId, data);
    }
}
