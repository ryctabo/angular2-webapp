package com.nativapps.arpia.database.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 2.7.0
 */
@Entity
@Table(name = "MESSENGER")
@PrimaryKeyJoinColumn(referencedColumnName = "PERSON_ID")
@NamedQueries({
    @NamedQuery(name = "messenger.findAll",
            query = "SELECT m FROM Messenger m"),
    @NamedQuery(name = "messenger.count",
            query = "SELECT COUNT(m) FROM Messenger m")
})
public class Messenger extends Person {

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "USER_ID", insertable = false, updatable = false)
    private long userId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RELIABILITY_ID")
    private Reliability reliability;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRICULUM_VITAE_ID")
    private CurriculumVitae curriculumVitae;

    @Column(name = "CURRICULUM_VITAE_ID", insertable = false, updatable = false)
    private long curriculumVitaeId;

    @OneToOne(mappedBy = "messenger")
    private MobileNumber mobile;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "messenger", cascade = CascadeType.ALL)
    private List<MessengerAction> actions;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "messenger")
    private List<Vehicle> vehicles;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "messenger", cascade = CascadeType.ALL)
    private List<Fault> faults;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "messenger", cascade = CascadeType.ALL)
    private List<Disease> diseases;

    @Column(name = "DISMISSAL", nullable = false)
    private boolean dismissal;

    @Column(name = "OBSERVATION", columnDefinition = "TEXT")
    private String observations;

    @Column(name = "PHOTO", nullable = false)
    private String photo;

    @Column(name = "CATEGORY")
    private int category;
    
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "id.messenger")
    private List<ShiftAssignment> assignments;
    
    @Column(name = "RETREAT")
    private boolean retire;

    public Messenger() {
        this.reliability = new Reliability();
    }

    public Messenger(long id) {
        super(id);
        this.reliability = new Reliability();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Reliability getReliability() {
        return reliability;
    }

    public void setReliability(Reliability reliability) {
        this.reliability = reliability;
    }

    public CurriculumVitae getCurriculumVitae() {
        return curriculumVitae;
    }

    public void setCurriculumVitae(CurriculumVitae curriculumVitae) {
        this.curriculumVitae = curriculumVitae;
    }

    public long getCurriculumVitaeId() {
        return curriculumVitaeId;
    }

    public void setCurriculumVitaeId(long curriculumVitaeId) {
        this.curriculumVitaeId = curriculumVitaeId;
    }

    public MobileNumber getMobile() {
        return mobile;
    }

    public void setMobile(MobileNumber mobile) {
        this.mobile = mobile;
    }

    public List<MessengerAction> getActions() {
        return actions;
    }

    public void setActions(List<MessengerAction> actions) {
        this.actions = actions;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Fault> getFaults() {
        return faults;
    }

    public void setFaults(List<Fault> faults) {
        this.faults = faults;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }

    public boolean isDismissal() {
        return dismissal;
    }

    public void setDismissal(boolean dismissal) {
        this.dismissal = dismissal;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public List<ShiftAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<ShiftAssignment> assignments) {
        this.assignments = assignments;
    }

    public boolean isRetire() {
        return retire;
    }

    public void setRetire(boolean retire) {
        this.retire = retire;
    }
}
