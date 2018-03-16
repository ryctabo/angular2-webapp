package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1
 */
@Entity
@Table(name = "EGRESS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ETYPE")
public class Egress implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "ASSIGNED_BY", nullable = false)
    private User assignedBy;

    @Column(name = "`VALUE`", nullable = false)
    private double value;

    @Column(name = "CONCEPT", columnDefinition = "TEXT", nullable = false)
    private String concept;

    @Column(name = "ETYPE", insertable = false, updatable = false)
    private String type;

    @Column(name = "CREATED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLOSURE_ID")
    private Closure closure;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Closure getClosure() {
        return closure;
    }

    public void setClosure(Closure closure) {
        this.closure = closure;
    }
}
