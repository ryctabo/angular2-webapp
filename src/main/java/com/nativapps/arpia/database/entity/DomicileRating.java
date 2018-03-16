package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "HOME_SERVICE_RATING")
public class DomicileRating implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "TRUST_LEVEL", nullable = false)
    private int trustLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRIORITY", nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "COMPLEXITY", nullable = false)
    private Complexity complexity;

    @Enumerated(EnumType.STRING)
    @Column(name = "VELOCITY", nullable = false)
    private Velocity velocity;

    public DomicileRating() { }

    public DomicileRating(int trustLevel, Priority priority, Complexity complexity, Velocity velocity) {
        this.trustLevel = trustLevel;
        this.priority = priority;
        this.complexity = complexity;
        this.velocity = velocity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTrustLevel() {
        return trustLevel;
    }

    public void setTrustLevel(int trustLevel) {
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
