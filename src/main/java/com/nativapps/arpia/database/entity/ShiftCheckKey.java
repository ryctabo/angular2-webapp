package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "SHIFT_CHECK_KEY")
@NamedQueries({
        @NamedQuery(name = "shiftCheckKey.find",
                query = "SELECT k FROM ShiftCheckKey k " +
                        "WHERE k.check.id = :checkId AND k.key = :skey")
})
public class ShiftCheckKey implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "`KEY`")
    private String key;

    @Column(name = "CREATION_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar creationDate;

    @Column(name = "USE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar useDate;

    @ManyToOne
    @JoinColumn(name = "SHIFT_CHECK_ID")
    private ShiftCheck check;

    public ShiftCheckKey() {
    }

    public ShiftCheckKey(String key, Calendar creationDate, ShiftCheck check) {
        this.key = key;
        this.creationDate = creationDate;
        this.check = check;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public Calendar getUseDate() {
        return useDate;
    }

    public void setUseDate(Calendar useDate) {
        this.useDate = useDate;
    }

    public ShiftCheck getCheck() {
        return check;
    }

    public void setCheck(ShiftCheck check) {
        this.check = check;
    }
}
