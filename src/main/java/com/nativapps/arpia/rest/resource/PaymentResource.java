package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.PaymentData;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.rest.bean.PaymentBean;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.PaymentService;
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
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PaymentResource {

    private static final Logger LOG = Logger.getLogger(PaymentResource.class.getName());

    private final HttpServletRequest request;

    private final PaymentService service = ServiceFactory.getPaymentService();

    public PaymentResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    public ListResponse<PaymentData> get(@PathParam("id") Long debtId,
            @BeanParam PaymentBean bean) {
        service.configurate(Collections.list(request.getLocales()));

        if (bean.getSize() == null)
            bean.setSize(50);

        return service.get(debtId, bean.getFrom(), bean.getTo(),
                bean.getStart(), bean.getSize(), bean.getOrderBy(),
                bean.getOrderType());
    }

    @GET
    @Path("{index}")
    public PaymentData getChange(@PathParam("id") Long id,
            @PathParam("index") Integer index) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id, index);
    }

    @POST
    public Response post(@PathParam("id") Long debtId,
            PaymentData payment, @Context UriInfo uriInfo,
            @HeaderParam("Authorization") String authToken) {

        service.configurate(Collections.list(request.getLocales()));
        UserInfo userInfo = getUserInfo(authToken);

        PaymentData newPayment = service.addPayment(debtId, payment, userInfo);
        String newID = newPayment.getIndex().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(location)
                .entity(newPayment)
                .build();
    }

    /**
     * Get user information from the auth token provided to header param.
     *
     * @param authToken authorization token
     * @return user information
     */
    private UserInfo getUserInfo(String authToken) {
        try {
            String token = authToken.replaceAll("Bearer ", "");
            return TokenUtil.validateToken(token);
        } catch (TokenException | UnauthorizedException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

}
