package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Complexity;
import com.nativapps.arpia.database.entity.Priority;
import com.nativapps.arpia.database.entity.Velocity;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class DomicileRatingData {

    private Integer trustLevel;

    private Priority priority;

    private Complexity complexity;

    private Velocity velocity;

    public DomicileRatingData() { }

    public DomicileRatingData(Integer trustLevel, Priority priority,
                              Complexity complexity, Velocity velocity) {
        this.trustLevel = trustLevel;
        this.priority = priority;
        this.complexity = complexity;
        this.velocity = velocity;
    }

    public Integer getTrustLevel() {
        return trustLevel;
    }

    public void setTrustLevel(Integer trustLevel) {
        this.trustLevel = trustLevel;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Complexity getComplexity() {
        return complexity;
    }

    public void setComplexity(Complexity complexity) {
        this.complexity = complexity;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }
}
