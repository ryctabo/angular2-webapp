package com.nativapps.arpia.model.dto;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public class ShortcutResponse extends ShortcutData {

    private Long id;

    public ShortcutResponse() { }

    public ShortcutResponse(Long id, String key, String command, String when, String description) {
        super(key, command, when, description);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
