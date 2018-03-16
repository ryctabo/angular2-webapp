package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ClosureData;
import com.nativapps.arpia.model.dto.DebtRequest;
import com.nativapps.arpia.model.dto.DebtResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.rest.bean.DebtBean;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.DebtService;
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
import javax.ws.rs.PUT;
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
@Path("debts")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DebtResource {

    private static final Logger LOG = Logger.getLogger(DebtResource.class.getName());

    @Context
    private HttpServletRequest request;

    private final DebtService service = ServiceFactory.getDebtService();

    @GET
    public ListResponse<DebtResponse> get(@BeanParam DebtBean bean) {
        service.configurate(Collections.list(request.getLocales()));

        if (bean.getSize() == null)
            bean.setSize(50);

        return service.get(bean.getSearch(), bean.getFrom(), bean.getTo(),
                bean.getStart(), bean.getSize(), bean.getOrderBy(),
                bean.getOrderType(), bean.isClosureState());
    }

    @GET
    @Path("{id}")
    public DebtResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public Response post(DebtRequest debt, @Context UriInfo uriInfo,
            @HeaderParam("Authorization") String authToken) {

        service.configurate(Collections.list(request.getLocales()));
        UserInfo userInfo = getUserInfo(authToken);

        DebtResponse newDebt = service.add(debt, userInfo);
        String newID = newDebt.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(location)
                .entity(newDebt)
                .build();
    }

    @PUT
    @Path("{id}")
    public DebtResponse put(@PathParam("id") Long id, DebtRequest debt,
            @HeaderParam("Authorization") String authToken) {

        service.configurate(Collections.list(request.getLocales()));
        UserInfo userInfo = getUserInfo(authToken);
        return service.update(id, debt, userInfo);
    }

    @PUT
    @Path("{id}/closure")
    public DebtResponse closurePut(@PathParam("id") Long id, ClosureData closureData,
            @HeaderParam("Authorization") String authToken) {

        service.configurate(Collections.list(request.getLocales()));
        UserInfo userInfo = getUserInfo(authToken);
        return service.updateDebtClosure(id, closureData, userInfo);
    }

    @Path("{id}/payments")
    public PaymentResource getPaymentResource() {
        return new PaymentResource(request);
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
