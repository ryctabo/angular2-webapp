package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0.1
 */
@Entity
@Table(name = "HOME_SERVICE")
@NamedQueries({
        @NamedQuery(name = "domicile.findByCustomerId",
                query = "SELECT d FROM Domicile d WHERE d.customerId = :id")
})
public class Domicile implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private CustomerData customer;

    @Column(name = "CUSTOMER_ID", insertable = false, updatable = false)
    private long customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATION_ID", nullable = false)
    private Operation operation;

    @Column(name = "OPERATION_ID", insertable = false, updatable = false)
    private long operationId;

    @Column(name = "PRICE", nullable = false)
    private float price;

    @Column(name = "MESSENGER_GAIN", nullable = false)
    private float messengerGain;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "domicile", fetch = FetchType.EAGER)
    private Set<Location> locations;

    @Enumerated(EnumType.STRING)
    @Column(name = "`TYPE`", nullable = false)
    private DomicileType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RATING_ID", nullable = false)
    private DomicileRating rating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REQUIREMENTS_ID", nullable = false)
    private DomicileRequirement requirements;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PERIODICITY_ID")
    private Repeat periodicity;

    @Column(name = "CREATE_DATE", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createDate;

    public Domicile() { }

    public Domicile(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CustomerData getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerData customer) {
        this.customer = customer;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public long getOperationId() {
        return operationId;
    }

    public void setOperationId(long operationId) {
        this.operationId = operationId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getMessengerGain() {
        return messengerGain;
    }

    public void setMessengerGain(float messengerGain) {
        this.messengerGain = messengerGain;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public DomicileType getType() {
        return type;
    }

    public void setType(DomicileType type) {
        this.type = type;
    }

    public DomicileRating getRating() {
        return rating;
    }

    public void setRating(DomicileRating rating) {
        this.rating = rating;
    }

    public DomicileRequirement getRequirements() {
        return requirements;
    }

    public void setRequirements(DomicileRequirement requirements) {
        this.requirements = requirements;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Repeat getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Repeat periodicity) {
        this.periodicity = periodicity;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }
}
