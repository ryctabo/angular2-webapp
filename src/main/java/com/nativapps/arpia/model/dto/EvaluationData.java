package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Confidence;
import com.nativapps.arpia.database.entity.Management;
import com.nativapps.arpia.database.entity.Speed;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1
 */
@XmlDiscriminatorNode("type")
public class EvaluationData {

    private String detail;

    private Speed speed;

    private Management management;

    private Confidence confidence;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public Management getManagement() {
        return management;
    }

    public void setManagement(Management management) {
        this.management = management;
    }

    public Confidence getConfidence() {
        return confidence;
    }

    public void setConfidence(Confidence confidence) {
        this.confidence = confidence;
    }
}
