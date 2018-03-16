package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.DomicileDao;
import com.nativapps.arpia.database.entity.*;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.service.converter.DomicileSimpleConverter;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.DomicileValidator;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.2
 */
public class DomicileServiceImpl extends GenericService implements DomicileService,
        DtoConverter<Domicile, DomicileRequest, DomicileResponse> {

    private final DomicileDao controller;

    public DomicileServiceImpl(DomicileDao controller) {
        this.controller = controller;
    }

    /**
     * Get domicile entity from the given ID.
     *
     * @param id domicile ID
     * @return domicile data
     * @throws BadRequestException if the domicile Id is null or less than or equal to 0.
     * @throws NotFoundException   if the domicile data obtained is null.
     */
    private Domicile getEntity(Long id) throws BadRequestException,
            NotFoundException {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("domicile.id"));

        Domicile domicile = controller.find(id);
        if (domicile == null) {
            final String FORMAT = config.getString("domicile.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }

        return domicile;
    }

    /**
     * Validate if domicile have all attributes for save to database.
     *
     * @param domicile domicile to evaluate
     * @param userInfo user information
     * @throws BadRequestException if the domicile data is null.
     * @throws ServiceException    if the domicile data don't have any attribute required.
     */
    private void validateDomicile(DomicileRequest domicile, UserInfo userInfo)
            throws BadRequestException, ServiceException {
        if (userInfo == null)
            throw new InternalServerErrorException(config.getString("user.info"));

        if (domicile == null) {
            throw new BadRequestException(config.getString("domicile.is_null"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);
            DomicileValidator.evaluateDomicile(domicile, emd, config);

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }
    }

    @Override
    public ListResponse<DomicileResponse> getAll(Long customerId, Long operationId, Calendar date,
                                                 int start, int size, String orderBy, OrderType type) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<DomicileResponse> response = new ArrayList<>();
        List<Domicile> domiciles = controller.findAll(customerId, operationId,
                date, start, size, orderBy, type);

        for (Domicile domicile : domiciles)
            response.add(convertToDto(domicile));

        long total = controller.findAllCount(customerId, operationId, date);
        return new ListResponse<>(total, response);
    }

    @Override
    public DomicileResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public List<DomicileFrequent> getDomicilesFrequent(Long customerId, int start, int size) {
        List<DomicileFrequent> domiciles = new ArrayList<>();

        List<Object[]> frequents = controller.frequent(customerId, start, size);
        for (Object[] objs : frequents) {
            DomicileFrequent frequent = new DomicileFrequent();

            Domicile domicile = (Domicile) objs[0];

            DomicileSimpleConverter.instance().fill(frequent, domicile);
            fillResponse(frequent, domicile);

            frequent.setCount((Long) objs[1]);

            domiciles.add(frequent);
        }

        return domiciles;
    }

    @Override
    public DomicileResponse add(DomicileRequest data, UserInfo userInfo) {
        validateDomicile(data, userInfo);

        Domicile domicile = convertToEntity(data);
        domicile.setUser(new User(userInfo.getId()));
        domicile.setCreateDate(Calendar.getInstance());

        return convertToDto(controller.save(domicile));
    }

    @Override
    public DomicileResponse delete(Long id) {
        DomicileResponse response = get(id);
        controller.delete(id);
        return response;
    }

    @Override
    public Domicile convertToEntity(DomicileRequest request) {
        Domicile domicile = new Domicile();

        // Convert customer and operation
        domicile.setCustomer(new CustomerData(request.getCustomer()));
        domicile.setCustomerId(request.getCustomer());
        domicile.setOperation(new Operation(request.getOperation()));
        domicile.setOperationId(request.getOperation());

        // Convert general domicile data
        domicile.setPrice(request.getPrice());
        domicile.setMessengerGain(request.getMessengerGain());
        domicile.setType(request.getType());

        // Convert locations
        Set<Location> locations = new HashSet<>();
        int index = 1;
        for (LocationRequest l : request.getLocations()) {
            LatLng coordinate = null;
            if (l.getCoordinates() != null) {
                LatLngData c = l.getCoordinates();
                coordinate = new LatLng(c.getLatitude(), c.getLongitude());
            }
            Location location = new Location();
            location.setDomicile(domicile);
            location.setAddress(l.getAddress());
            location.setReference(l.getReference());

            Long neighId = l.getNeighborhood();
            Neighborhood neighborhood = neighId == null ? null : new Neighborhood(neighId);
            location.setNeighborhood(neighborhood);

            location.setCoordinates(coordinate);
            location.setTask(l.getTask());

            location.getId().setIndex(index++);
            locations.add(location);
        }
        domicile.setLocations(locations);

        // Convert domicile rating
        DomicileRating rating = new DomicileRating();
        if (request.getRating() != null) {
            DomicileRatingData r = request.getRating();
            rating.setPriority(r.getPriority());
            rating.setTrustLevel(r.getTrustLevel());
            rating.setComplexity(r.getComplexity());
            rating.setVelocity(r.getVelocity());
        }
        domicile.setRating(rating);

        // Convert domicile requirements
        DomicileRequirement requirements = new DomicileRequirement();
        if (request.getRequirements() != null) {
            DomicileRequirementData rq = request.getRequirements();
            requirements.setCashRequired(rq.getCashRequired());
            requirements.setMonitoring(rq.getMonitoring());
            requirements.setIdentification(rq.getIdentification());
            requirements.setEasyCheck(rq.getEasyCheck());
            requirements.setEnrutable(rq.getEnrutable());
            requirements.setHamper(rq.getHamper());
            requirements.setPriceSmart(rq.getPriceSmart());
            requirements.setTicket(rq.getTicket());
            requirements.setWineCellar(rq.getWineCellar());
        }
        domicile.setRequirements(requirements);

        // Convert periodicity
        if (request.getPeriodicity() != null) {
            RepeatData p = request.getPeriodicity();

            Repeat periodicity;
            if (p instanceof DailyRepeatData) {
                DailyRepeatData d = (DailyRepeatData) p;
                periodicity = new DailyRepeat();
                ((DailyRepeat) periodicity).setEvery(d.getEvery());
            } else if (p instanceof WeeklyRepeatData) {
                WeeklyRepeatData w = (WeeklyRepeatData) p;
                periodicity = new WeeklyRepeat();
                ((WeeklyRepeat) periodicity).setEvery(w.getEvery());
                ((WeeklyRepeat) periodicity).setFriday(w.getFriday());
                ((WeeklyRepeat) periodicity).setMonday(w.getMonday());
                ((WeeklyRepeat) periodicity).setSaturday(w.getSaturday());
                ((WeeklyRepeat) periodicity).setSunday(w.getSunday());
                ((WeeklyRepeat) periodicity).setThursday(w.getThursday());
                ((WeeklyRepeat) periodicity).setTuesday(w.getTuesday());
                ((WeeklyRepeat) periodicity).setWednesday(w.getWednesday());
            } else if (p instanceof MonthlyRepeatData) {
                MonthlyRepeatData m = (MonthlyRepeatData) p;
                periodicity = new MonthlyRepeat();
                ((MonthlyRepeat) periodicity).setEvery(m.getEvery());
                ((MonthlyRepeat) periodicity).setDayOfMonth(m.getDayOfMonth());
            } else {
                YearlyRepeatData y = (YearlyRepeatData) p;
                periodicity = new YearlyRepeat();
                ((YearlyRepeat) periodicity).setEvery(y.getEvery());
                ((YearlyRepeat) periodicity).setDateOfYear(y.getDateOfYear());
            }

            periodicity.setTime(p.getTime());
            periodicity.setStartDate(p.getStartDate());
            periodicity.setEndDate(p.getEndDate());

            domicile.setPeriodicity(periodicity);
        }

        return domicile;
    }

    @Override
    public DomicileResponse convertToDto(Domicile domicile) {
        DomicileResponse response = DomicileSimpleConverter.instance()
                .convertToDto(domicile);
        fillResponse(response, domicile);
        return response;
    }

    /**
     * Fill response with data of domicile.
     *
     * @param domicile domicile entity
     * @param response domicile response
     */
    private void fillResponse(DomicileResponse response, Domicile domicile) {
        // Convert domicile rating
        DomicileRating r = domicile.getRating();
        DomicileRatingData rating = new DomicileRatingData();
        rating.setPriority(r.getPriority());
        rating.setTrustLevel(r.getTrustLevel());
        rating.setComplexity(r.getComplexity());
        rating.setVelocity(r.getVelocity());
        response.setRating(rating);

        // Convert domicile requirement
        DomicileRequirement rq = domicile.getRequirements();
        DomicileRequirementData requirements = new DomicileRequirementData();
        requirements.setCashRequired(rq.getCashRequired());
        requirements.setMonitoring(rq.isMonitoring());
        requirements.setIdentification(rq.isIdentification());
        requirements.setEasyCheck(rq.isEasyCheck());
        requirements.setEnrutable(rq.isEnrutable());
        requirements.setHamper(rq.isHamper());
        requirements.setPriceSmart(rq.isPriceSmart());
        requirements.setTicket(rq.isTicket());
        requirements.setWineCellar(rq.isWineCellar());
        response.setRequirements(requirements);

        // Convert periodicity
        if (domicile.getPeriodicity() != null) {
            Repeat p = domicile.getPeriodicity();
            RepeatData periodicity;
            if (p instanceof DailyRepeat) {
                DailyRepeat d = (DailyRepeat) p;
                periodicity = new DailyRepeatData();
                ((DailyRepeatData) periodicity).setEvery(d.getEvery());
            } else if (p instanceof WeeklyRepeat) {
                WeeklyRepeat w = (WeeklyRepeat) p;
                periodicity = new WeeklyRepeatData();
                ((WeeklyRepeatData) periodicity).setEvery(w.getEvery());
                ((WeeklyRepeatData) periodicity).setFriday(w.isFriday());
                ((WeeklyRepeatData) periodicity).setMonday(w.isMonday());
                ((WeeklyRepeatData) periodicity).setSaturday(w.isSaturday());
                ((WeeklyRepeatData) periodicity).setSunday(w.isSunday());
                ((WeeklyRepeatData) periodicity).setThursday(w.isThursday());
                ((WeeklyRepeatData) periodicity).setTuesday(w.isTuesday());
                ((WeeklyRepeatData) periodicity).setWednesday(w.isWednesday());
            } else if (p instanceof MonthlyRepeat) {
                MonthlyRepeat m = (MonthlyRepeat) p;
                periodicity = new MonthlyRepeatData();
                ((MonthlyRepeatData) periodicity).setEvery(m.getEvery());
                ((MonthlyRepeatData) periodicity).setDayOfMonth(m.getDayOfMonth());
            } else {
                YearlyRepeat y = (YearlyRepeat) p;
                periodicity = new YearlyRepeatData();
                ((YearlyRepeatData) periodicity).setEvery(y.getEvery());
                ((YearlyRepeatData) periodicity).setDateOfYear(y.getDateOfYear());
            }

            periodicity.setTime(p.getTime());
            periodicity.setStartDate(p.getStartDate());
            periodicity.setEndDate(p.getEndDate());

            response.setPeriodicity(periodicity);
        }
    }

}
