package com.nativapps.arpia.model.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class DocumentResponse extends DocumentData {

    private Long id;
    
    private List<DocumentLogData> log;

    public DocumentResponse() {
        this.log = new ArrayList<>();
    }

    public DocumentResponse(Long id, String name, String description, 
            String url, Calendar expirationDate, Boolean visible) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.expirationDate = expirationDate;
        this.visible = visible;
        this.log = new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DocumentLogData> getLog() {
        return log;
    }

    public void setLog(List<DocumentLogData> log) {
        this.log = log;
    }
}
