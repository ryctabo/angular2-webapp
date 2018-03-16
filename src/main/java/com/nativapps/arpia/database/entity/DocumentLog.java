package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "DOCUMENT_LOG")
public class DocumentLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "URL")
    private String url;
    
    @Column(name = "REGISTER_DATE")
    @Temporal(TemporalType.DATE)
    private Calendar registerDate;
    
    @ManyToOne
    @JoinColumn(name = "DOCUMENT_ID")
    private Document document;

    public DocumentLog() {
    }
    
    public DocumentLog(String url, Calendar registerDate) {
        this.url = url;
        this.registerDate = registerDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
