package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.service.GoodStandingRService;
import com.nativapps.arpia.service.ServiceFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class GSReportResource {

    @Context
    private HttpServletRequest request;
    
    private final GoodStandingRService service = ServiceFactory.getGoodStandingRService();

    public GSReportResource(HttpServletRequest request) {
        this.request = request;
    }
    
    @GET
    public Response get(@PathParam("id") final Long id) {
        service.configurate(Collections.list(request.getLocales()));
        StreamingOutput streamingOutput = new StreamingOutput() {
            @Override
            public void write(OutputStream out) throws IOException,
                    WebApplicationException {
                service.get(id, out);
            }
        };
        return Response.ok(streamingOutput)
                .type("application/pdf")
                .header("Content-Disposition", "filename=reporte.pdf")
                .build();
    }
}
