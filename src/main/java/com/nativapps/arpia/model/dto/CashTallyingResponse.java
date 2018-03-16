package com.nativapps.arpia.model.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class CashTallyingResponse {

    private Long id;
    
    private UserResponse user;
    
    private Calendar registerDate;
    
    private List<CashRegisterResponse> cashRegisters;
    
    private SettlementResponse settlement;
    
    private String observations;
    
    private Float totalPapers;
    
    private Float totalCoins;
    
    private Float totalCash;
    
    private Float imbalance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public List<CashRegisterResponse> getCashRegisters() {
        return cashRegisters;
    }

    public void setCashRegisters(List<CashRegisterResponse> cashRegisters) {
        this.cashRegisters = cashRegisters;
    }
    
    public void addCashRegister(CashRegisterResponse register) {
        if (this.cashRegisters == null)
            this.cashRegisters = new ArrayList<>();
        
        if (register != null)
            this.cashRegisters.add(register);
    }

    public Float getTotalPapers() {
        return totalPapers;
    }

    public void setTotalPapers(Float totalPapers) {
        this.totalPapers = totalPapers;
    }

    public Float getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(Float totalCoins) {
        this.totalCoins = totalCoins;
    }

    public Float getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(Float totalCash) {
        this.totalCash = totalCash;
    }

    public Float getImbalance() {
        return imbalance;
    }

    public void setImbalance(Float imbalance) {
        this.imbalance = imbalance;
    }

    public SettlementResponse getSettlement() {
        return settlement;
    }

    public void setSettlement(SettlementResponse settlement) {
        this.settlement = settlement;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
