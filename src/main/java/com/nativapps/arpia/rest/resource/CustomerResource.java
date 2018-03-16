package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.adapter.DateRange;
import com.nativapps.arpia.model.dto.CustomerDataDto;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.service.CustomerDataService;
import com.nativapps.arpia.service.ServiceFactory;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Path("customers")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class CustomerResource {

    @Context
    private HttpServletRequest request;

    private final CustomerDataService service = ServiceFactory
            .getCustomerDataService();

    @GET
    public ListResponse<CustomerDataDto> getAll(@QueryParam("start") int start,
            @QueryParam("size") Integer size, @QueryParam("search") String search) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null || size <= 0)
            size = 20;
        return service.getAll(start, size, search);
    }

    @GET
    @Path("{id}")
    public CustomerDataDto get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @GET
    @Path("nocom/{date}")
    public ListResponse<CustomerDataDto> getAllByNotCommunication(
            @QueryParam("start") int start, @QueryParam("size") Integer size,
            @PathParam("date") DateRange range) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null || size <= 0)
            size = 20;
        return service.getAllByNotCommunication(start, size, 
                range == null ? null : range.getCalendar());
    }

    @Path("particulars")
    public ParticularResource particulars() {
        return new ParticularResource(request);
    }

    @Path("establishments")
    public EstablishmentResource establishments() {
        return new EstablishmentResource(request);
    }

    @Path("{customerId}/bonus")
    public BonusResource bonus() {
        return new BonusResource(request);
    }

    @Path("{customerId}/creditinfo")
    public CreditInfoResource creditInfo() {
        return new CreditInfoResource(request);
    }

    @Path("{customerId}/banned")
    public CustomerBanResource customerBanHistory() {
        return new CustomerBanResource(request);
    }

    @Path("{customerId}/qcr")
    public QCRResource qcr() {
        return new QCRResource(request);
    }

    @Path("{customerId}/messenger")
    public MessengerFBResource messengerFB() {
        return new MessengerFBResource(request);
    }
}
