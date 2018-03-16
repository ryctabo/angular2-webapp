package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.SecurityDepositPaymentRequest;
import com.nativapps.arpia.model.dto.SecurityDepositPaymentResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.rest.bean.SecurityDepositPaymentBean;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.SecurityDepositPaymentService;
import com.nativapps.arpia.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class SecurityDepositPaymentResource {

    @Context
    private final HttpServletRequest request;

    private final SecurityDepositPaymentService service = ServiceFactory
            .getSecurityDepositPaymentService();
    private static final Logger LOG = Logger
            .getLogger(SecurityDepositPaymentResource.class.getName());

    public SecurityDepositPaymentResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    @Path("{id}")
    public SecurityDepositPaymentResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @GET
    public ListResponse<SecurityDepositPaymentResponse> getAll(
            @BeanParam SecurityDepositPaymentBean bean) {
        if (bean.getSize() == null) {
            bean.setSize(20);
        }
        service.configurate(Collections.list(request.getLocales()));
        return service.getAll(bean.getSearch(), bean.getStart(),
                bean.getSize(), bean.getFrom(), bean.getTo(), bean.getOrderBy(),
                bean.getOrderType());
    }

    @POST
    public Response post(SecurityDepositPaymentRequest payment,
            @Context UriInfo uriInfo,
            @HeaderParam("Authorization") String authHeader)
            throws UnauthorizedException, TokenException {

        service.configurate(Collections.list(request.getLocales()));
        try {
            String token = authHeader.replace("Bearer ", "");
            UserInfo userInfo = TokenUtil.validateToken(token);

            SecurityDepositPaymentResponse response = service.add(payment,
                    userInfo.getId());
            String newId = response.getId().toString();
            URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
            return Response.created(location)
                    .entity(response)
                    .build();
        } catch (UnauthorizedException | TokenException ex) {
            LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
            throw ex;
        }
    }

}
