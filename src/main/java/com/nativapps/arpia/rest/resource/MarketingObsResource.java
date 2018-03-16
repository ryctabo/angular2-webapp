package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MarketingObsRequest;
import com.nativapps.arpia.model.dto.MarketingObsResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.MarketingObsService;
import com.nativapps.arpia.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("marketingobs")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MarketingObsResource {

    @Context
    private HttpServletRequest request;
    
    private final MarketingObsService service = ServiceFactory
            .getMarketingObsService();
    
    @GET
    public ListResponse<MarketingObsResponse> getAll(@QueryParam("start") int start, 
            @QueryParam("size") Integer size, @QueryParam("customerId") Long customerId) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null)
            size = 20;
        
        return service.getAll(start, size, customerId);
    }
    
    @GET
    @Path("{id}")
    public MarketingObsResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }
    
    @POST
    public Response post(MarketingObsRequest data, @HeaderParam("Authorization") 
            String authHeader, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        try {
            String authToken = authHeader.replaceAll("Bearer ", "");
            MarketingObsResponse response = service.add(data, TokenUtil
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
    @Path("{id}")
    public MarketingObsResponse put(@PathParam("id") Long id, MarketingObsRequest data, 
            @HeaderParam("Authorization") String authHeader){
        service.configurate(Collections.list(request.getLocales()));
        try {
            String authToken = authHeader.replaceAll("Bearer ", "");
            return service.update(id, data, TokenUtil.validateToken(authToken));
        } catch (TokenException ex) {
            throw new WebApplicationException(ex.getMessage(), ex);
        } catch (UnauthorizedException ex) {
            throw new WebApplicationException(ex.getMessage(),
                    Response.Status.UNAUTHORIZED);
        }
    }
    
    @DELETE
    @Path("{id}")
    public MarketingObsResponse delete(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(id);
    }
}
