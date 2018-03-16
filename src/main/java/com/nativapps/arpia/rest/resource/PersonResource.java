package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.PersonResponse;
import com.nativapps.arpia.service.PersonService;
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
@Path("people")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PersonResource {

    @Context
    private HttpServletRequest request;
    
    private final PersonService service = ServiceFactory.getPersonService();
    
    @GET
    public ListResponse<PersonResponse> getAll(@QueryParam("start") int start,
            @QueryParam("size") Integer size, @QueryParam("search") String search) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null)
            size = 20;
        
        return service.getAll(start, size, search);
    }
    
    @GET
    @Path("{id}")
    public PersonResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }
}
