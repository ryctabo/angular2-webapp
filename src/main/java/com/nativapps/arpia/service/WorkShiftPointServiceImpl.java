package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.MapPointDao;
import com.nativapps.arpia.database.dao.MessengerDao;
import com.nativapps.arpia.database.dao.WorkShiftPointDao;
import com.nativapps.arpia.database.entity.MapPoint;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.WorkShiftPoint;
import com.nativapps.arpia.model.dto.ErrorMessageData;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.WorkShiftPointRequest;
import com.nativapps.arpia.model.dto.WorkShiftPointResponse;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.converter.MapPointSimpleConverter;
import com.nativapps.arpia.service.converter.MessengerSimpleConverter;
import com.nativapps.arpia.service.exception.ServiceException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.*;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class WorkShiftPointServiceImpl extends GenericService implements WorkShiftPointService,
        DtoConverter<WorkShiftPoint, WorkShiftPointRequest, WorkShiftPointResponse> {

    /**
     * Instance of {@link WorkShiftPointDao} controller.
     */
    private final WorkShiftPointDao wController;

    /**
     * Instance of {@link MessengerDao} controller.
     */
    private final MessengerDao mController;

    /**
     * Instance of {@link MapPointDao} controller.
     */
    private final MapPointDao mpController;

    /**
     * Key of map point.
     */
    private static final String MAP_POINT_KEY = "point";

    /**
     * Key of messenger.
     */
    private static final String MESSENGER_KEY = "messenger";

    /**
     * Create a new instance of {@link WorkShiftPointServiceImpl}.
     *
     * @param wController  work shift point controller
     * @param mController  messenger controller
     * @param mpController map point controller
     */
    public WorkShiftPointServiceImpl(WorkShiftPointDao wController,
                                     MessengerDao mController,
                                     MapPointDao mpController) {
        this.wController = wController;
        this.mController = mController;
        this.mpController = mpController;
    }

    /**
     * Get entity from the given ID.
     *
     * @param id the work shift point ID
     * @return work shift point
     */
    private WorkShiftPoint getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("workshiftpoint.id"));
        WorkShiftPoint workShiftPoint = this.wController.find(id);
        if (workShiftPoint == null) {
            final String FORMAT = config.getString("workshiftpoint.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return workShiftPoint;
    }

    /**
     * Validate if {@code workShiftPoint} contains all attributes
     * needed to create a new entity in the database.
     *
     * @param workShiftPoint object to evaluate
     * @return map of entities
     */
    private Map<String, ? extends Serializable> validate(WorkShiftPointRequest workShiftPoint) {
        Map<String, Serializable> values = new HashMap<>(2);
        if (workShiftPoint == null) {
            throw new BadRequestException(config.getString("workshiftpoint.is_null"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);

            Long mapPointId = workShiftPoint.getMapPoint();
            if (mapPointId == null || mapPointId <= 0) {
                emd.addMessage(config.getString("point.id"));
            } else {
                MapPoint mapPoint = mpController.find(mapPointId);
                if (mapPoint == null) {
                    final String FORMAT = config.getString("point.not_found");
                    emd.addMessage(String.format(FORMAT, mapPointId));
                } else {
                    values.put(MAP_POINT_KEY, mapPoint);
                }
            }

            Long messengerId = workShiftPoint.getMessenger();
            if (messengerId == null || messengerId <= 0) {
                emd.addMessage(config.getString("messenger.id_required"));
            } else {
                Messenger messenger = mController.find(messengerId);
                if (messenger == null) {
                    final String FORMAT = config.getString("messenger.not_found");
                    emd.addMessage(String.format(FORMAT, messengerId));
                } else {
                    values.put(MESSENGER_KEY, messenger);
                }
            }

            Calendar start = workShiftPoint.getStart();
            Calendar end = workShiftPoint.getEnd();
            if (start == null)
                emd.addMessage(config.getString("workshiftpoint.start"));
            if (end == null)
                emd.addMessage(config.getString("workshiftpoint.end"));
            if (start != null && end != null && start.compareTo(end) > 0)
                emd.addMessage(config.getString("workshiftpoint.start_end"));

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }

        return values;
    }

    @Override
    public ListResponse<WorkShiftPointResponse> get(Long mapPointId, int start, int size) {
        List<WorkShiftPointResponse> items = new ArrayList<>();
        for (WorkShiftPoint item : wController.findAll(mapPointId, start, size))
            items.add(this.convertToDto(item));
        long total = wController.count(mapPointId);
        return new ListResponse<>(total, items);
    }

    @Override
    public WorkShiftPointResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public WorkShiftPointResponse add(WorkShiftPointRequest request) {
        Map<String, ? extends Serializable> values = validate(request);

        WorkShiftPoint workShiftPoint = convertToEntity(request);
        workShiftPoint.setMapPoint(((MapPoint) values.get(MAP_POINT_KEY)));
        workShiftPoint.setMessenger(((Messenger) values.get(MESSENGER_KEY)));

        return convertToDto(wController.save(workShiftPoint));
    }

    @Override
    public WorkShiftPointResponse update(Long id, WorkShiftPointRequest request) {
        WorkShiftPoint workShiftPoint = getEntity(id);
        Map<String, ? extends Serializable> values = validate(request);

        workShiftPoint.setMapPoint(((MapPoint) values.get(MAP_POINT_KEY)));
        workShiftPoint.setMessenger(((Messenger) values.get(MESSENGER_KEY)));
        workShiftPoint.setStartDateAndTime(request.getStart());
        workShiftPoint.setEndDateAndTime(request.getEnd());

        return convertToDto(wController.save(workShiftPoint));
    }

    @Override
    public WorkShiftPoint convertToEntity(WorkShiftPointRequest data) {
        WorkShiftPoint workShiftPoint = new WorkShiftPoint();
        workShiftPoint.setStartDateAndTime(data.getStart());
        workShiftPoint.setEndDateAndTime(data.getEnd());
        return workShiftPoint;
    }

    @Override
    public WorkShiftPointResponse convertToDto(WorkShiftPoint entity) {
        WorkShiftPointResponse workShiftPoint = new WorkShiftPointResponse();

        workShiftPoint.setId(entity.getId());

        workShiftPoint.setMapPoint(MapPointSimpleConverter.instance()
                .convertToDto(entity.getMapPoint()));
        workShiftPoint.getMapPoint().setPricings(null);
        workShiftPoint.getMapPoint().setSchedules(null);

        workShiftPoint.setMessenger(MessengerSimpleConverter.instance()
                .convertToDto(entity.getMessenger()));

        workShiftPoint.setStart(entity.getStartDateAndTime());
        workShiftPoint.setEnd(entity.getEndDateAndTime());

        return workShiftPoint;
    }
}
