package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.GoodStandingDao;
import com.nativapps.arpia.database.entity.GoodStanding;
import com.nativapps.arpia.model.report.GoodStandingReport;
import com.nativapps.arpia.model.report.ReportGenerator;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class GoodStandingRServiceImpl extends GenericService 
        implements GoodStandingRService {

    private final ReportGenerator generator = new ReportGenerator();
    
    private final GoodStandingDao gsDao;

    public GoodStandingRServiceImpl(GoodStandingDao gsDao) {
        this.gsDao = gsDao;
    }
    
    @Override
    public void get(Long gsId, OutputStream out) {
        if (out == null)
            throw new InternalServerErrorException(config.getString("gs_report.missing_stream"));
        GoodStanding entity = getEntity(gsId);
        
        if (entity.getApproved() != null && entity.getApproved()) {
            List<GoodStandingReport> data = new ArrayList<>();
            DateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("es","CO"));
            
            data.add(new GoodStandingReport(entity.getMessenger().getName(), 
                    entity.getMessenger().getLastName(), entity.getMessenger().getIdentification(), 
                    entity.getOperation().getName(), formatter.format(Calendar
                            .getInstance().getTime())));
            generator.generate("gs_report", data, out);
        } else
            throw new BadRequestException(config.getString("gs_report.not_approved"));
    }
    
    private GoodStanding getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("good_standing.id_required"));
        
        GoodStanding entity = gsDao.find(id);
        if (entity == null) {
            String err = String.format(config.getString("good_standing.not_found"), id);
            throw new NotFoundException(err);
        }
        
        return entity;
    }
}
