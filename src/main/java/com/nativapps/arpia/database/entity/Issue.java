package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "ISSUE")
@NamedQueries({
    @NamedQuery(name = "issue.findAll", query = "SELECT i FROM Issue i"),
    @NamedQuery(name = "issue.findAllCount", 
            query = "SELECT COUNT(i) FROM Issue i")
})
public class Issue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @Column(name = "SHORT_NAME", length = 3)
    private String shortName;

    public Issue() {
    }

    public Issue(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
