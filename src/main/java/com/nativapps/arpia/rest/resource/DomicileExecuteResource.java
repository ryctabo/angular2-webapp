package com.nativapps.arpia.rest.resource;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.nativapps.arpia.model.dto.CancelData;
import com.nativapps.arpia.model.dto.DomicileExecuteRequest;
import com.nativapps.arpia.model.dto.DomicileExecuteResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.TokenUtil;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.rest.bean.DomicileExecuteBean;
import com.nativapps.arpia.rest.exception.TokenException;
import com.nativapps.arpia.rest.exception.UnauthorizedException;
import com.nativapps.arpia.service.DomicileExecuteService;
import com.nativapps.arpia.service.FileService;
import com.nativapps.arpia.service.ServiceFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.StreamingOutput;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.1
 */
@Path("domicilesexe")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DomicileExecuteResource {

    private static final Logger LOG = Logger.getLogger(DomicileExecuteResource.class.getName());

    @Context
    private HttpServletRequest request;

    private final DomicileExecuteService service = ServiceFactory
            .getDomicileExecuteService();
    
    private final FileService fileService = ServiceFactory.getFileService();

    @GET
    public ListResponse<DomicileExecuteResponse> getAll(@BeanParam DomicileExecuteBean bean) {
        service.configurate(Collections.list(request.getLocales()));
        if (bean.getSize() == null)
            bean.setSize(25);

        return service.getAll(bean.getState(), bean.getStartDate(), bean.getEndDate(), bean.getCustomerId(),
                bean.getCustomerGender(), bean.getAuto(), bean.getOperatorId(), bean.getDispatcherId(),
                bean.getOperationId(), bean.getCanceled(), bean.getStart(), bean.getSize(), bean.getOrderBy(),
                bean.getOrderType());
    }

    @GET
    @Path("{id}")
    public DomicileExecuteResponse get(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.get(id);
    }

    @POST
    public Response add(DomicileExecuteRequest domicile, @Context UriInfo uriInfo,
                        @HeaderParam("Authorization") String authHeader) {
        service.configurate(Collections.list(request.getLocales()));

        //get user information
        String token = authHeader.replaceAll("Bearer ", "");
        UserInfo userInfo = null;
        try {
            userInfo = TokenUtil.validateToken(token);
        } catch (TokenException | UnauthorizedException ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }

        DomicileExecuteResponse newDomicile = service.add(domicile, userInfo);
        String newID = newDomicile.getId().toString();
        URI location = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(location)
                .entity(newDomicile)
                .build();
    }

    @PUT
    @Path("{id}/cancel")
    public DomicileExecuteResponse cancel(@PathParam("id") Long id, CancelData cancelData) {
        service.configurate(Collections.list(request.getLocales()));
        return service.cancel(id, cancelData);
    }

    @PUT
    @Path("{id}/finish")
    public DomicileExecuteResponse finish(@PathParam("id") Long id) {
        service.configurate(Collections.list(request.getLocales()));
        return service.finish(id);
    }

    @Path("{id}/assignments")
    public AssignmentResource getAssignmentResource() {
        return new AssignmentResource(request);
    }
    
    @GET
    @Path("{id}/pdf")
    public Response downloadPdf(@PathParam("id") Long id) throws FileNotFoundException, DocumentException {
        final Document document = service.generateOrder(id);
        SimpleDateFormat dateFormat = new SimpleDateFormat(" HHmm dd/MM/yyyy");
        Date date = new Date();
        StreamingOutput streamingOutput = new StreamingOutput() {
            @Override
            public void write(OutputStream out) throws IOException,
                    WebApplicationException {
                String pathname = fileService.getPathName(
                        FileService.FileType.DOCUMENT, "factura.pdf");
                FileInputStream in = new FileInputStream(new File(pathname));
                fileService.write(in, out);
            }
        };
        return Response.ok(streamingOutput)
                .type("application/pdf")
                .header("Content-Disposition", "filename=" + "factura "+dateFormat.format(date)+".pdf")
                .build();
    }

}
