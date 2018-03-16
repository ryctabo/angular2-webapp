package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "MESSENGER_EVALUATION")
public class Evaluation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "MESSENGER_ID")
    private Messenger messenger;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "REGISTER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registerDate;

    @Column(name = "DETAIL", columnDefinition = "TEXT")
    private String detail;

    @Column(name = "SPEED")
    @Enumerated(EnumType.STRING)
    private Speed speed;

    @Column(name = "MANAGEMENT")
    @Enumerated(EnumType.STRING)
    private Management management;

    @Column(name = "CONFIDENCE")
    @Enumerated(EnumType.STRING)
    private Confidence confidence;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public Management getManagement() {
        return management;
    }

    public void setManagement(Management management) {
        this.management = management;
    }

    public Confidence getConfidence() {
        return confidence;
    }

    public void setConfidence(Confidence confidence) {
        this.confidence = confidence;
    }
}
