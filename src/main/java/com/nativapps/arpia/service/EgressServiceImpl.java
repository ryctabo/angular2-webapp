package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.CashDao;
import com.nativapps.arpia.database.dao.EgressDao;
import com.nativapps.arpia.database.entity.*;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.rest.UserInfo;
import com.nativapps.arpia.rest.bean.EgressType;
import com.nativapps.arpia.service.converter.CustomerSimpleConverter;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.converter.MessengerSimpleConverter;
import com.nativapps.arpia.service.converter.UserSimpleConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.EgressValidator;
import com.nativapps.arpia.util.TextUtil;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0.1
 */
public class EgressServiceImpl extends GenericService implements EgressService,
        DtoConverter<Egress, EgressData, EgressData> {

    /**
     * Instance of {@link EgressDao}.
     */
    private final EgressDao mainController;

    /**
     * Instance of {@link CashDao}.
     */
    private final CashDao cController;


    /**
     * Construct an object of {@link EgressServiceImpl}.
     *
     * @param controller  egress controller
     * @param cController cash controller
     */
    EgressServiceImpl(EgressDao controller, CashDao cController) {
        this.mainController = controller;
        this.cController = cController;
    }

    /**
     * Get egress entity from the given ID.
     *
     * @param id the egress ID
     * @return egress entity
     * @throws BadRequestException if the egress ID is null or less than or equal to 0.
     * @throws NotFoundException   if the egress data obtained is null.
     */
    private Egress getEntity(Long id) throws NotFoundException, BadRequestException {
        if (id == null || id <= 0)
            throw new BadRequestException(config.getString("egress.id"));
        Egress egress = mainController.find(id);
        if (egress == null) {
            final String FORMAT = config.getString("egress.not_found");
            throw new NotFoundException(String.format(FORMAT, id));
        }
        return egress;
    }

    /**
     * Validate egress data request if contains all attributes
     * need for save in the database.
     *
     * @param egressData the egress data to evaluate
     * @param userInfo   the user information
     */
    private void validate(EgressData egressData, UserInfo userInfo) {
        if (userInfo == null)
            throw new InternalServerErrorException(config.getString("user.info"));

        if (egressData == null) {
            throw new BadRequestException(config.getString("egress.is_null"));
        } else {
            int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            ErrorMessageData emd = new ErrorMessageData(statusCode);
            EgressValidator.validate(egressData, emd, config);

            if (!emd.getMessages().isEmpty())
                throw new ServiceException(emd);
        }
    }

    @Override
    public ListResponse<EgressData> get(Long userId, Boolean closured, EgressType egressType,
                                        String search, Calendar from, Calendar to,
                                        int start, int size, String orderBy, OrderType type) {
        List<EgressData> items = new ArrayList<>();
        List<Egress> egresses = mainController.findAll(
                userId,
                closured,
                egressType,
                search,
                from,
                to,
                start,
                size,
                orderBy,
                type
        );
        for (Egress egress : egresses)
            items.add(convertToDto(egress));

        long count = mainController.count(userId, closured, egressType, search, from, to);
        return new ListResponse<>(count, items);
    }

    @Override
    public EgressData get(Long id) {
        Egress egress = getEntity(id);
        return convertToDto(egress);
    }

    @Override
    public EgressData add(EgressData request, UserInfo userInfo) {
        validate(request, userInfo);
        Egress newEgress = convertToEntity(request);
        newEgress.setAssignedBy(new User(userInfo.getId()));

        Cash entity = new Cash();
        entity.setAmount(request.getValue().floatValue());
        entity.setOperation(new Operation(request.getOperationId()));
        entity.setReason(request.getConcept());
        entity.setRegisterDate(Calendar.getInstance());
        entity.setRegisterType(Cash.Type.OUT);
        entity.setUser(new User(userInfo.getId()));
        Cash last = cController.lastRegister();

        if (last != null && request.getValue() <= last.getTotalCash()) {
            entity.setTotalCash(last.getTotalCash() - entity.getAmount());
            cController.save(entity);
        } else {
            throw new NotFoundException(config.getString("cash.no_money"));
        }

        return convertToDto(mainController.save(newEgress));
    }

    @Override
    public EgressData update(Long id, EgressData request, UserInfo userInfo) {
        Egress egress = getEntity(id);
        validate(request, userInfo);

        if (egress instanceof CustomerEgress) {
            CustomerEgressData crequest = (CustomerEgressData) request;

            CustomerData customer = new CustomerData();
            customer.setId(crequest.getCustomer().getId());

            ((CustomerEgress) egress).setCustomer(customer);
        } else if (egress instanceof ManagementEgress) {
            ManagementEgressData arequest = (ManagementEgressData) request;

            ((ManagementEgress) egress).setIdentification(arequest.getIdentification());
            ((ManagementEgress) egress).setName(arequest.getName());
            ((ManagementEgress) egress).setLastName(arequest.getLastName());
        } else if (egress instanceof MessengerEgress) {
            MessengerEgressData mrequest = (MessengerEgressData) request;

            Messenger messenger = new Messenger();
            messenger.setId(mrequest.getMessengerId());

            ((MessengerEgress) egress).setTypeConcept(mrequest.getTypeConcept());
            ((MessengerEgress) egress).setMessenger(messenger);
        } else if (egress instanceof OperatorEgress) {
            OperatorEgressData orequest = (OperatorEgressData) request;

            ((OperatorEgress) egress).setOperator(new User(userInfo.getId()));
            ((OperatorEgress) egress).setTypeConcept(orequest.getTypeConcept());
        }

        egress.setAssignedBy(new User(userInfo.getId()));
        egress.setConcept(request.getConcept());
        egress.setCreated(request.getCreated());
        egress.setValue(request.getValue());

        return convertToDto(mainController.save(egress));
    }

    @Override
    public EgressData closure(Long id, ClosureData closureData, UserInfo userInfo) {
        if (userInfo == null) {
            throw new InternalServerErrorException(config.getString("egress.closure_user"));
        }

        Egress egress = getEntity(id);

        int statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        ErrorMessageData emd = new ErrorMessageData(statusCode);

        if (TextUtil.isEmpty(closureData.getObservation()))
            emd.addMessage(config.getString("egress.closure_observation"));
        if (closureData.getType() == null)
            emd.addMessage(config.getString("egress.closure_type"));

        if (!emd.getMessages().isEmpty())
            throw new ServiceException(emd);

        Closure closure = new Closure();

        closure.setClosuredBy(new User(userInfo.getId()));
        closure.setObservation(closureData.getObservation());
        closure.setType(closureData.getType());
        closure.setDate(Calendar.getInstance());

        egress.setClosure(closure);

        return convertToDto(mainController.save(egress));
    }

    @Override
    public Egress convertToEntity(EgressData request) {
        Egress egress = null;

        if (request instanceof CustomerEgressData) {
            egress = new CustomerEgress();
            CustomerEgressData customerEgress = (CustomerEgressData) request;
            ((CustomerEgress) egress).setCustomer(new CustomerData(customerEgress.getCustomerId()));
        } else if (request instanceof ManagementEgressData) {
            egress = new ManagementEgress();
            ManagementEgressData managementEgress = (ManagementEgressData) request;
            ((ManagementEgress) egress).setIdentification(managementEgress.getIdentification());
            ((ManagementEgress) egress).setName(managementEgress.getName());
            ((ManagementEgress) egress).setLastName(managementEgress.getLastName());
        } else if (request instanceof MessengerEgressData) {
            egress = new MessengerEgress();
            MessengerEgressData messengerEgress = (MessengerEgressData) request;

            FeeInfo feeInfo = new FeeInfo();
            FeeInfoData feeInfoRequest = messengerEgress.getFeeInfo();
            feeInfo.setFirstPayment(feeInfoRequest.getFirstPayment());
            feeInfo.setNumberOfFees(feeInfoRequest.getNumberOfFees());
            feeInfo.setPeriod(feeInfoRequest.getPeriod());

            Debt debt = new Debt();
            debt.setConcept(messengerEgress.getConcept());
            debt.setValue(messengerEgress.getValue());
            debt.setFeeInfo(feeInfo);
            debt.setCreated(Calendar.getInstance());
            debt.setOwner(new User(messengerEgress.getMessengerId())); //this is a bug

            ((MessengerEgress) egress).setDebt(debt);
            ((MessengerEgress) egress).setTypeConcept(messengerEgress.getTypeConcept());
            ((MessengerEgress) egress).setMessenger(new Messenger(messengerEgress.getMessengerId()));
        } else if (request instanceof OperatorEgressData) {
            egress = new OperatorEgress();
            OperatorEgressData operatorEgress = (OperatorEgressData) request;

            FeeInfo feeInfo = new FeeInfo();
            FeeInfoData feeInfoRequest = operatorEgress.getFeeInfo();
            feeInfo.setFirstPayment(feeInfoRequest.getFirstPayment());
            feeInfo.setNumberOfFees(feeInfoRequest.getNumberOfFees());
            feeInfo.setPeriod(feeInfoRequest.getPeriod());

            Debt debt = new Debt();
            debt.setConcept(operatorEgress.getConcept());
            debt.setValue(operatorEgress.getValue());
            debt.setFeeInfo(feeInfo);
            debt.setCreated(Calendar.getInstance());
            debt.setOwner(new User(operatorEgress.getOperatorId()));

            ((OperatorEgress) egress).setDebt(debt);
            ((OperatorEgress) egress).setTypeConcept(operatorEgress.getTypeConcept());
            ((OperatorEgress) egress).setOperator(new User(operatorEgress.getOperatorId()));
        }

        if (egress != null) {
            egress.setValue(request.getValue());
            egress.setConcept(request.getConcept());
            egress.setCreated(Calendar.getInstance());
        }

        return egress;
    }

    @Override
    public EgressData convertToDto(Egress entity) {
        EgressData egressdata = null;

        if (entity instanceof CustomerEgress) {
            CustomerEgress customerEgress = (CustomerEgress) entity;
            egressdata = new CustomerEgressData();
            CustomerDataDto customer = CustomerSimpleConverter.instance()
                    .convertToDto(customerEgress.getCustomer());
            ((CustomerEgressData) egressdata).setCustomer(customer);
        } else if (entity instanceof ManagementEgress) {
            ManagementEgress ae = (ManagementEgress) entity;
            egressdata = new ManagementEgressData();
            ((ManagementEgressData) egressdata).setIdentification(ae.getIdentification());
            ((ManagementEgressData) egressdata).setName(ae.getName());
            ((ManagementEgressData) egressdata).setLastName(ae.getLastName());
        } else if (entity instanceof MessengerEgress) {
            MessengerEgress messengerEgress = (MessengerEgress) entity;
            egressdata = new MessengerEgressData();
            MessengerResponse messenger = MessengerSimpleConverter.instance()
                    .convertToDto(messengerEgress.getMessenger());
            ((MessengerEgressData) egressdata).setMessenger(messenger);
            ((MessengerEgressData) egressdata).setTypeConcept(messengerEgress.getTypeConcept());
        } else if (entity instanceof OperatorEgress) {
            OperatorEgress operatorEgress = (OperatorEgress) entity;
            egressdata = new OperatorEgressData();
            UserResponse operatorResponse = UserSimpleConverter.instance()
                    .convertToDto(operatorEgress.getOperator());
            ((OperatorEgressData) egressdata).setTypeConcept(operatorEgress.getTypeConcept());
            ((OperatorEgressData) egressdata).setOperator(operatorResponse);
        }

        if (egressdata != null) {
            egressdata.setId(entity.getId());
            egressdata.setValue(entity.getValue());
            egressdata.setConcept(entity.getConcept());
            egressdata.setCreated(entity.getCreated());

            Closure closure = entity.getClosure();
            if (closure != null) {
                ClosureData closureData = new ClosureData();

                UserResponse user = UserSimpleConverter.instance()
                        .convertToDto(closure.getClosuredBy());

                closureData.setClosuredBy(user);
                closureData.setDate(closure.getDate());
                closureData.setObservation(closure.getObservation());
                closureData.setType(closure.getType());

                egressdata.setClosure(closureData);
            }
        }

        return egressdata;
    }


}
