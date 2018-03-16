package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 3.0
 */
@Entity
@Table(name = "REFERENCE")
public class Reference implements Serializable {

    @EmbeddedId
    private ReferencePK id;

    @Column(name = "`NAME`", length = 60, nullable = false)
    private String name;

    @Column(name = "LAST_NAME", length = 50, nullable = false)
    private String lastName;

    @Column(name = "ADDRESS", length = 100, nullable = false)
    private String address;

    @Column(name = "PHONE", length = 12, nullable = false)
    private String phone;

    @Column(name = "TYPE_REFERENCE", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeReference type;

    @Column(name = "RELATIONSHIP", nullable = false)
    @Enumerated(EnumType.STRING)
    private Relationship relationship;

    @MapsId("curriculumVitaeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRICULUM_VITAE_ID", nullable = false)
    private CurriculumVitae curriculumVitae;

    public Reference() {
        this.id = new ReferencePK();
    }

    public ReferencePK getId() {
        return id;
    }

    public void setId(ReferencePK id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TypeReference getType() {
        return type;
    }

    public void setType(TypeReference type) {
        this.type = type;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public CurriculumVitae getCurriculumVitae() {
        return curriculumVitae;
    }

    public void setCurriculumVitae(CurriculumVitae curriculumVitae) {
        this.curriculumVitae = curriculumVitae;
    }

    @Embeddable
    public static class ReferencePK implements Serializable {

        @Column(name = "`INDEX`", nullable = false)
        private int index;

        @Column(name = "CURRICULUM_VITAE_ID")
        private long curriculumVitaeId;

        public ReferencePK() {
        }

        public ReferencePK(int index, long curriculumVitaeId) {
            this.index = index;
            this.curriculumVitaeId = curriculumVitaeId;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public long getCurriculumVitaeId() {
            return curriculumVitaeId;
        }

        public void setCurriculumVitaeId(long curriculumVitaeId) {
            this.curriculumVitaeId = curriculumVitaeId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ReferencePK that = (ReferencePK) o;

            if (index != that.index) return false;
            return curriculumVitaeId == that.curriculumVitaeId;
        }

        @Override
        public int hashCode() {
            int result = index;
            result = 31 * result + (int) (curriculumVitaeId ^ (curriculumVitaeId >>> 32));
            return result;
        }
    }

}
