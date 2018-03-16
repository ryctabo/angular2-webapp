package com.nativapps.arpia.model.dto;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class MonitoringRequest extends MonitoringData {

    public MonitoringRequest() {
    }

    public MonitoringRequest(long domicileId, String content) {
        super(domicileId, content);
    }

}
