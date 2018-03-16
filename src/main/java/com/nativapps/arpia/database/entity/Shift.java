package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "SHIFT")
@NamedQueries({
    @NamedQuery(name = "shift.findAllByShiftplanningId",
            query = "SELECT s FROM Shift s WHERE s.shiftplanning.id = :shiftPId"),
    @NamedQuery(name = "shift.count",
            query = "SELECT COUNT(s) FROM Shift s "
                    + "WHERE s.shiftplanning.id = :shiftPId")
})
public class Shift implements Serializable {

    @EmbeddedId
    private ShiftPK id;
    
    @Column(name = "START_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar startTime;
    
    @Column(name = "END_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar endTime;
    
    @Column(name = "COUNT", nullable = false)
    private int count;
    
    @MapsId("shiftplanningId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHIFTPLANNING_ID", nullable = false)
    private Shiftplanning shiftplanning;
    
    @OneToMany(mappedBy = "id.shift")
    private List<ShiftAssignment> assignments;

    public Shift() {
        this.id = new ShiftPK();
    }

    public Shift(int index, long shiftplanningId) {
        this.id = new ShiftPK(index, shiftplanningId);
    }

    public ShiftPK getId() {
        return id;
    }

    public void setId(ShiftPK id) {
        this.id = id;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shiftplanning getShiftplanning() {
        return shiftplanning;
    }

    public void setShiftplanning(Shiftplanning shiftplanning) {
        this.shiftplanning = shiftplanning;
    }

    public List<ShiftAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<ShiftAssignment> assignments) {
        this.assignments = assignments;
    }
    
    public void addAssignment(ShiftAssignment assignment) {
        if (this.assignments == null)
            this.assignments = new ArrayList<>();
        
        if (assignment != null) {
            this.assignments.add(assignment);
            
            if (assignment.getId().getShift() != this)
                assignment.getId().setShift(this);
        }
    }
    
    @Embeddable
    public static class ShiftPK implements Serializable {
        
        @Column(name = "`INDEX`", nullable = false)
        private int index;
        
        @Column(name = "SHIFTPLANNING_ID")
        private long shiftplanningId;

        public ShiftPK() {
        }

        public ShiftPK(int index, long shiftplanningId) {
            this.index = index;
            this.shiftplanningId = shiftplanningId;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public long getShiftplanningId() {
            return shiftplanningId;
        }

        public void setShiftplanningId(long shiftplanningId) {
            this.shiftplanningId = shiftplanningId;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + this.index;
            hash = 67 * hash + (int) (this.shiftplanningId ^ (this.shiftplanningId >>> 32));
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final ShiftPK other = (ShiftPK) obj;
            if (this.index != other.index)
                return false;
            return this.shiftplanningId == other.shiftplanningId;
        }
    }
}
