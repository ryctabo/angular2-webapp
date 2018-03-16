package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
@Entity
@Table(name = "SHORTCUT")
@NamedQueries({
        @NamedQuery(name = "shortcut.findByKey",
                query = "SELECT s FROM Shortcut s WHERE s.key = :key")
})
public class Shortcut implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`KEY`", unique = true, nullable = false)
    private String key;

    @Column(name = "COMMAND", nullable = false)
    private String command;

    @Column(name = "`WHEN`", nullable = false)
    private String when;

    @Column(name = "DESCRIPTION")
    private String description;

    public Shortcut() {
    }

    public Shortcut(String key, String command, String when, String description) {
        this.key = key;
        this.command = command;
        this.when = when;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
