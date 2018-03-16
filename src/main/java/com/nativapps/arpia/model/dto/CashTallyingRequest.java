package com.nativapps.arpia.model.dto;

import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class CashTallyingRequest {
    
    private List<CashRegisterRequest> cashRegisters;
    
    private SettlementRequest settlement;
    
    private String observations;

    public List<CashRegisterRequest> getCashRegisters() {
        return cashRegisters;
    }

    public void setCashRegisters(List<CashRegisterRequest> cashRegisters) {
        this.cashRegisters = cashRegisters;
    }

    public SettlementRequest getSettlement() {
        return settlement;
    }

    public void setSettlement(SettlementRequest settlement) {
        this.settlement = settlement;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
