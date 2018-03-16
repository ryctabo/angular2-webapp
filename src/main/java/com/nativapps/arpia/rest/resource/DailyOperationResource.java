package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.DailyOperationResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.DailyOperationService;
import com.nativapps.arpia.service.ServiceFactory;
import java.util.Collections;
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

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DailyOperationResource {

    @Context
    private HttpServletRequest request;

    private final DailyOperationService service = ServiceFactory
            .getDailyOperationService();

    public DailyOperationResource(HttpServletRequest request) {
        this.request = request;
    }
    
    @GET
    public ListResponse<DailyOperationResponse> getAll(@QueryParam("start") 
            int start, @QueryParam("size") Integer size, 
            @PathParam("operationId") Long operationId) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null)
            size = 20;
        return service.getAll(start, size, operationId);
    }
    
    @GET
    @Path("{id}")
    public DailyOperationResponse get(@PathParam("operationId") Long operationId, 
            @PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(operationId, id);
    }
    
    @POST
    @Path("open")
    public DailyOperationResponse open(@PathParam("operationId") Long operationId,
            @HeaderParam("Authorization") String authHeader) {
        service.configurate(Collections.list(request.getLocales()));
        try {
            String authToken = authHeader.replaceAll("Bearer ", "");
            return service.open(operationId, TokenUtil.validateToken(authToken)); 
        } catch (TokenException ex) {
            throw new WebApplicationException(ex.getMessage(), ex);
        } catch (UnauthorizedException ex) {
            throw new WebApplicationException(ex.getMessage(),
                    Response.Status.UNAUTHORIZED);
        }
    }
    
    @POST
    @Path("close")
    public DailyOperationResponse close(@PathParam("operationId") Long operationId,
            @HeaderParam("Authorization") String authHeader) {
        service.configurate(Collections.list(request.getLocales()));
        try {
            String authToken = authHeader.replaceAll("Bearer ", "");
            return service.close(operationId, TokenUtil.validateToken(authToken)); 
        } catch (TokenException ex) {
            throw new WebApplicationException(ex.getMessage(), ex);
        } catch (UnauthorizedException ex) {
            throw new WebApplicationException(ex.getMessage(),
                    Response.Status.UNAUTHORIZED);
        }
    }
}
