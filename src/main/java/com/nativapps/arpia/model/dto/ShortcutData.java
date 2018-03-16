package com.nativapps.arpia.model.dto;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
@XmlDiscriminatorNode("type")
public class ShortcutData {

    private String key;

    private String command;

    private String when;

    private String description;

    public ShortcutData() { }

    public ShortcutData(String key, String command, String when, String description) {
        this.key = key;
        this.command = command;
        this.when = when;
        this.description = description;
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
