package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "SHIFT_CHECK")
@NamedQueries({
    @NamedQuery(name = "shift_check.findByAssignId",
            query = "SELECT c FROM ShiftCheck c "
                    + "WHERE c.assignment.id.messenger.id = :messengerId "
                    + "AND c.assignment.id.shift.id.shiftplanningId = :shiftPId "
                    + "AND c.assignment.id.shift.id.index = :index")
})
public class ShiftCheck implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "CLOCK_IN", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar clockIn;
    
    @Column(name = "CLOCK_OUT")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar clockOut;
    
    @Column(name = "GOOD_APPEARANCE", nullable = false)
    private boolean goodAppearance;
    
    @Column(name = "OBSERVATIONS")
    private String observations;
    
    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "MESSENGER_ID", referencedColumnName = "MESSENGER_ID", 
                nullable = false),
        @JoinColumn(name = "SHIFTPLANNING_ID", 
                referencedColumnName = "SHIFTPLANNING_ID", nullable = false),
        @JoinColumn(name = "SHIFT_INDEX", referencedColumnName = "SHIFT_INDEX",
                nullable = false)
    })
    private ShiftAssignment assignment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getClockIn() {
        return clockIn;
    }

    public void setClockIn(Calendar clockIn) {
        this.clockIn = clockIn;
    }

    public Calendar getClockOut() {
        return clockOut;
    }

    public void setClockOut(Calendar clockOut) {
        this.clockOut = clockOut;
    }

    public boolean isGoodAppearance() {
        return goodAppearance;
    }

    public void setGoodAppearance(boolean goodAppearance) {
        this.goodAppearance = goodAppearance;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public ShiftAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(ShiftAssignment assignment) {
        this.assignment = assignment;
    }
}
