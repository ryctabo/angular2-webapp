package com.nativapps.arpia.model.dto;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class SettlementResponse extends SettlementData {

    private Float total;

    public SettlementResponse() {
    }
    
    public SettlementResponse(Float produced, Float credits, Float advances, 
            Float expenses) {
        this.produced = produced;
        this.credits = credits;
        this.advances = advances;
        this.expenses = expenses;
        this.total = produced-credits-advances-expenses;
    }
    
    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
