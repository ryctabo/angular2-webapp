package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
@Entity
@Table(name = "DISCOUNT")
public class Discount implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "START_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar startDate;

    @Column(name = "END_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Calendar endDate;

    @Column(name = "PERCENT", nullable = false)
    private float percent;

    @Column(name = "USE_LIMIT")
    private int useLimit;

    @OneToMany(mappedBy = "discount")
    private Set<DomicileExecute> domiciles;

    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL)
    private Set<DiscountChangeLog> changes;

    public Discount() {
        this.changes = new HashSet<>();
        this.domiciles = new HashSet<>();
    }

    public Discount(long id) {
        this.id = id;
        this.changes = new HashSet<>();
        this.domiciles = new HashSet<>();
    }

    public Discount(String name, Calendar startDate, Calendar endDate,
                    float percent, int useLimit) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        this.useLimit = useLimit;
        this.changes = new HashSet<>();
        this.domiciles = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public int getUseLimit() {
        return useLimit;
    }

    public void setUseLimit(int useLimit) {
        this.useLimit = useLimit;
    }

    public Set<DiscountChangeLog> getChanges() {
        return changes;
    }

    public void setChanges(Set<DiscountChangeLog> changes) {
        this.changes = changes;
    }

    public Set<DomicileExecute> getDomiciles() {
        return domiciles;
    }

    public void setDomiciles(Set<DomicileExecute> domiciles) {
        this.domiciles = domiciles;
    }

}
