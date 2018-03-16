package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ClosureData;
import com.nativapps.arpia.model.dto.EgressData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.rest.bean.EgressBean;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.EgressService;
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
 * @version 1.0.1
 */
@Path("egresses")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class EgressResource {

    private static final Logger LOG = Logger.getLogger(EgressResource.class.getName());

    @Context
    private HttpServletRequest request;

    private final EgressService service = ServiceFactory.getEgressService();

    @GET
    public ListResponse<EgressData> get(@BeanParam EgressBean bean) {
        service.configurate(Collections.list(request.getLocales()));
        if (bean.getSize() == null)
            bean.setSize(50);

        return service.get(bean.getUserId(), bean.getClosured(),
                bean.getType(), bean.getSearch(), bean.getFrom(), bean.getTo(),
                bean.getStart(), bean.getSize(), bean.getOrderBy(),
                bean.getOrderType());
    }

    @GET
    @Path("{id}")
    public EgressData get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public Response post(EgressData egress, @Context UriInfo uriInfo,
                         @HeaderParam("Authorization") String authToken) {
        service.configurate(Collections.list(request.getLocales()));
        UserInfo userInfo = getUserInfo(authToken);
        EgressData newEgress = service.add(egress, userInfo);
        String newID = newEgress.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(location)
                .entity(newEgress)
                .build();
    }

    @PUT
    @Path("{id}")
    public EgressData put(@PathParam("id") Long id, EgressData egress,
                          @HeaderParam("Authorization") String authToken) {
        service.configurate(Collections.list(request.getLocales()));
        UserInfo userInfo = getUserInfo(authToken);
        return service.update(id, egress, userInfo);
    }

    @PUT
    @Path("{id}/closure")
    public EgressData closurePut(@PathParam("id") Long id, ClosureData closureData,
                                 @HeaderParam("Authorization") String authToken) {
        service.configurate(Collections.list(request.getLocales()));
        UserInfo userInfo = getUserInfo(authToken);
        return service.closure(id, closureData, userInfo);
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
