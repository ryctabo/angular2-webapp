package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MonitoringRequest;
import com.nativapps.arpia.model.dto.MonitoringResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.rest.bean.MonitoringBean;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.MonitoringService;
import com.nativapps.arpia.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Path("monitorings")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MonitoringResource {

    private static final Logger LOG = Logger
            .getLogger(MonitoringResource.class.getName());

    @Context
    private HttpServletRequest request;

    private final MonitoringService service = ServiceFactory.getMonitoringService();

    @GET
    public ListResponse<MonitoringResponse> get(@BeanParam MonitoringBean bean) {
        service.configurate(Collections.list(request.getLocales()));

        if (bean.getSize() == null)
            bean.setSize(50);

        return service.get(bean.getStartDate(), bean.getEndDate(),
                bean.getDomicileId(), bean.getStart(), bean.getSize(),
                bean.getOrderBy(), bean.getOrderType());
    }

    @GET
    @Path("{id}")
    public MonitoringResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public Response post(MonitoringRequest monitoring, @Context UriInfo uriInfo,
            @HeaderParam("Authorization") String authToken) {
        service.configurate(Collections.list(request.getLocales()));
        UserInfo userInfo = getUserInfo(authToken);

        MonitoringResponse newMonitoring = service.add(monitoring, userInfo);
        String newID = newMonitoring.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(location)
                .entity(newMonitoring)
                .build();
    }

    @PUT
    @Path("{id}")
    public MonitoringResponse put(@PathParam("id") Long id, MonitoringRequest monitoring,
            @HeaderParam("Authorization") String authToken) {
        service.configurate(Collections.list(request.getLocales()));
        UserInfo userInfo = getUserInfo(authToken);
        return service.update(id, monitoring, userInfo);
    }

    @DELETE
    @Path("{id}")
    public MonitoringResponse delete(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(id);
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
