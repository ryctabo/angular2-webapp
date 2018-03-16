package com.nativapps.arpia.service.converter;

import com.nativapps.arpia.database.entity.Assignment;
import com.nativapps.arpia.database.entity.Discount;
import com.nativapps.arpia.database.entity.DomicileExecute;
import com.nativapps.arpia.model.dto.AssignmentData;
import com.nativapps.arpia.model.dto.DomicileExecuteResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.2
 */
public class DomicileExecuteSimpleConverter implements DtoConverter<DomicileExecute, Void, DomicileExecuteResponse> {

    private static final DomicileExecuteSimpleConverter INSTANCE = new DomicileExecuteSimpleConverter();

    /**
     * Don't let anyone instantiate this class.
     */
    private DomicileExecuteSimpleConverter() { }

    public static DomicileExecuteSimpleConverter instance() {
        return INSTANCE;
    }

    @Override
    public DomicileExecute convertToEntity(Void request) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public DomicileExecuteResponse convertToDto(DomicileExecute domicile) {
        DomicileExecuteResponse response = new DomicileExecuteResponse();
        response.setId(domicile.getId());

        response.setDomicile(DomicileSimpleConverter.instance()
                .convertToDto(domicile.getDomicile()));

        response.setUser(UserSimpleConverter.instance()
                .convertToDto(domicile.getUser()));

        List<AssignmentData> assignments = new ArrayList<>();
        for (Assignment a : domicile.getAssignments())
            assignments.add(new AssignmentData(
                    a.getId().getIndex(),
                    a.getMessengerId(),
                    a.getRegisterDate()
            ));
        response.setAssignments(assignments);

        Discount discount = domicile.getDiscount();
        Float priceToPaid;
        if (discount != null) {
            response.setDiscount(discount.getId());
            priceToPaid = domicile.getDomicile().getPrice() * (1 - discount.getPercent());
        } else {
            priceToPaid = domicile.getDomicile().getPrice();
        }
        response.setPriceToPaid(priceToPaid);

        response.setAutomatic(domicile.isAutomatic());
        response.setCancelDate(domicile.getCancelDate());
        response.setCancelReason(domicile.getCancelReason());
        response.setFinishDate(domicile.getFinishDate());
        response.setStartDate(domicile.getStartDate());
        response.setStatus(domicile.getStatus());

        return response;
    }
}
