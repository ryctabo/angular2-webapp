package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.2.0
 */
@Entity
@Table(name = "`OPERATION`")
@NamedQueries({
    @NamedQuery(name = "operation.findByName",
            query = "select o from Operation o where o.name = :name")
    ,
    @NamedQuery(name = "operation.findByAlias",
            query = "select o from Operation o where o.alias = :alias")
})
public class Operation implements Serializable {

    @Id
    @Column(name = "OPERATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "`NAME`", nullable = false, length = 30, unique = true)
    private String name;

    @Column(name = "`ALIAS`", nullable = false, length = 4, unique = true)
    private String alias;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "LOGO_IMAGE_URL")
    private String picUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADMIN_ID")
    private User adminUser;

    @OneToMany(mappedBy = "operation")
    private Set<MobileNumber> mobileNumbers;

    public Operation() {
        this.mobileNumbers = new HashSet<>();
    }

    public Operation(long id) {
        this.id = id;
        this.mobileNumbers = new HashSet<>();
    }

    public Operation(String name, String alias, String description,
            String picUrl) {
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.picUrl = picUrl;
        this.mobileNumbers = new HashSet<>();
    }

    public Operation(long id, String name, String alias, String description,
            String picUrl) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.picUrl = picUrl;
        this.mobileNumbers = new HashSet<>();
    }

    public Operation(long id, String name, String alias, String description,
            String picUrl, User adminUser) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.picUrl = picUrl;
        this.adminUser = adminUser;
        this.mobileNumbers = new HashSet<>();
    }

    public Operation(long id, String name, String alias, String description,
            String picUrl, User adminUser, Set<MobileNumber> mobileNumbers) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.picUrl = picUrl;
        this.adminUser = adminUser;
        this.mobileNumbers = mobileNumbers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public User getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(User adminUser) {
        this.adminUser = adminUser;
    }

    public boolean addMobileNumber(MobileNumber mobileNumber) {
        if (mobileNumber.getOperation() != this)
            mobileNumber.setOperation(this);
        return this.mobileNumbers.add(mobileNumber);
    }

    public Set<MobileNumber> getMobileNumbers() {
        return mobileNumbers;
    }

    public void setMobileNumbers(Set<MobileNumber> mobileNumbers) {
        this.mobileNumbers = mobileNumbers;
    }

}
