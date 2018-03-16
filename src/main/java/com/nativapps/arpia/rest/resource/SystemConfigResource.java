package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.SystemConfig;
import com.nativapps.arpia.service.ServiceFactory;
import com.nativapps.arpia.service.SystemConfigService;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Path("settings")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class SystemConfigResource {

    @Context
    private HttpServletRequest request;
    
    private final SystemConfigService service = ServiceFactory
            .getSystemConfigService();
    
    @GET
    public SystemConfig get() {
        service.configurate(Collections.list(request.getLocales()));
        return service.get();
    }
    
    @PUT
    public Response update(SystemConfig data) {
        service.configurate(Collections.list(request.getLocales()));
        service.update(data);
        return Response.ok().build();
    }
    
}
