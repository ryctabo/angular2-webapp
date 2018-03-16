package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "CUSTOMER_DATA")
@NamedQueries({
    @NamedQuery(name = "customerData.findParticular",
            query = "SELECT p.identification, p.name, p.lastName "
            + "FROM Particular p, CustomerData c "
            + "WHERE p.customerData.id = c.id AND c.id = :id"),
    @NamedQuery(name = "customerData.findEstablishment",
            query = "SELECT e.nit, e.name FROM Establishment e, CustomerData c "
            + "WHERE e.customerData.id = c.id AND c.id = :id"),
    @NamedQuery(name = "customerData.noComunication",
            query = "SELECT c FROM CustomerData c, DomicileExecute de "
            + "JOIN de.domicile d ON d.customerId = c.id "
            + "WHERE de.startDate <= :date"),
    @NamedQuery(name = "customerData.noComunicationCount",
            query = "SELECT COUNT(c) FROM CustomerData c, DomicileExecute de "
            + "JOIN de.domicile d ON d.customerId = c.id "
            + "WHERE de.startDate <= :date")
})
public class CustomerData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private long id;

    @Column(name = "OBSERVATIONS", columnDefinition = "TEXT")
    private String observations;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PARAMETER_ID")
    private CustomerParameter param;

    @OneToMany(mappedBy = "customerData")
    private List<Bonus> bonusList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CREDIT_INFO_ID")
    private CreditInfo creditInfo;

    @Column(name = "BANNED")
    private boolean banned;

    @OneToMany(mappedBy = "customer")
    private List<CustomerBanHistory> history;

    @OneToMany(mappedBy = "customer")
    private List<QCR> qcrList;

    @OneToOne(mappedBy = "customerData", fetch = FetchType.LAZY)
    private Particular particular;

    @OneToOne(mappedBy = "customerData", fetch = FetchType.LAZY)
    private Establishment establishment;

    public CustomerData() {
        this.creditInfo = new CreditInfo();
    }

    public CustomerData(long id) {
        this.id = id;
    }

    public CustomerData(CustomerType type) {
        this.creditInfo = new CreditInfo();
        this.type = type;
    }

    public CustomerData(String observations, CustomerType type) {
        this.creditInfo = new CreditInfo();
        this.observations = observations;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public CustomerParameter getParam() {
        return param;
    }

    public void setParam(CustomerParameter param) {
        this.param = param;
    }

    public List<Bonus> getBonusList() {
        return bonusList;
    }

    public void setBonusList(List<Bonus> bonusList) {
        this.bonusList = bonusList;
    }

    public void addBonus(Bonus bonus) {
        if (bonusList == null)
            bonusList = new ArrayList<>();

        if (bonus != null) {
            bonusList.add(bonus);
            if (bonus.getCustomerData() != this)
                bonus.setCustomerData(this);
        }
    }

    public CreditInfo getCreditInfo() {
        return creditInfo;
    }

    public void setCreditInfo(CreditInfo creditInfo) {
        this.creditInfo = creditInfo;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public List<CustomerBanHistory> getHistory() {
        return history;
    }

    public void setHistory(List<CustomerBanHistory> history) {
        this.history = history;
    }

    public void addHistory(CustomerBanHistory customerHistory) {
        if (history == null)
            history = new ArrayList<>();

        if (customerHistory != null) {
            history.add(customerHistory);
            if (customerHistory.getCustomer() != this)
                customerHistory.setCustomer(this);
        }
    }

    public List<QCR> getQcrList() {
        return qcrList;
    }

    public void setQcrList(List<QCR> qcrList) {
        this.qcrList = qcrList;
    }

    public void addComplaints(QCR qcr) {
        if (this.qcrList == null)
            this.qcrList = new ArrayList<>();

        if (qcr != null) {
            this.qcrList.add(qcr);

            if (qcr.getCustomer() != this)
                qcr.setCustomer(this);
        }
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public Particular getParticular() {
        return particular;
    }

    public void setParticular(Particular particular) {
        this.particular = particular;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }
}
