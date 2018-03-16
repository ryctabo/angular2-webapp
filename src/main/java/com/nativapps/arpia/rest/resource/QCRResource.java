package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.QCRRequest;
import com.nativapps.arpia.model.dto.QCRResponse;
import com.nativapps.arpia.rest.bean.QCRBean;
import com.nativapps.arpia.service.QCRService;
import com.nativapps.arpia.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collections;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
@Path("qcr")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class QCRResource {

    @QueryParam("customer")
    private Long customerId;

    @Context
    private HttpServletRequest request;

    private final QCRService service = ServiceFactory.getQCRService();

    public QCRResource() { }

    public QCRResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    public ListResponse<QCRResponse> getAll(@BeanParam QCRBean bean,
                                            @PathParam("customerId") Long customerId) {
        service.configurate(Collections.list(request.getLocales()));
        if (bean.getSize() == null)
            bean.setSize(25);
        if (customerId != null)
            this.customerId = customerId;

        return service.getAll(bean.getStart(), bean.getSize(), this.customerId, 
                bean.getMessengerId(), bean.getFrom(), bean.getTo(), bean.getStatus());
    }

    @GET
    @Path("{id}")
    public QCRResponse get(@PathParam("customerId") Long customerId,
                           @PathParam("id") Integer index) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(customerId == null ? this.customerId : customerId, index);
    }

    @POST
    public QCRResponse step1(@PathParam("customerId") Long customerId, QCRRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        if (customerId != null)
            data.setCustomer(customerId);
        return service.add(data);
    }

    @PUT
    @Path("{id}/counterpart")
    public QCRResponse step2(@PathParam("customerId") Long customerId,
                             @PathParam("id") Integer index, QCRRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        if (customerId != null)
            this.customerId = customerId;
        return service.counterpart(this.customerId, index, data);
    }

    @PUT
    @Path("{id}/solution")
    public QCRResponse step3(@PathParam("customerId") Long customerId,
                             @PathParam("id") Integer index, QCRRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        if (customerId != null)
            this.customerId = customerId;
        return service.solution(this.customerId, index, data);
    }

    @PUT
    @Path("{id}/cancel")
    public QCRResponse cancel(@PathParam("customerId") Long customerId,
                              @PathParam("id") Integer index) {
        service.configurate(Collections.list(request.getLocales()));
        if (customerId != null)
            this.customerId = customerId;
        return service.cancel(this.customerId, index);
    }
}
