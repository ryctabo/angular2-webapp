package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MarketingMessageRequest;
import com.nativapps.arpia.model.dto.MarketingMessageResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.MarketingMessageService;
import com.nativapps.arpia.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
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
@Path("marketingmessages")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MarketingMessageResource {

    @Context
    private HttpServletRequest request;
    
    private final MarketingMessageService service = ServiceFactory
            .getMarketingMessageService();
    
    @GET
    public ListResponse<MarketingMessageResponse> getAll(@QueryParam("start") 
            int start, @QueryParam("size") Integer size, 
            @QueryParam("customerId") Long customerId) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null)
            size = 20;
        
        return service.getAll(start, size, customerId);
    }
    
    @GET
    @Path("{id}")
    public MarketingMessageResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }
    
    @POST
    public Response post(MarketingMessageRequest data, @Context UriInfo uriInfo, 
            @HeaderParam("Authorization") String authHeader) {
        service.configurate(Collections.list(request.getLocales()));
        
        try {
            String authToken = authHeader.replaceAll("Bearer ", "");
            MarketingMessageResponse response = service.add(data, TokenUtil
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
    
    @DELETE
    @Path("{id}")
    public MarketingMessageResponse delete(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(id);
    }
}
