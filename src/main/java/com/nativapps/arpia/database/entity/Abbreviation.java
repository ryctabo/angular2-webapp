package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
@Entity
@Table(name = "ABBREVIATION")
@NamedQueries({
        @NamedQuery(name = "abbreviation.findByShortText",
                query = "select a from Abbreviation a where a.shortText = :shortText")
})
public class Abbreviation implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "SHORT_TEXT", length = 5, unique = true, nullable = false)
    private String shortText;

    @Column(name = "CONTENT", length = 200, nullable = false)
    private String content;

    public Abbreviation() { }

    public Abbreviation(String shortText, String content) {
        this.shortText = shortText;
        this.content = content;
    }

    public Abbreviation(long id, String shortText, String content) {
        this.id = id;
        this.shortText = shortText;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String code) {
        this.shortText = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String description) {
        this.content = description;
    }

}
