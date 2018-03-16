package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.CashDao;
import com.nativapps.arpia.database.dao.DebtDao;
import com.nativapps.arpia.database.entity.Cash;
import com.nativapps.arpia.database.entity.Debt;
import com.nativapps.arpia.database.entity.Operation;
import com.nativapps.arpia.database.entity.Payment;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.PaymentData;
import com.nativapps.arpia.rest.UserInfo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import com.nativapps.arpia.database.dao.PaymentDao;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.converter.UserSimpleConverter;
import com.nativapps.arpia.service.validator.PaymentValidator;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class PaymentServiceImpl extends GenericService implements PaymentService,
        DtoConverter<Payment, PaymentData, PaymentData> {

    private final PaymentDao controller;

    private final DebtDao debtController;

    private final CashDao cashController;

    public PaymentServiceImpl(PaymentDao controller, DebtDao debtController, CashDao cashController) {
        this.controller = controller;
        this.debtController = debtController;
        this.cashController = cashController;
    }

    @Override
    public ListResponse<PaymentData> get(Long debtId, Calendar from, Calendar to, int start,
            int size, String orderBy, OrderType type) {

        if (debtId == null || debtId <= 0)
            throw new BadRequestException(config.getString("debt.id"));

        List<PaymentData> items = new ArrayList<>();
        List<Payment> payments = controller.findAll(debtId, from, to, start, size,
                orderBy, type);

        for (Payment payment : payments) {
            items.add(convertToDto(payment));
        }

        long count = controller.getCount(debtId, from, to);

        return new ListResponse<>(count, items);
    }

    @Override
    public PaymentData addPayment(Long debtId, PaymentData request, UserInfo userInfo) {

        if (debtId == null || debtId <= 0)
            throw new BadRequestException(config.getString("debt.id"));

        PaymentValidator.validate(request, userInfo, config);

        Payment newPayment = convertToEntity(request);
        newPayment.setAuthorizedBy(new User(userInfo.getId()));
        int index = controller.findAll(debtId).size() + 1;
        newPayment.getId().setIndex(index);

        Debt debt = debtController.find(debtId);
        Double realValue = controller.getRealValue(debtId, debt.getValue());

        if (request.getPayment() > realValue)
            throw new BadRequestException(config.getString("payment.real_value"));

        newPayment.setDebt(debt);

        Cash entity = new Cash();
        entity.setAmount(Double.valueOf(request.getPayment()).floatValue());
        entity.setOperation(new Operation(request.getOperationId()));
        entity.setReason(debt.getConcept());
        entity.setRegisterDate(Calendar.getInstance());
        entity.setRegisterType(Cash.Type.IN);
        Cash last = cashController.lastRegister();

        if (last != null)
            entity.setTotalCash((float) (last.getTotalCash() + request.getPayment()));
        else
            entity.setTotalCash(entity.getAmount());

        cashController.save(entity);

        return convertToDto(controller.save(newPayment));
    }

    @Override
    public PaymentData get(Long debtId, Integer index) {
        Payment payment = null;

        if (debtId == null || debtId <= 0)
            throw new BadRequestException(config.getString("debt.id"));

        if (index == null || index <= 0)
            throw new BadRequestException(config.getString("payment.index"));

        for (Payment newPayment : controller.findAll(debtId)) {
            if (newPayment.getId().getIndex() == index) {
                payment = newPayment;
                break;
            }
        }

        if (payment == null) {
            final String FORMAT = config.getString("payment.not_found");
            throw new NotFoundException(String.format(FORMAT, index));
        }

        return convertToDto(payment);
    }

    @Override
    public Payment convertToEntity(PaymentData data) {
        Payment payment = new Payment();

        payment.setPayment(data.getPayment());
        payment.setDate(Calendar.getInstance());

        return payment;
    }

    @Override
    public PaymentData convertToDto(Payment entity) {
        PaymentData payment = new PaymentData();

        payment.setIndex(entity.getId().getIndex());
        payment.setDate(entity.getDate());
        payment.setPayment(entity.getPayment());
        payment.setAuthorizedBy(UserSimpleConverter.instance()
                .convertToDto(entity.getAuthorizedBy()));

        return payment;
    }

}
