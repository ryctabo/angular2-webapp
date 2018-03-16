package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ShiftCheckKeyRequest;
import com.nativapps.arpia.model.dto.ShiftCheckKeyResponse;
import com.nativapps.arpia.service.ServiceFactory;
import com.nativapps.arpia.service.ShiftCheckKeyService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ShiftCheckKeyResource {

    @Context
    private HttpServletRequest request;

    private final ShiftCheckKeyService service = ServiceFactory
            .getShiftCheckKeyService();

    public ShiftCheckKeyResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    public List<ShiftCheckKeyResponse> getAll(
            @PathParam("shiftplanningId") Long shiftplanningId,
            @PathParam("index") Integer shiftIndex,
            @QueryParam("messengerId") Long messengerId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getAll(shiftplanningId, shiftIndex, messengerId);
    }

    @GET
    @Path("generate")
    public ShiftCheckKeyResponse generateKey(@PathParam("shiftplanningId") Long shiftplanningId,
                                             @PathParam("index") Integer shiftIndex,
                                             @QueryParam("messengerId") Long messengerId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.generateKey(shiftplanningId, shiftIndex, messengerId);
    }

    @POST
    @Path("use")
    public ShiftCheckKeyResponse useKey(@PathParam("shiftplanningId") Long shiftplanningId,
                                        @PathParam("index") Integer shiftIndex,
                                        @QueryParam("messengerId") Long messengerId,
                                        ShiftCheckKeyRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.useKey(shiftplanningId, shiftIndex, messengerId, data);
    }
}
