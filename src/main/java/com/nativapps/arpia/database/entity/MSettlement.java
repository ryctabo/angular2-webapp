package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "MESSENGER_SETTLEMENT")
public class MSettlement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "OBSERVATIONS")
    private String observations;

    @Column(name = "REGISTER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registerDate;

    @OneToOne
    @JoinColumn(name = "CHECK_ID")
    private ShiftCheck check;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public ShiftCheck getCheck() {
        return check;
    }

    public void setCheck(ShiftCheck check) {
        this.check = check;
    }
}
