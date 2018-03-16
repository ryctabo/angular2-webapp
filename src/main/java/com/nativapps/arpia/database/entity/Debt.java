package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1
 */
@Entity
@Table(name = "DEBT")
public class Debt implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "`VALUE`", nullable = false)
    private double value;

    @Column(name = "CONCEPT", columnDefinition = "TEXT", nullable = false)
    private String concept;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FEE_INFO_ID", nullable = false)
    private FeeInfo feeInfo;

    @ManyToOne
    @JoinColumn(name = "OWNER", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "debt", cascade = CascadeType.ALL)
    private Set<Payment> payments;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLOSURE_ID")
    private Closure closure;

    @Column(name = "CREATED", nullable = false, updatable = false)
    private Calendar created;

    public Debt() {
        this.payments = new HashSet<>();
    }

    public Debt(double value, String concept, FeeInfo feeInfo, User owner, Calendar created) {
        this.value = value;
        this.concept = concept;
        this.feeInfo = feeInfo;
        this.owner = owner;
        this.payments = new HashSet<>();
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public FeeInfo getFeeInfo() {
        return feeInfo;
    }

    public void setFeeInfo(FeeInfo feeInfo) {
        this.feeInfo = feeInfo;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Closure getClosure() {
        return closure;
    }

    public void setClosure(Closure closure) {
        this.closure = closure;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }
}
