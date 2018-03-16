package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.QCR;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
@XmlDiscriminatorNode("type")
public class QCRData {

    protected String inconvenient;

    protected String counterpartVersion;

    protected String evaluation;

    protected String solution;

    protected QCR.Status status;

    public String getInconvenient() {
        return inconvenient;
    }

    public void setInconvenient(String inconvenient) {
        this.inconvenient = inconvenient;
    }

    public String getCounterpartVersion() {
        return counterpartVersion;
    }

    public void setCounterpartVersion(String counterpartVersion) {
        this.counterpartVersion = counterpartVersion;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public QCR.Status getStatus() {
        return status;
    }

    public void setStatus(QCR.Status status) {
        this.status = status;
    }
}
