package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.InventoryInfoResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MessengerRequest;
import com.nativapps.arpia.model.dto.MessengerResponse;
import com.nativapps.arpia.rest.bean.MessengerBean;
import com.nativapps.arpia.service.MessengerService;
import com.nativapps.arpia.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
@Path("messengers")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MessengerResource {

    @Context
    private HttpServletRequest request;

    MessengerService service = ServiceFactory.getMessengerService();

    @GET
    public ListResponse<MessengerResponse> getAll(@BeanParam MessengerBean bean) {
        service.configurate(Collections.list(request.getLocales()));
        if (bean.getSize() == null)
            bean.setSize(20);
        return service.getAll(bean.getDismissal(), bean.getCategory(),
                bean.getSearch(), bean.getStart(), bean.getSize(),
                bean.getOrderBy(), bean.getOrderType(), bean.getRetire());
    }

    @GET
    @Path("{messengerId}")
    public MessengerResponse get(@PathParam("messengerId") Long messengerId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(messengerId);
    }

    @GET
    @Path("{messengerId}/inventory")
    public List<InventoryInfoResponse> getInventoryInfo(@PathParam("messengerId") Long messengerId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getInventoryInfo(messengerId);
    }

    @POST
    public Response post(MessengerRequest data, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        MessengerResponse messenger = service.add(data);
        String newId = messenger.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(location)
                .entity(messenger)
                .build();
    }

    @PUT
    @Path("{messengerId}")
    public MessengerResponse put(@PathParam("messengerId") Long messengerId,
            MessengerRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(messengerId, data);
    }
    
    @PUT
    @Path("{id}/retire")
    public MessengerResponse retire(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.retire(id);
    }
    
    @PUT
    @Path("{id}/comeback")
    public MessengerResponse comeback(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.comeback(id);
    }

    @Path("{ownerId}/addresses")
    public AddressResource addresses() {
        return new AddressResource(request);
    }

    @Path("{ownerId}/emails")
    public EmailResource emails() {
        return new EmailResource(request);
    }

    @Path("{ownerId}/phones")
    public PhoneResource phones() {
        return new PhoneResource(request);
    }

    @Path("{messengerId}/faults")
    public FaultResource faults() {
        return new FaultResource(request);
    }

    @Path("{messengerId}/vehicles")
    public VehicleResource vehicles() {
        return new VehicleResource(request);
    }

    @Path("{messengerId}/reliabilities")
    public ReliabilityResource reliabilities() {
        return new ReliabilityResource(request);
    }

    @Path("{messengerId}/actions")
    public MessengerActionResource actions() {
        return new MessengerActionResource(request);
    }

    @Path("{messengerId}/prohibitions")
    public ProhibitionResource prohibitions() {
        return new ProhibitionResource(request);
    }

    @Path("{messengerId}/restrictions")
    public RestrictionResource restrictions() {
        return new RestrictionResource(request);
    }
}
