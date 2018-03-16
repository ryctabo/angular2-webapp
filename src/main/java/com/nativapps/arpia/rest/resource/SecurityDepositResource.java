package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.SecurityDepositResponse;
import com.nativapps.arpia.rest.bean.GenericFilterBean;
import com.nativapps.arpia.service.SecurityDepositService;
import com.nativapps.arpia.service.ServiceFactory;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Path("deposits")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class SecurityDepositResource {

    @Context
    private HttpServletRequest request;

    private final SecurityDepositService service = ServiceFactory
            .getSecurityDepositService();

    @GET
    public List<SecurityDepositResponse> getAll(
            @BeanParam GenericFilterBean bean) {
        if (bean.getSize() == null) {
            bean.setSize(20);
        }
        service.configurate(Collections.list(request.getLocales()));
        return service.getAll(bean.getSearch(), bean.getStart(),
                bean.getSize(), bean.getOrderBy(), bean.getOrderType());
    }

    @Path("payments")
    public SecurityDepositPaymentResource payments() {
        return new SecurityDepositPaymentResource(request);
    }
}
