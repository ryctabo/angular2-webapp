package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.database.entity.MessengerFB;
import com.nativapps.arpia.model.dto.SimpleMessengerRequest;
import com.nativapps.arpia.model.dto.SimpleMessengerResponse;
import com.nativapps.arpia.service.ServiceFactory;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.nativapps.arpia.service.MessengerFBService;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MessengerFBResource {

    @Context
    private HttpServletRequest request;

    private final MessengerFBService service = ServiceFactory
            .getMessengerBlackListService();

    public MessengerFBResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    @Path("favorites")
    public List<SimpleMessengerResponse> getFavorites(@PathParam("customerId") 
            Long customerId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getAll(customerId, MessengerFB.Type.FAVORITE_LIST);
    }
    
    @GET
    @Path("blacklist")
    public List<SimpleMessengerResponse> getBlacklist(@PathParam("customerId") 
            Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getAll(id, MessengerFB.Type.BLACKLIST);
    }

    @GET
    @Path("favorites/{messengerId}")
    public SimpleMessengerResponse getFavorite(@PathParam("customerId") 
            Long customerId, @PathParam("messengerId") Long messengerId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(customerId, messengerId, MessengerFB.Type.FAVORITE_LIST);
    }
    
    @GET
    @Path("blacklist/{messengerId}")
    public SimpleMessengerResponse getBanned(@PathParam("customerId") 
            Long customerId, @PathParam("messengerId") Long messengerId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(customerId, messengerId, MessengerFB.Type.BLACKLIST);
    }

    @POST
    @Path("favorites")
    public SimpleMessengerResponse postFavorite(@PathParam("customerId") 
            Long customerId, SimpleMessengerRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.add(customerId, data, MessengerFB.Type.FAVORITE_LIST);
    }
    
    @POST
    @Path("blacklist")
    public SimpleMessengerResponse postBlacklist(@PathParam("customerId") 
            Long customerId, SimpleMessengerRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.add(customerId, data, MessengerFB.Type.BLACKLIST);
    }

    @DELETE
    @Path("favorites/{messengerId}")
    public SimpleMessengerResponse deleteFavorite(@PathParam("customerId") 
            Long customerId, @PathParam("messengerId") Long messengerId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(customerId, messengerId, 
                MessengerFB.Type.FAVORITE_LIST);
    }
    
    @DELETE
    @Path("blacklist/{messengerId}")
    public SimpleMessengerResponse deleteBlacklist(@PathParam("customerId") 
            Long customerId, @PathParam("messengerId") Long messengerId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(customerId, messengerId, 
                MessengerFB.Type.BLACKLIST);
    }
}
