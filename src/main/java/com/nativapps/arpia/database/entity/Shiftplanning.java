package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "SHIFTPLANNING")
@NamedQueries({
    @NamedQuery(name = "shiftplanning.findByDate", 
            query = "SELECT s FROM Shiftplanning s WHERE s.initialDate = :date")
})
public class Shiftplanning implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "INITIAL_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar initialDate;
    
    @Column(name = "FINAL_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar finalDate;
    
    @ManyToOne
    @JoinColumn(name = "OPERATION_ID", nullable = false)
    private Operation operation;
    
    @OneToMany(mappedBy = "shiftplanning", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<Shift> shifts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Calendar initialDate) {
        this.initialDate = initialDate;
    }

    public Calendar getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Calendar finalDate) {
        this.finalDate = finalDate;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }
    
    public void addShift(Shift shift) {
        if (this.shifts == null)
            this.shifts = new ArrayList<>();
        
        if (shift != null) {
            this.shifts.add(shift);
            
            if (shift.getShiftplanning() != this)
                shift.setShiftplanning(this);
        }
    }
}
