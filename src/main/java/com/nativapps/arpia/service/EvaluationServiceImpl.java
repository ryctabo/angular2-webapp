package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EvaluationDao;
import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.entity.Evaluation;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.EvaluationRequest;
import com.nativapps.arpia.model.dto.EvaluationResponse;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.converter.MessengerSimpleConverter;
import com.nativapps.arpia.service.converter.UserSimpleConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.TextUtil;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.2
 */
public class EvaluationServiceImpl extends GenericService implements EvaluationService,
        DtoConverter<Evaluation, EvaluationRequest, EvaluationResponse> {

    /**
     * An instance of {@link EvaluationDao}.
     */
    private final EvaluationDao eController;

    /**
     * An instance of {@link MessengerDao}.
     */
    private final MessengerDao mController;

    /**
     * Construct an instance from the given parameters.
     *
     * @param eController evaluation controller
     * @param mController messenger controller
     */
    public EvaluationServiceImpl(EvaluationDao eController, MessengerDao mController) {
        this.eController = eController;
        this.mController = mController;
    }

    @Override
    public ListResponse<EvaluationResponse> getAll(Long messengerId, int start, int size) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<EvaluationResponse> evaluations = new ArrayList<>();
        for (Evaluation evaluation : eController.findAll(start, size, messengerId))
            evaluations.add(convertToDto(evaluation));

        long total = eController.findAllCount(messengerId);
        return new ListResponse<>(total, evaluations);
    }

    @Override
    public EvaluationResponse get(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("evaluation.id"));

        Evaluation evaluation = eController.find(id);
        if (evaluation == null) {
            final String FORMAT = config.getString("evaluation.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }

        return convertToDto(evaluation);
    }

    @Override
    public EvaluationResponse add(EvaluationRequest data, UserInfo userInfo) {
        Messenger messenger = null;
        if (data == null) {
            throw new BadRequestException(config.getString("evaluation.is_null"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);

            if (data.getMessenger() == null || data.getMessenger() <= 0) {
                emd.addMessage(config.getString("messenger.id_required"));
            } else {
                messenger = mController.find(data.getMessenger());
                if (messenger == null) {
                    final String FORMAT = config.getString("messenger.not_found");
                    emd.addMessage(String.format(FORMAT, data.getMessenger()));
                }
            }

            if (TextUtil.isEmpty(data.getDetail()))
                emd.addMessage(config.getString("evaluation.detail"));
            if (data.getConfidence() == null)
                emd.addMessage(config.getString("evaluation.confidence"));
            if (data.getManagement() == null)
                emd.addMessage(config.getString("evaluation.management"));
            if (data.getSpeed() == null)
                emd.addMessage(config.getString("evaluation.speed"));

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }

        Evaluation entity = convertToEntity(data);
        entity.setMessenger(messenger);
        entity.setUser(new User(userInfo.getId()));
        entity.setRegisterDate(Calendar.getInstance());

        return convertToDto(eController.save(entity));
    }

    @Override
    public Evaluation convertToEntity(EvaluationRequest data) {
        Evaluation evaluation = new Evaluation();

        evaluation.setDetail(data.getDetail());
        evaluation.setConfidence(data.getConfidence());
        evaluation.setManagement(data.getManagement());
        evaluation.setSpeed(data.getSpeed());

        return evaluation;
    }

    @Override
    public EvaluationResponse convertToDto(Evaluation entity) {
        EvaluationResponse response = new EvaluationResponse();

        response.setId(entity.getId());
        response.setDetail(entity.getDetail());

        response.setMessenger(MessengerSimpleConverter.instance()
                .convertToDto(entity.getMessenger()));
        response.setUser(UserSimpleConverter.instance()
                .convertToDto(entity.getUser()));

        response.setConfidence(entity.getConfidence());
        response.setSpeed(entity.getSpeed());
        response.setManagement(entity.getManagement());
        response.setRegisterDate(entity.getRegisterDate());

        return response;
    }

}
