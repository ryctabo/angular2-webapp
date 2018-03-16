package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.MapPointDao;
import com.nativapps.arpia.database.dao.PricingDao;
import com.nativapps.arpia.database.dao.ScheduleDao;
import com.nativapps.arpia.database.entity.LatLng;
import com.nativapps.arpia.database.entity.MapPoint;
import com.nativapps.arpia.database.entity.Pricing;
import com.nativapps.arpia.database.entity.Schedule;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.converter.MapPointSimpleConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.util.TextUtil;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class MapPointServiceImpl extends GenericService implements MapPointService,
        DtoConverter<MapPoint, MapPointRequest, MapPointResponse> {

    private final MapPointDao mController;

    private final ScheduleDao sController;

    private final PricingDao pController;

    public MapPointServiceImpl(MapPointDao mController, ScheduleDao sController, PricingDao pController) {
        this.mController = mController;
        this.sController = sController;
        this.pController = pController;
    }

    /**
     * Get map point entity from the given ID.
     *
     * @param id map point ID
     * @return map point data
     * @throws BadRequestException if the map point ID is null or less than or equal to 0.
     * @throws NotFoundException   if the map point data obtained is null.
     */
    private MapPoint getEntity(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("point.id"));
        MapPoint mapPoint = mController.find(id);
        if (mapPoint == null) {
            final String FORMAT = config.getString("point.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return mapPoint;
    }

    /**
     * Validate if map point data comply with all requeriments.
     *
     * @param mapPoint map point data
     * @throws BadRequestException if the monitoring data is null
     * @throws ServiceException    if the monitoring data don't have any attribute required
     */
    private void validate(MapPointRequest mapPoint) {
        if (mapPoint == null) {
            throw new BadRequestException(config.getString("point.is_null"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);

            if (TextUtil.isEmpty(mapPoint.getName()))
                emd.addMessage(config.getString("point.name"));

            if (mapPoint.getCoordinates() != null) {
                LatLngData coordinates = mapPoint.getCoordinates();
                if (coordinates.getLatitude() == null)
                    emd.addMessage(config.getString("location.latitude"));
                if (coordinates.getLongitude() == null)
                    emd.addMessage(config.getString("location.longitude"));
            } else {
                emd.addMessage(config.getString("point.coordinate"));
            }

            if (mapPoint.getType() == null)
                emd.addMessage(config.getString("point.type"));

            List<ScheduleData> schedules = mapPoint.getSchedules();
            if (schedules == null | schedules.isEmpty()) {
                emd.addMessage(config.getString("point.schedules"));
            } else {
                for (int index = 0; index < schedules.size(); index++) {
                    ScheduleData s = schedules.get(index);
                    if (s.getWeekday() == null) {
                        final String FORMAT = config.getString("schedule.weekday");
                        emd.addMessage(String.format(FORMAT, index));
                    }

                    if (s.getOpening() == null) {
                        final String FORMAT = config.getString("schedule.opening");
                        emd.addMessage(String.format(FORMAT, index));
                    }

                    if (s.getClosing() == null) {
                        final String FORMAT = config.getString("schedule.closing");
                        emd.addMessage(String.format(FORMAT, index));
                    }

                    if (s.getClosing().before(s.getOpening())
                            || (s.getBreakClosing() != null && s.getBreakOpening() != null
                            && s.getBreakClosing().before(s.getBreakOpening()))) {
                        final String FORMAT = config.getString("schedule.incoherent");
                        emd.addMessage(String.format(FORMAT, index));
                    }
                }
            }

            List<PricingData> pricing = mapPoint.getPricings();
            if (pricing == null || pricing.isEmpty()) {
                emd.addMessage(config.getString("point.pricing"));
            } else {
                for (int index = 0; index < pricing.size(); index++) {
                    PricingData p = pricing.get(index);
                    if (p.getDistance() == null || p.getDistance() <= 0) {
                        final String FORMAT = config.getString("pricing.distance");
                        emd.addMessage(String.format(FORMAT, index));
                    }

                    if (p.getPrice() == null || p.getPrice() <= 0) {
                        final String FORMAT = config.getString("pricing.price");
                        emd.addMessage(String.format(FORMAT, index));
                    }
                }
            }

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }
    }

    @Override
    public ListResponse<MapPointResponse> get(String search, int start, int size, String orderBy, OrderType type) {
        List<MapPointResponse> points = new ArrayList<>();

        for (MapPoint p : mController.findAll(search, start, size, orderBy, type))
            points.add(convertToDto(p));

        long total = mController.count(search);
        return new ListResponse<>(total, points);
    }

    @Override
    public MapPointResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public MapPointResponse add(MapPointRequest mapPoint) {
        validate(mapPoint);
        return convertToDto(mController.save(convertToEntity(mapPoint)));
    }

    @Override
    public MapPointResponse update(Long id, MapPointRequest mapPoTint) {
        MapPoint eMap = getEntity(id);//verify if exists the map point
        validate(mapPoTint); //validate data

        MapPoint entity = convertToEntity(mapPoTint);
        entity.setId(id);
        entity.getCoordinates().setId(eMap.getCoordinates().getId());

        for (Schedule s : entity.getSchedules())
            s.getId().setMapPointId(id);
        sController.deleteByMapPointId(id);

        for (Pricing p : entity.getPricings())
            p.getId().setMapPointId(id);
        pController.deleteByMapPointId(id);

        return convertToDto(mController.save(entity));
    }

    @Override
    public MapPointResponse delete(Long id) {
        MapPointResponse mapPoint = get(id);
        mController.delete(id);
        return mapPoint;
    }

    @Override
    public MapPoint convertToEntity(MapPointRequest data) {
        MapPoint mapPoint = new MapPoint();

        LatLngData co = data.getCoordinates();
        mapPoint.setCoordinates(new LatLng(co.getLatitude(), co.getLongitude()));

        mapPoint.setName(data.getName());

        if (data.getCoverage() != null)
            mapPoint.setCoverage(data.getCoverage());

        mapPoint.setType(data.getType());

        for (ScheduleData s : data.getSchedules()) {
            Schedule schedule = new Schedule();
            schedule.getId().setWeekday(s.getWeekday());
            schedule.setOpening(s.getOpening());
            schedule.setClosing(s.getClosing());
            schedule.setBreakOpening(s.getBreakOpening());
            schedule.setBreakClosing(s.getBreakClosing());

            mapPoint.addSchedule(schedule);
        }

        int index = 1;
        for (PricingData p : data.getPricings()) {
            Pricing pricing = new Pricing(p.getDistance(), p.getPrice());
            pricing.getId().setIndex(index++);

            mapPoint.addPricing(pricing);
        }

        return mapPoint;
    }

    @Override
    public MapPointResponse convertToDto(MapPoint entity) {
        MapPointResponse response = MapPointSimpleConverter.instance()
                .convertToDto(entity);

        for (Schedule s : entity.getSchedules()) {
            ScheduleData schedule = new ScheduleData();
            schedule.setWeekday(s.getId().getWeekday());
            schedule.setOpening(s.getOpening());
            schedule.setClosing(s.getClosing());
            schedule.setBreakOpening(s.getBreakOpening());
            schedule.setBreakClosing(s.getBreakClosing());

            response.getSchedules().add(schedule);
        }

        for (Pricing p : entity.getPricings()) {
            PricingData pricing = new PricingData();
            pricing.setIndex(p.getId().getIndex());
            pricing.setDistance(p.getDistance());
            pricing.setPrice(p.getPrice());

            response.getPricings().add(pricing);
        }

        return response;
    }
}
