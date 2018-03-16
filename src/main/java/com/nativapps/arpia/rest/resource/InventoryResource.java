package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.rest.bean.GenericFilterBean;
import com.nativapps.arpia.service.InventoryService;
import com.nativapps.arpia.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.2.0
 */
@Path("inventory")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class InventoryResource {

    @Context
    private HttpServletRequest request;

    private final InventoryService service = ServiceFactory.getInventoryService();

    @GET
    public ListResponse<InventoryResponse> get(@BeanParam GenericFilterBean params) {
        service.configurate(Collections.list(request.getLocales()));
        if (params.getSize() == null)
            params.setSize(50);
        return service.getAll(params.getSearch(), params.getOrderBy(),
                params.getOrderType(), params.getStart(), params.getSize());
    }

    @POST
    public Response post(InventoryRequest inventory, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        InventoryResponse newInventoryItem = service.add(inventory);
        String newId = newInventoryItem.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(location)
                .entity(newInventoryItem)
                .build();
    }

    @GET
    @Path("{id}")
    public InventoryResponse get(@PathParam("id") Long inventoryId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(inventoryId);
    }

    @PUT
    @Path("{id}")
    public InventoryResponse put(@PathParam("id") Long inventoryId,
                                 InventoryRequest inventory) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(inventoryId, inventory);
    }

    @DELETE
    @Path("{id}")
    public InventoryResponse delete(@PathParam("id") Long inventoryId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(inventoryId);
    }

    @GET
    @Path("{id}/messengers")
    public List<MessengerInfoResponse> getMessengers(@PathParam("id") Long inventoryId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getMessengers(inventoryId);
    }

    @GET
    @Path("{id}/chipInfo")
    public ChipInfoResponse getChipInfo(@PathParam("id") Long inventoryId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getChipInfo(inventoryId);
    }

    @Path("{id}/stocks")
    public StockResource getStockResource() {
        return new StockResource(request);
    }

}
