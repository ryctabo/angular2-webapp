package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.MobileNumberRequest;
import com.nativapps.arpia.model.dto.MobileNumberResponse;
import com.nativapps.arpia.rest.bean.MobileNumberBean;
import com.nativapps.arpia.service.MobileNumberService;
import com.nativapps.arpia.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 2.0
 */
@Path("mobiles")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MobileNumberResource {

    @Context
    private HttpServletRequest request;

    private final MobileNumberService service = ServiceFactory.getMobileNumberService();

    public MobileNumberResource() {
    }

    public MobileNumberResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    public List<MobileNumberResponse> getAll(@BeanParam MobileNumberBean bean) {
        service.configurate(Collections.list(request.getLocales()));
        if (bean.getSize() == null)
            bean.setSize(100);
        return service.getAll(bean.getOperation(), bean.getAvailable(),
                bean.getStart(), bean.getSize(), bean.getOrderBy(),
                bean.getOrderType());
    }

    @POST
    public Response add(@PathParam("operationId") Long operationId,
            MobileNumberRequest data, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));

        //verify if operationId is null or less than 1
        if (data.getOperationId() == null || data.getOperationId() <= 0)
            data.setOperationId(operationId);

        service.add(data);
        URI location = uriInfo.getAbsolutePathBuilder().build();
        return Response.created(location).build();
    }

    @PUT
    @Path("{index}")
    public MobileNumberResponse update(@PathParam("operationId") Long operationId,
            @PathParam("index") Integer index, MobileNumberRequest data) {
        service.configurate(Collections.list(request.getLocales()));

        //verify if operationId is null or less than 1
        if (data.getOperationId() == null || data.getOperationId() <= 0)
            data.setOperationId(operationId);

        return service.update(index, data);
    }

}
