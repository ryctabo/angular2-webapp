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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "DOCUMENT")
@NamedQueries({
    @NamedQuery(name = "document.findByName",
            query = "SELECT d FROM Document d WHERE d.name = :name"),
    @NamedQuery(name = "document.findByUrl",
            query = "SELECT d FROM Document d WHERE d.url = :url")
})
public class Document implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "NAME", unique = true, nullable = false)
    private String name;
    
    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "URL", unique = true, nullable = false)
    private String url;
    
    @Column(name = "EXPIRATION_DATE")
    @Temporal(TemporalType.DATE)
    private Calendar expirationDate;
    
    @Column(name = "VISIBLE")
    private boolean visible;
    
    @OneToMany(mappedBy = "document", fetch = FetchType.EAGER, 
            cascade = CascadeType.ALL)
    private List<DocumentLog> log;

    public Document() {
    }

    public Document(String name, String description, String url, 
            Calendar expirationDate, boolean visible) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.expirationDate = expirationDate;
        this.visible = visible;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<DocumentLog> getLog() {
        return log;
    }

    public void setLog(List<DocumentLog> log) {
        this.log = log;
    }
    
    public void addLog(DocumentLog documentLog) {
        if (this.log == null)
            log = new ArrayList<>();
        
        if (documentLog != null) {
            log.add(documentLog);
            
            if (documentLog.getDocument() != this)
                documentLog.setDocument(this);
        }
    }

    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }
}
