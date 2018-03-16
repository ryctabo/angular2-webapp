package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.SecurityDepositResponse;
import com.nativapps.arpia.service.SecurityDepositService;
import com.nativapps.arpia.service.ServiceFactory;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class SecurityDepositParamResource {

    @Context
    private final HttpServletRequest request;

    private final SecurityDepositService service = ServiceFactory
            .getSecurityDepositService();

    SecurityDepositParamResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    public SecurityDepositResponse get(@PathParam("userId") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @Path("payments")
    public SecurityDepositPaymentResource payments() {
        return new SecurityDepositPaymentResource(request);
    }
}
