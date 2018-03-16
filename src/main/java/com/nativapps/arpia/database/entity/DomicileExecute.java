package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 2.1.2
 */
@Entity
@Table(name = "HOME_SERVICE_EXECUTE")
@NamedQueries({
        @NamedQuery(name = "execute.findByCustomerId",
                query = "SELECT de FROM DomicileExecute de " +
                        "JOIN de.domicile d WHERE d.customerId = :id"),
        @NamedQuery(name = "execute.findByMessengerId",
                query = "SELECT de FROM DomicileExecute de " +
                        "JOIN de.assignments a " +
                        "WHERE a.messenger.id = :messengerId " +
                        "AND (de.finishDate BETWEEN :startDate AND :endDate)")
})
public class DomicileExecute implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "HOME_SERVICE_ID", nullable = false)
    private Domicile domicile;

    @ManyToOne
    @JoinColumn(name = "DISCOUNT_ID")
    private Discount discount;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "domicile")
    private Set<Assignment> assignments;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private DomicileStatus status;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "AUTOMATIC_ASSIGNMENT", nullable = false)
    private boolean automatic;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar startDate;

    @Column(name = "FINISH_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar finishDate;

    @Column(name = "CANCEL_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar cancelDate;

    @Column(name = "CANCEL_REASON", columnDefinition = "TEXT")
    private String cancelReason;

    public DomicileExecute() {
        this.assignments = new HashSet<>();
    }

    public DomicileExecute(long id) {
        this.id = id;
        this.assignments = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Domicile getDomicile() {
        return domicile;
    }

    public void setDomicile(Domicile domicile) {
        this.domicile = domicile;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Set<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<Assignment> asignments) {
        this.assignments = asignments;
    }

    public DomicileStatus getStatus() {
        return status;
    }

    public void setStatus(DomicileStatus status) {
        this.status = status;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Calendar finishDate) {
        this.finishDate = finishDate;
    }

    public Calendar getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Calendar cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

}
