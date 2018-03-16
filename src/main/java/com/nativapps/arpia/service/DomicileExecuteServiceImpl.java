package com.nativapps.arpia.service;

import com.lowagie.text.Document;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nativapps.arpia.database.dao.DomicileDao;
import com.nativapps.arpia.database.dao.DomicileExecuteDao;
import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.database.dao.ParticularDao;
import com.nativapps.arpia.database.entity.*;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.service.converter.DomicileExecuteSimpleConverter;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.TextUtil;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.3.1
 */
public class DomicileExecuteServiceImpl extends GenericService implements DomicileExecuteService,
        DtoConverter<DomicileExecute, DomicileExecuteRequest, DomicileExecuteResponse> {

    private final DomicileExecuteDao deController;

    private final ParticularDao paController;

    public DomicileExecuteServiceImpl(DomicileExecuteDao dController,
                                      ParticularDao pController) {
        this.deController = dController;
        this.paController = pController;
    }

    /**
     * Get domicile entity from the given ID.
     *
     * @param id the domicile ID
     * @return the domicile data
     * @throws BadRequestException if the domicile Id is null or less than or equal to 0.
     * @throws NotFoundException   if the domicile data obtained is null.
     */
    private DomicileExecute getEntity(Long id) throws NotFoundException, BadRequestException {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("dexecute.id"));
        DomicileExecute domicile = deController.find(id);
        if (domicile == null) {
            final String FORMAT = config.getString("dexecute.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return domicile;
    }

    /**
     * Validate if domicile have all attributes for save to database.
     *
     * @param domicile domicile to evaluate
     * @param userInfo user information
     * @throws InternalServerErrorException if the user information is null
     * @throws BadRequestException          if the domicile data is null
     * @throws ServiceException             if the domicile data don't have any attribute required
     */
    private void validateDomicileExecute(DomicileExecuteRequest domicile, UserInfo userInfo)
            throws BadRequestException, ServiceException {
        if (userInfo == null)
            throw new InternalServerErrorException("user.info");

        if (domicile == null) {
            throw new BadRequestException(config.getString("dexecute.is_null"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);

            Long id = domicile.getDomicile();
            if (id == null || id <= 0)
                emd.addMessage(config.getString("domicile.id"));

            DomicileDao dController = EntityControllerFactory.getDomicileController();
            Domicile d = dController.find(id);
            if (d == null) {
                final String FORMAT = config.getString("domicile.not_found");
                emd.addMessage(String.format(FORMAT, id));
            }

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }
    }

    @Override
    public ListResponse<DomicileExecuteResponse> getAll(DomicileStatus state, Calendar startDate, Calendar endDate,
                                                        Long customerId, Gender customerGender, Boolean auto,
                                                        Long operatorId, Long dispatcherId, Long operationId,
                                                        Boolean canceled, int start, int size, String orderBy,
                                                        OrderType orderType) {
        List<DomicileExecuteResponse> domiciles = new ArrayList<>();
        List<DomicileExecute> result = deController.findAll(state, startDate, endDate, customerId, customerGender, auto,
                operatorId, dispatcherId, operationId, canceled, start, size, orderBy, orderType);

        for (DomicileExecute domicile : result)
            domiciles.add(convertToDto(domicile));

        long total = deController.count(state, startDate, endDate, customerId, customerGender,
                auto, operatorId, dispatcherId, operationId, canceled);
        return new ListResponse<>(total, domiciles);
    }

    @Override
    public DomicileExecuteResponse get(Long id) {
        return this.convertToDto(getEntity(id));
    }

    @Override
    public DomicileExecuteResponse add(DomicileExecuteRequest request, UserInfo userInfo) {
        this.validateDomicileExecute(request, userInfo);

        DomicileExecute domicileExecute = this.convertToEntity(request);
        domicileExecute.setUser(new User(userInfo.getId()));
        domicileExecute.setStatus(DomicileStatus.WAITING);

        return convertToDto(deController.save(domicileExecute));
    }

    @Override
    public DomicileExecuteResponse cancel(Long id, CancelData cancelReason) {
        DomicileExecute domicile = getEntity(id);

        if (domicile.getStatus() == DomicileStatus.FINALIZED)
            throw new BadRequestException(config.getString("dexecute.finished"));
        if (domicile.getStatus() == DomicileStatus.CANCELLED)
            throw new BadRequestException(config.getString("dexecute.cancelled"));

        domicile.setCancelDate(Calendar.getInstance());

        if (cancelReason == null || TextUtil.isEmpty(cancelReason.getMessage()))
            throw new BadRequestException(config.getString("dexecute.cancel"));
        domicile.setCancelReason(cancelReason.getMessage());
        domicile.setStatus(DomicileStatus.CANCELLED);

        return convertToDto(deController.save(domicile));
    }

    @Override
    public DomicileExecuteResponse finish(Long id) {
        DomicileExecute domicile = getEntity(id);

        if (domicile.getStatus() == DomicileStatus.CANCELLED)
            throw new BadRequestException(config.getString("dexecute.cancelled"));
        if (domicile.getStatus() == DomicileStatus.FINALIZED)
            throw new BadRequestException(config.getString("dexecute.finished2"));
        if (domicile.getStatus() == DomicileStatus.WAITING)
            throw new BadRequestException(config.getString("dexecute.waiting"));

        domicile.setFinishDate(Calendar.getInstance());
        domicile.setStatus(DomicileStatus.FINALIZED);

        return convertToDto(deController.save(domicile));
    }

    @Override
    public DomicileExecute convertToEntity(DomicileExecuteRequest request) {
        DomicileExecute execute = new DomicileExecute();

        execute.setDomicile(new Domicile(request.getDomicile()));

        Boolean automatic = request.getAutomatic();
        execute.setAutomatic(automatic == null ? false : automatic);

        Long discountId = request.getDiscount();
        execute.setDiscount(discountId == null ? null : new Discount(discountId));

        return execute;
    }

    @Override
    public DomicileExecuteResponse convertToDto(DomicileExecute entity) {
        return DomicileExecuteSimpleConverter.instance().convertToDto(entity);
    }

    @Override
    public Document generateOrder(Long id) throws FileNotFoundException, DocumentException {

        DomicileExecute de = getEntity(id);

        if (de.getStatus() != DomicileStatus.DISPATCHED)
            throw new BadRequestException(config.getString("dexecute.dispatched"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Date date = new Date();

        // Se crea el documento
        Rectangle rectangle = new Rectangle(288, 720);
        Document document = new Document(rectangle, 0, 0, 5, 0);

        // Se crea el OutputStream para el fichero donde queremos dejar el pdf.
        File dir = new File("documents");
        if (!dir.exists())
            dir.mkdirs();
        FileOutputStream ficheroPdf = new FileOutputStream("documents/factura.pdf");

        // Se asocia el documento al OutputStream y se indica que el espaciado entre
        // lineas sera de 20. Esta llamada debe hacerse antes de abrir el document
        PdfWriter.getInstance(document, ficheroPdf).setInitialLeading(20);

        // Se abre el document.
        document.open();

        PdfPTable table = new PdfPTable(2);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidthPercentage(95);

        PdfPCell cell;

        cell = createCell(null, "OR#" + de.getId(), Element.ALIGN_LEFT,
                null);
        table.addCell(cell);

        cell = createCell(null, dateFormat.format(date), Element.ALIGN_RIGHT,
                null);
        table.addCell(cell);

        cell = createCell(null, "*************************************",
                Element.ALIGN_CENTER, 2);
        table.addCell(cell);

        for (Location location : de.getDomicile().getLocations()) {
            cell = createCell(null, "*DIRECCION*: " + location.getAddress(),
                    Element.ALIGN_LEFT, 2);
            table.addCell(cell);

            if (location.getReference() != null) {
                cell = createCell(null, "*REF*: " + location.getReference(),
                        Element.ALIGN_LEFT, 2);
                table.addCell(cell);
            }

            cell = createCell(null, "*TAREA*: " + location.getTask(),
                    Element.ALIGN_LEFT, 2);
            table.addCell(cell);

            cell = createCell(null, "-------------------------------------",
                    Element.ALIGN_CENTER, 2);
            table.addCell(cell);
        }

        cell = createCell(null, "Valor:", Element.ALIGN_LEFT, null);
        table.addCell(cell);

        float price = de.getDomicile().getPrice();
        cell = createCell(null, String.valueOf(price), Element.ALIGN_RIGHT, null);
        table.addCell(cell);

        List<Assignment> assignments = new ArrayList<>(de.getAssignments());
        Assignment lastAssignment = assignments.get(assignments.size() - 1);

        cell = createCell(null, "ALIADO:", Element.ALIGN_LEFT, null);
        table.addCell(cell);

        cell = createCell(null, String.valueOf(lastAssignment.getMessengerId()),
                Element.ALIGN_RIGHT, null);
        table.addCell(cell);

        if (de.getDiscount() != null) {
            float percent = de.getDiscount().getPercent();
            float priceWithDiscount = price * percent;

            cell = createCell(null, "DESCUENTO:", Element.ALIGN_LEFT, null);
            table.addCell(cell);

            cell = createCell(null, String.valueOf(priceWithDiscount),
                    Element.ALIGN_RIGHT, null);
            table.addCell(cell);
        }

        cell = createCell(null, "COD. CLIENTE:", Element.ALIGN_LEFT, null);
        table.addCell(cell);

        cell = createCell(null, String.valueOf(de.getDomicile().getCustomerId()),
                Element.ALIGN_RIGHT, null);
        table.addCell(cell);

        cell = createCell(null, "Cliente:", Element.ALIGN_LEFT, null);
        table.addCell(cell);

        Long customerId = de.getDomicile().getCustomerId();
        Particular particular = paController.findByCustomerId(customerId);
        cell = createCell(null, particular.getName() + " " + particular.getLastName(),
                Element.ALIGN_RIGHT, null);
        table.addCell(cell);

        cell = createCell(null, "\n", Element.ALIGN_CENTER, 2);
        table.addCell(cell);

        cell = createCell(null, null, Element.ALIGN_CENTER, 2);
        table.addCell(cell);

        document.add(table);
        document.close();

        return document;
    }

    /**
     * A generic method for the generations of cells.
     *
     * @param element   The element to be added to the cell
     * @param text      The text of the paragraph
     * @param alignment The alignment of the paragraph
     * @param colSpan   the span of the cell column
     * @return the cell created.
     */
    public static PdfPCell createCell(Element element, String text, Integer alignment,
                                      Integer colSpan) {

        Font font = FontFactory.getFont("Courier", 12, Font.NORMAL);

        PdfPCell cell = new PdfPCell();
        if (element != null) {
            cell.addElement(element);
        } else {
            if (text != null) {
                text = text.toUpperCase();
                Paragraph p = new Paragraph(text, font);
                p.setAlignment(alignment);
                cell.addElement(p);
            }
        }

        if (colSpan != null && colSpan >= 0)
            cell.setColspan(colSpan);

        cell.setPaddingRight(3);
        cell.setPaddingLeft(3);

        return cell;
    }

}
