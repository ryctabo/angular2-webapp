package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.BaseRequest;
import com.nativapps.arpia.model.dto.BaseResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.BaseService;
import com.nativapps.arpia.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
@Path("bases")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class BaseResource {

    @Context
    private HttpServletRequest request;

    private final BaseService service = ServiceFactory.getBaseService();
    
    @GET
    public ListResponse<BaseResponse> getAll(@QueryParam("start")int start, 
            @QueryParam("size")Integer size, 
            @QueryParam("messengerId") Long messengerId,
            @QueryParam("debt") Boolean debt){
        service.configurate(Collections.list(request.getLocales()));
        if (size == null || size <= 0)
            size = 20;
        return service.getAll(start, size, messengerId, debt);
    }
    
    @GET
    @Path("{id}")
    public BaseResponse get(@PathParam("id") Long baseId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(baseId);
    }
    
    @POST
    public Response post(BaseRequest data, @HeaderParam("Authorization") 
            String authHeader, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        
        try {
            String authToken = authHeader.replaceAll("Bearer ", "");
            BaseResponse response = service.add(null, data, TokenUtil
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
    
    @POST
    @Path("{id}")
    public BaseResponse post(@PathParam("id") Long baseId, BaseRequest data, 
            @HeaderParam("Authorization") String authHeader) {
        service.configurate(Collections.list(request.getLocales()));
        
        try {
            String authToken = authHeader.replaceAll("Bearer ", "");
            return service.add(baseId, data, TokenUtil.validateToken(authToken));
        } catch (TokenException ex) {
            throw new WebApplicationException(ex.getMessage(), ex);
        } catch (UnauthorizedException ex) {
            throw new WebApplicationException(ex.getMessage(),
                    Response.Status.UNAUTHORIZED);
        }
    }
}
