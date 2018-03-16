package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "ADMINISTRATOR")
@PrimaryKeyJoinColumn(referencedColumnName = "PERSON_ID")
@NamedQueries({
    @NamedQuery(name = "administrator.findByEstablishmentId",
            query = "SELECT a FROM Administrator a WHERE "
            + "a.establishment.id = :establishmentId AND "
            + "a.id = :administratorId")
    ,
    @NamedQuery(name = "administrator.findAllByEstablishmentId",
            query = "SELECT a FROM Administrator a WHERE "
            + "a.establishment.id = :establishmentId"),
    @NamedQuery(name = "administrator.findByIdentification",
            query = "SELECT a FROM Administrator a "
                    + "WHERE a.identification = :identification")
})
public class Administrator implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "IDENTIFICATION", length = 15, unique = true)
    private String identification;

    @Column(name = "`NAME`", length = 60, nullable = false)
    private String name;

    @Column(name = "LAST_NAME", nullable = false, length = 50)
    private String lastName;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "BIRTHDAY")
    @Temporal(TemporalType.DATE)
    private Calendar birthday;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "OBSERVATIONS", columnDefinition = "TEXT")
    private String observations;

    @Column(name = "LINKED", nullable = false)
    private boolean linked;

    @Column(name = "NDAY_REPORT")
    private Integer nDayReport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTABLISHMENT_ID", nullable = false)
    private Establishment establishment;

    public Administrator() {
    }

    public Administrator(String identification, String name, String lastName,
            Gender gender, String observations, boolean linked) {
        this.identification = identification;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.observations = observations;
        this.linked = linked;
    }

    public Administrator(String identification, String name, String lastName,
            Gender gender, Calendar birthday, String address, String email,
            String phone, String observations, boolean linked, Integer nDayReport) {
        this.identification = identification;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.observations = observations;
        this.linked = linked;
        this.nDayReport = nDayReport;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    public Integer getnDayReport() {
        return nDayReport;
    }

    public void setnDayReport(Integer nDayReport) {
        this.nDayReport = nDayReport;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;

        if (!establishment.getAdministrators().contains(this)) {
            establishment.addAdministrator(this);
        }
    }
}
