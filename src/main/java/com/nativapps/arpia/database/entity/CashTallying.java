package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "CASH_TALLYING")
public class CashTallying implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    
    @Column(name = "REGISTER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registerDate;
    
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "cashTallying", cascade = CascadeType.ALL, 
            fetch = FetchType.EAGER)
    private List<CashRegister> cash;
    
    @Fetch(FetchMode.SELECT)
    @OneToOne(mappedBy = "cashTallying", cascade = CascadeType.ALL)
    private Settlement settlement;
    
    @Column(name = "OBSERVATIONS")
    private String observations;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public List<CashRegister> getCash() {
        return cash;
    }

    public void setCash(List<CashRegister> cash) {
        this.cash = cash;
    }
    
    public void addCash(CashRegister cashRegister) {
        if (this.cash == null)
            this.cash = new ArrayList<>();
        
        if(cashRegister != null) {
            this.cash.add(cashRegister);
            
            if (cashRegister.getCashTallying() != this)
                cashRegister.setCashTallying(this);
        }
    }

    public Settlement getSettlement() {
        return settlement;
    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
        
        if (this.settlement.getCashTallying() != this)
            this.settlement.setCashTallying(this);
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
