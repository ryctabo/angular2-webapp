package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "SHIFT_ASSIGNMENT")
@NamedQueries({
    @NamedQuery(name = "shift_assign.findAllByShiftId",
            query = "SELECT a FROM ShiftAssignment a "
                    + "WHERE a.id.shift.id.index = :index "
                    + "AND a.id.shift.id.shiftplanningId = :shiftPId"),
    @NamedQuery(name = "shift_assign.countByShiftId",
            query = "SELECT COUNT(a) FROM ShiftAssignment a "
                    + "WHERE a.id.shift.id.index = :index "
                    + "AND a.id.shift.id.shiftplanningId = :shiftPId")
})
public class ShiftAssignment implements Serializable {
    
    @EmbeddedId
    private ShiftAssignmentPK id;

    public ShiftAssignment() {
        this.id = new ShiftAssignmentPK();
    }
    
    public ShiftAssignment(Messenger messenger, Shift shift) {
        this.id = new ShiftAssignmentPK(messenger, shift);
    }

    public ShiftAssignmentPK getId() {
        return id;
    }

    public void setId(ShiftAssignmentPK id) {
        this.id = id;
    }
    
    @Embeddable
    public static class ShiftAssignmentPK implements Serializable {
        
        @ManyToOne
        @JoinColumn(name = "MESSENGER_ID", nullable = false)
        private Messenger messenger; 
        
        @ManyToOne
        @JoinColumns({
            @JoinColumn(name = "SHIFT_INDEX", referencedColumnName = "`INDEX`"),
            @JoinColumn(name = "SHIFTPLANNING_ID", 
                    referencedColumnName = "SHIFTPLANNING_ID")
        })
        private Shift shift;

        public ShiftAssignmentPK() {
        }

        public ShiftAssignmentPK(Messenger messenger, Shift shift) {
            this.messenger = messenger;
            this.shift = shift;
        }

        public Messenger getMessenger() {
            return messenger;
        }

        public void setMessenger(Messenger messenger) {
            this.messenger = messenger;
        }

        public Shift getShift() {
            return shift;
        }

        public void setShift(Shift shift) {
            this.shift = shift;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + Objects.hashCode(this.messenger);
            hash = 17 * hash + Objects.hashCode(this.shift);
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
            final ShiftAssignmentPK other = (ShiftAssignmentPK) obj;
            if (!Objects.equals(this.messenger, other.messenger))
                return false;
            if (!Objects.equals(this.shift, other.shift))
                return false;
            return true;
        }
    }
}
