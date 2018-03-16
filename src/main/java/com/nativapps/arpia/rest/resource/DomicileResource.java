package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.rest.bean.DomicileBean;
import com.nativapps.arpia.rest.bean.FrequentBean;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.DomicileExecuteService;
import com.nativapps.arpia.service.DomicileService;
import com.nativapps.arpia.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Path("domiciles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DomicileResource {

    private static final Logger LOG = Logger.getLogger(DomicileResource.class.getName());

    @Context
    private HttpServletRequest request;

    private final DomicileService service = ServiceFactory.getDomicileService();

    @GET
    public ListResponse<DomicileResponse> getAll(@BeanParam DomicileBean bean) {
        service.configurate(Collections.list(request.getLocales()));
        if (bean.getSize() == null)
            bean.setSize(100);
        return service.getAll(bean.getCustomerId(), bean.getOperationId(),
                bean.getDate(), bean.getStart(), bean.getSize(),
                bean.getOrderBy(), bean.getOrderType());
    }

    @GET
    @Path("{domicileId}")
    public DomicileResponse get(@PathParam("domicileId") Long domicileId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(domicileId);
    }

    @POST
    public Response add(DomicileRequest domicile, @Context UriInfo uriInfo,
                        @HeaderParam("Authorization") String authHeader) {
        service.configurate(Collections.list(request.getLocales()));
        DomicileResponse newDomicile = service.add(domicile, getUserInfo(authHeader));
        String newID = newDomicile.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(location)
                .entity(newDomicile)
                .build();
    }

    @DELETE
    @Path("{domicileId}")
    public DomicileResponse delete(@PathParam("domicileId") Long domicileId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(domicileId);
    }

    @POST
    @Path("{domicileId}/execute")
    public Response execute(@PathParam("domicileId") Long id, DomicileExecuteRequest domicile,
                            @Context UriInfo uriInfo, @HeaderParam("Authorization") String authHeader) {
        service.configurate(Collections.list(request.getLocales()));

        //get a service for execute a domicile with the given ID
        DomicileExecuteService dservice = ServiceFactory.getDomicileExecuteService();

        //execute a domicile with the given ID
        domicile.setDomicile(id);
        DomicileExecuteResponse newDomicile = dservice.add(domicile, getUserInfo(authHeader));

        //create location for get domicile execute information
        String newID = newDomicile.getId().toString();
        URI location = uriInfo.getBaseUriBuilder()
                .path(DomicileExecuteResource.class)
                .path(newID)
                .build();

        return Response.created(location)
                .entity(newDomicile)
                .build();
    }

    @GET
    @Path("frequent")
    public List<DomicileFrequent> getDomicilesFrequent(@BeanParam FrequentBean bean) {
        service.configurate(Collections.list(request.getLocales()));
        if (bean.getSize() == null)
            bean.setSize(5);
        return service.getDomicilesFrequent(bean.getCustomerId(),
                bean.getStart(),
                bean.getSize());
    }

    /**
     * Get user information from the given authorization param.
     *
     * @param authHeader authorization header param
     * @return user information
     */
    private UserInfo getUserInfo(String authHeader) {
        String token = authHeader.replaceAll("Bearer ", "");
        UserInfo userInfo = null;
        try {
            userInfo = TokenUtil.validateToken(token);
        } catch (TokenException | UnauthorizedException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return userInfo;
    }

}
