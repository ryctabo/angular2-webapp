package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "ISSUE_DETAIL")
public class IssueDetail implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "ISSUE_ID")
    private Issue type;

    public IssueDetail() {
    }

    public IssueDetail(String description, Issue issue) {
        this.description = description;
        this.type = issue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Issue getType() {
        return type;
    }

    public void setType(Issue type) {
        this.type = type;
    }
}
