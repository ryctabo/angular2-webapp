package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.VehicleRequest;
import com.nativapps.arpia.model.dto.VehicleResponse;
import com.nativapps.arpia.service.ServiceFactory;
import com.nativapps.arpia.service.VehicleService;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 1.0.1
 */
@Path("vehicles")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class VehicleResource {

    @QueryParam("messengerId")
    private Long messengerId;

    @Context
    private HttpServletRequest request;

    private final VehicleService service = ServiceFactory.getVehicleService();

    public VehicleResource() {
    }

    public VehicleResource(HttpServletRequest request) {
        this.request = request;
    }

    @GET
    public List<VehicleResponse> getAll(@PathParam("messengerId") Long messengerId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.getAll(messengerId == null ? this.messengerId : messengerId);
    }

    @GET
    @Path("{vehicleId}")
    public VehicleResponse get(@PathParam("vehicleId") Long vehicleId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(vehicleId);
    }

    @POST
    public Response post(@PathParam("messengerId") Long messengerId,
            VehicleRequest vehicle, @Context UriInfo uriInfo) {
        service.configurate(Collections.list(request.getLocales()));
        Long mId = messengerId == null ? this.messengerId : messengerId;
        VehicleResponse newVehicle = service.add(mId, vehicle);
        String newId = newVehicle.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return Response.created(location)
                .entity(newVehicle)
                .build();
    }

    @PUT
    @Path("{vehicleId}")
    public VehicleResponse update(@PathParam("vehicleId") Long vehicleId,
            VehicleRequest data) {
        service.configurate(Collections.list(request.getLocales()));
        return service.update(vehicleId, data);
    }

    @DELETE
    @Path("{vehicleId}")
    public VehicleResponse delete(@PathParam("vehicleId") Long vehicleId) {
        service.configurate(Collections.list(request.getLocales()));
        return service.delete(vehicleId);
    }
}
