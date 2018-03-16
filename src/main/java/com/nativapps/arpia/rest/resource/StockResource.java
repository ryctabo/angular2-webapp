package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.StockRequest;
import com.nativapps.arpia.model.dto.StockResponse;
import com.nativapps.arpia.rest.bean.StockBean;
import com.nativapps.arpia.service.ServiceFactory;
import com.nativapps.arpia.service.StockService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class StockResource {

    private HttpServletRequest request;

    private final StockService service = ServiceFactory.getStockService();

    public StockResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    public ListResponse<StockResponse> get(@PathParam("id") Long inventoryId,
                                           @BeanParam StockBean bean) {
        service.configurate(Collections.list(request.getLocales()));
        if (bean.getSize() == null)
            bean.setSize(20);
        return service.get(inventoryId, bean.getType(), bean.getStartDate(),
                bean.getEndDate(), bean.getStart(), bean.getSize());
    }

    @GET
    @Path("{index}")
    public StockResponse get(@PathParam("id") Long inventoryId,
                             @PathParam("index") Integer index) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(inventoryId, index);
    }

    @POST
    public StockResponse addNewStock(@PathParam("id") Long inventoryId, StockRequest stock) {
        service.configurate(Collections.list(request.getLocales()));
        return service.add(inventoryId, stock);
    }

}
