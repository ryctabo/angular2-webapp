package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.DomicileType;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0.2
 */
@XmlDiscriminatorNode("otype")
public class DomicileData {

    protected Long customer;

    protected Long operation;

    protected Float price;

    protected Float messengerGain;

    protected DomicileType type;

    protected DomicileRatingData rating;

    protected DomicileRequirementData requirements;

    protected RepeatData periodicity;

    public Long getCustomer() {
        return customer;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public Long getOperation() {
        return operation;
    }

    public void setOperation(Long operation) {
        this.operation = operation;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getMessengerGain() {
        return messengerGain;
    }

    public void setMessengerGain(Float messengerGain) {
        this.messengerGain = messengerGain;
    }

    public DomicileType getType() {
        return type;
    }

    public void setType(DomicileType type) {
        this.type = type;
    }

    public DomicileRatingData getRating() {
        return rating;
    }

    public void setRating(DomicileRatingData rating) {
        this.rating = rating;
    }

    public DomicileRequirementData getRequirements() {
        return requirements;
    }

    public void setRequirements(DomicileRequirementData requirements) {
        this.requirements = requirements;
    }

    public RepeatData getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(RepeatData periodicity) {
        this.periodicity = periodicity;
    }
}
