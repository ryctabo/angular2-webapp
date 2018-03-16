package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.BonusRequest;
import com.nativapps.arpia.model.dto.BonusResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.BonusService;
import com.nativapps.arpia.service.ServiceFactory;
import java.net.URI;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class BonusResource {

    @Context
    private final HttpServletRequest request;

    private final BonusService service = ServiceFactory.getBonusService();

    public BonusResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    public ListResponse<BonusResponse> getAll(@QueryParam("start") int start,
            @QueryParam("size") Integer size,
            @PathParam("customerId") Long customerId) {
        service.configurate(Collections.list(request.getLocales()));
        if (size == null || size <= 0)
            size = 20;
        return service.getAll(start, size, customerId);
    }

    @GET
    @Path("bonusId")
    public BonusResponse get(@PathParam("customerId") Long customerId,
            @PathParam("bonusId") Long bonusId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(customerId, bonusId);
    }

    @POST
    public Response post(@PathParam("customerId") Long customerId,
            @HeaderParam("Authorization") String authHeader, BonusRequest bonus,
            @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        try {
            String authToken = authHeader.replaceAll("Bearer ", "");
            BonusResponse response = service.add(customerId, TokenUtil
                    .validateToken(authToken).getId(), bonus);
            URI uri = uriInfo.getAbsolutePathBuilder().path(response.getId()
                    .toString()).build();
            return Response.created(uri).entity(response).build();
        } catch (TokenException ex) {
            throw new WebApplicationException(ex.getMessage(), ex);
        } catch (UnauthorizedException ex) {
            throw new WebApplicationException(ex.getMessage(),
                    Response.Status.UNAUTHORIZED);
        }
    }
}
