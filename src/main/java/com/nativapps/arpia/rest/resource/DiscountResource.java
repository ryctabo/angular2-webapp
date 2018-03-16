package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.DiscountRequest;
import com.nativapps.arpia.model.dto.DiscountResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.rest.bean.DiscountBean;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.DiscountService;
import com.nativapps.arpia.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1
 */
@Path("discounts")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DiscountResource {

    private static final Logger LOG = Logger.getLogger(MonitoringResource.class.getName());

    @Context
    private HttpServletRequest request;

    private final DiscountService service = ServiceFactory.getDiscountService();

    @GET
    public ListResponse<DiscountResponse> get(@BeanParam DiscountBean bean) {
        service.configurate(Collections.list(request.getLocales()));
        if (bean.getSize() == null)
            bean.setSize(25);

        return service.get(bean.getSearch(), bean.getActive(), bean.getStart(),
                bean.getSize(), bean.getOrderBy(), bean.getOrderType());
    }

    @GET
    @Path("{id}")
    public DiscountResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public Response post(DiscountRequest discount, @Context UriInfo uriInfo,
                         @HeaderParam("Authorization") String authToken) {
        service.configurate(Collections.list(request.getLocales()));
        UserInfo userInfo = getUserInfo(authToken);

        DiscountResponse newDiscount = service.add(discount, userInfo);
        String newID = newDiscount.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(location)
                .entity(newDiscount)
                .build();
    }

    @PUT
    @Path("{id}")
    public DiscountResponse put(@PathParam("id") Long id, DiscountRequest discount,
                                @HeaderParam("Authorization") String authToken) {

        service.configurate(Collections.list(request.getLocales()));
        UserInfo userInfo = getUserInfo(authToken);
        return service.update(id, discount, userInfo);
    }

    @DELETE
    @Path("{id}")
    public DiscountResponse delete(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(id);
    }

    @Path("{id}/changes")
    public DiscountChangeLogResource getDiscountChangeLogResource() {
        return new DiscountChangeLogResource(request);
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
