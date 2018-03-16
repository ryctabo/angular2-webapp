package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ChipRequest;
import com.nativapps.arpia.model.dto.ChipResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.bean.ChipBean;
import com.nativapps.arpia.service.ChipService;
import com.nativapps.arpia.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collections;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Path("chips")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ChipResource {

    @Context
    private HttpServletRequest request;

    private final ChipService service = ServiceFactory.getChipService();

    @GET
    public ListResponse<ChipResponse> get(@BeanParam ChipBean bean) {
        service.configurate(Collections.list(request.getLocales()));
        if (bean.getSize() == null) bean.setSize(25);
        return service.get(bean.getMessengerId(), bean.getType(),
                bean.getStart(), bean.getSize());
    }

    @POST
    public Response post(ChipRequest chip, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        ChipResponse newChip = service.add(chip);
        String newID = newChip.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(location)
                .entity(newChip)
                .build();
    }

    @GET
    @Path("{id}")
    public ChipResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }
}
