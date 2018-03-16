package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.DiscountChangeLogResponse;
import com.nativapps.arpia.service.DiscountChangeLogService;
import com.nativapps.arpia.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */

@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DiscountChangeLogResource {

    private final HttpServletRequest request;

    private final DiscountChangeLogService service = ServiceFactory.getDiscountChangeLogService();

    public DiscountChangeLogResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    public List<DiscountChangeLogResponse> getChanges(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getLogs(id);
    }

    @GET
    @Path("{logIndex}")
    public DiscountChangeLogResponse getChange(@PathParam("id") Long id, @PathParam("logIndex") Integer logIndex) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getLog(id, logIndex);
    }
}
