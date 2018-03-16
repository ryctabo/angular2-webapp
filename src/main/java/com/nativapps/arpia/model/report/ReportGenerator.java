package com.nativapps.arpia.model.report;

import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.InternalServerErrorException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class ReportGenerator {

    private static final Logger LOG = Logger.getLogger(ReportGenerator.class.getName());
    
    private final String PATH = "com/nativapps/arpia/report/";

    public void generate(String reportName, List list, OutputStream out) {
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass()
                    .getClassLoader().getResourceAsStream(PATH + reportName+".jrxml"));
            JasperPrint print = JasperFillManager.fillReport(report, null,
                    new JRBeanCollectionDataSource(list));
            JasperExportManager.exportReportToPdfStream(print, out);
        } catch (JRException ex) {
            LOG.log(Level.SEVERE, "Error during generation process", ex);
            throw new InternalServerErrorException("Error during generation process: "+ex.getMessage());
        }
    }
}
