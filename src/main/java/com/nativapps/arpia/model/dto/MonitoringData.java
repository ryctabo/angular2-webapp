package com.nativapps.arpia.model.dto;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class MonitoringData {

    protected Long domicileId;

    protected String content;

    public MonitoringData() {
    }

    public MonitoringData(Long domicileId, String content) {
        this.domicileId = domicileId;
        this.content = content;
    }

    public Long getDomicileId() {
        return domicileId;
    }

    public void setDomicileId(Long domicileId) {
        this.domicileId = domicileId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
