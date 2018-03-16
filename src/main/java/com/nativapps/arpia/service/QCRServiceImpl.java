package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.CustomerDataDao;
import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.dao.QCRDao;
import com.nativapps.arpia.database.entity.CustomerData;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.QCR;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.QCRRequest;
import com.nativapps.arpia.model.dto.QCRResponse;
import com.nativapps.arpia.service.converter.CustomerSimpleConverter;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.converter.MessengerSimpleConverter;
import com.nativapps.arpia.util.TextUtil;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class QCRServiceImpl extends GenericService implements QCRService,
        DtoConverter<QCR, QCRRequest, QCRResponse> {

    private final QCRDao qController;

    private final CustomerDataDao cController;
    
    private final MessengerDao mController;

    public QCRServiceImpl(QCRDao qController, CustomerDataDao cController, MessengerDao mController) {
        this.qController = qController;
        this.cController = cController;
        this.mController = mController;
    }

    /**
     * Get entity from the given parameters.
     *
     * @param customerId the customer ID
     * @param index      the INDEX of QCR
     * @return QCR entity
     */
    private QCR getEntity(Long customerId, Integer index) {
        if (customerId == null || customerId <= 0)
            throw new BadRequestException(config.getString("customer.id_required"));
        if (index == null || index <= 0)
            throw new BadRequestException(config.getString("qcr.index"));
        QCR qcr = qController.find(new QCR.QCRPK(index, customerId));
        if (qcr == null) {
            final String FORMAT = config.getString("qcr.not_found");
            throw new NotFoundException(String.format(FORMAT, customerId, index));
        }
        return qcr;
    }

    @Override
    public ListResponse<QCRResponse> getAll(int start, int size, Long customerId, Long messengerId, Calendar from, Calendar to, QCR.Status state) {
        List<QCRResponse> qcrs = new ArrayList<>();
        for (QCR qcr : qController.findAll(start, size, customerId, messengerId, from, to, state))
            qcrs.add(convertToDto(qcr));
        long total = qController.count(customerId, messengerId, from, to, state);
        return new ListResponse<>(total, qcrs);
    }

    @Override
    public QCRResponse get(Long customerId, Integer index) {
        return convertToDto(getEntity(customerId, index));
    }

    @Override
    public QCRResponse add(QCRRequest data) {
        Long customerId = data.getCustomer();
        if (customerId == null || customerId <= 0)
            throw new BadRequestException(config.getString("customer.id_required"));
        CustomerData customer = cController.find(customerId);
        if (customer == null) {
            final String FORMAT = config.getString("customer.not_found");
            throw new NotFoundException(String.format(FORMAT, customerId));
        }
        
        Long messengerId = data.getMessenger();
        if (messengerId == null || messengerId <= 0)
            throw new BadRequestException(config.getString("messenger.id_required"));
        Messenger messenger = mController.find(messengerId);
        if (messenger == null) {
            final String FORMAT = config.getString("messenger.not_found");
            throw new NotFoundException(String.format(FORMAT, messengerId));
        }
        
        if (TextUtil.isEmpty(data.getInconvenient()))
            throw new BadRequestException(config.getString("qcr.inconvenient"));

        int index = ((int) qController.count(customerId, messengerId, null, null, null)) + 1;

        QCR qcr = convertToEntity(data);
        qcr.getId().setIndex(index);
        qcr.setOpeningDate(Calendar.getInstance());

        return convertToDto(qController.save(qcr));
    }

    @Override
    public QCRResponse counterpart(Long customerId, Integer index, QCRRequest data) {
        if (TextUtil.isEmpty(data.getCounterpartVersion()))
            throw new BadRequestException(config.getString("qcr.counterpart"));

        QCR qcr = getEntity(customerId, index);
        if (qcr.getStatus() == QCR.Status.FINISHED)
            throw new BadRequestException(config.getString("qcr.finished"));

        qcr.setCounterpartVersion(data.getCounterpartVersion());
        qcr.setStatus(QCR.Status.IN_PROCESS);

        return convertToDto(qController.save(qcr));
    }

    @Override
    public QCRResponse solution(Long customerId, Integer index, QCRRequest data) {
        if (TextUtil.isEmpty(data.getSolution()))
            throw new BadRequestException(config.getString("qcr.solution"));

        QCR qcr = getEntity(customerId, index);
        if (TextUtil.isEmpty(qcr.getCounterpartVersion()))
            throw new BadRequestException(config.getString("qcr.counterpart_null"));
        if (qcr.getStatus() == QCR.Status.FINISHED)
            throw new BadRequestException(config.getString("qcr.finished"));

        qcr.setAdminEval(data.getEvaluation());
        qcr.setAdminSolution(data.getSolution());
        qcr.setStatus(QCR.Status.FINISHED);
        qcr.setClosingDate(Calendar.getInstance());

        return convertToDto(qController.save(qcr));
    }

    @Override
    public QCRResponse cancel(Long customerId, Integer index) {
        QCR qcr = getEntity(customerId, index);

        if (qcr.getStatus() == QCR.Status.FINISHED)
            throw new BadRequestException(config.getString("qcr.finished"));

        qcr.setStatus(QCR.Status.CANCELED);
        qcr.setClosingDate(Calendar.getInstance());

        return convertToDto(qController.save(qcr));
    }

    @Override
    public QCR convertToEntity(QCRRequest data) {
        QCR qcr = new QCR();
        qcr.setInconvenient(data.getInconvenient());
        qcr.setCustomer(new CustomerData(data.getCustomer()));
        qcr.setMessenger(new Messenger(data.getMessenger()));
        qcr.setStatus(QCR.Status.PENDING);
        return qcr;
    }

    @Override
    public QCRResponse convertToDto(QCR entity) {
        QCRResponse response = new QCRResponse();
        response.setIndex(entity.getId().getIndex());

        response.setCustomer(CustomerSimpleConverter.instance()
                .convertToDto(entity.getCustomer()));
        response.setMessenger(MessengerSimpleConverter.instance()
                .convertToDto(entity.getMessenger()));

        response.setInconvenient(entity.getInconvenient());
        response.setCounterpartVersion(entity.getCounterpartVersion());
        response.setEvaluation(entity.getAdminEval());
        response.setSolution(entity.getAdminSolution());
        response.setStatus(entity.getStatus());
        response.setOpeningDate(entity.getOpeningDate());
        response.setClosingDate(entity.getClosingDate());

        return response;
    }
}
