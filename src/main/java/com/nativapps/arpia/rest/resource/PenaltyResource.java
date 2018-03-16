package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.PenaltyRequest;
import com.nativapps.arpia.model.dto.PenaltyResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.rest.bean.PenaltyBean;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.PenaltyService;
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
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Path("penalties")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PenaltyResource {

    private static final Logger LOG = Logger.getLogger(PenaltyResource.class.getName());

    @Context
    private HttpServletRequest request;

    private final PenaltyService service = ServiceFactory.getPenaltyService();

    @GET
    public ListResponse<PenaltyResponse> getAll(@BeanParam PenaltyBean bean) {
        service.configurate(Collections.list(request.getLocales()));
        if (bean.getSize() == null)
            bean.setSize(20);
        return service.getAll(bean.getMessenger(), bean.getStart(), bean.getSize());
    }

    @GET
    @Path("{id}")
    public PenaltyResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public Response post(PenaltyRequest data, @Context UriInfo uriInfo,
            @HeaderParam("Authorization") String authHeader) throws UnauthorizedException, TokenException {
        try {
            String token = authHeader.replace("Bearer ", "");
            UserInfo userInfo = TokenUtil.validateToken(token);

            service.configurate(Collections.list(request.getLocales()));
            PenaltyResponse penalty = service.add(data, userInfo.getId());
            String newId = penalty.getId().toString();
            URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
            return Response.created(location)
                    .entity(penalty)
                    .build();
        } catch (UnauthorizedException | TokenException ex) {
            LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
            throw ex;
        }
    }

    @PUT
    @Path("{id}")
    public PenaltyResponse put(@PathParam("id") Long messengerId,
            PenaltyRequest data, @HeaderParam("Authorization") String authHeader) throws UnauthorizedException, TokenException {
        String token = authHeader.replace("Bearer ", "");
        UserInfo userInfo = TokenUtil.validateToken(token);

        service.configurate(Collections.list(request.getLocales()));
        return service.update(messengerId, data, userInfo.getId());
    }

    @DELETE
    @Path("{id}")
    public PenaltyResponse delete(@PathParam("id") Long id) {
        return service.delete(id);
    }

}
