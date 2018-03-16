package com.nativapps.arpia.rest.resource;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.ParticularResponse;
import com.nativapps.arpia.rest.bean.BirthdayBean;
import com.nativapps.arpia.service.BirthdayService;
import com.nativapps.arpia.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collections;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0.1
 */
@Path("birthdays")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class BirthdayResource {

    @Context
    private HttpServletRequest request;

    private final BirthdayService service = ServiceFactory.getBirthdayService();

    @GET
    public ListResponse<ParticularResponse> getAll(@BeanParam BirthdayBean bean) {
        service.configurate(Collections.list(request.getLocales()));
        if (bean.getSize() == null)
            bean.setSize(25);

        if (bean.getTo() == null)
            throw new BadRequestException("The date is required.");

        return service.getAll(bean.getStart(), bean.getSize(), bean.getTo());
    }
}
