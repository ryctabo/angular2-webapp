package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "`CLOSURE`")
public class Closure implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "CLOSURED_BY", nullable = false)
    private User closuredBy;

    @Column(name = "`TYPE`", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClosuredType type;

    @Column(name = "OBSERVATION", columnDefinition = "TEXT")
    private String observation;

    @Column(name = "`DATE`", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar date;

    public Closure() { }

    public Closure(User closuredBy, ClosuredType type, Calendar date) {
        this.closuredBy = closuredBy;
        this.type = type;
        this.date = date;
    }

    public Closure(User closuredBy, ClosuredType type, String observation, Calendar date) {
        this.closuredBy = closuredBy;
        this.type = type;
        this.observation = observation;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getClosuredBy() {
        return closuredBy;
    }

    public void setClosuredBy(User closuredBy) {
        this.closuredBy = closuredBy;
    }

    public ClosuredType getType() {
        return type;
    }

    public void setType(ClosuredType type) {
        this.type = type;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

}
