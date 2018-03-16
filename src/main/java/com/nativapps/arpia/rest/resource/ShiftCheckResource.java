package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ShiftCheckRequest;
import com.nativapps.arpia.model.dto.ShiftCheckResponse;
import com.nativapps.arpia.service.ServiceFactory;
import com.nativapps.arpia.service.ShiftCheckService;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ShiftCheckResource {
    
    @Context
    private HttpServletRequest request;
    
    private final ShiftCheckService service = ServiceFactory.getCheckService();

    public ShiftCheckResource(HttpServletRequest request) {
        this.request = request;
    }
    
    @GET
    public List<ShiftCheckResponse> getAll(@PathParam("shiftplanningId") 
            Long shiftplanningId, @PathParam("index") Integer shiftIndex, 
            @QueryParam("messengerId") Long messengerId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getAll(shiftplanningId, shiftIndex, messengerId);
    }

    @POST
    @Path("clockin")
    public ShiftCheckResponse clockin(@PathParam("shiftplanningId") 
            Long shiftplanningId, @PathParam("index") Integer shiftIndex,
            ShiftCheckRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.clockin(shiftplanningId, shiftIndex, data);
    }
    
    @PUT
    @Path("clockout")
    public ShiftCheckResponse clockout(@PathParam("shiftplanningId") 
            Long shiftplanningId, @PathParam("index") Integer shiftIndex,
            ShiftCheckRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.clockout(shiftplanningId, shiftIndex, data);
    }

    @Path("keys")
    public ShiftCheckKeyResource keys() {
        return new ShiftCheckKeyResource(request);
    }
}
