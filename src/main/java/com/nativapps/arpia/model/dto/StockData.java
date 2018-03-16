package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.StockType;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
@XmlDiscriminatorNode("stype")
public class StockData {

    private Integer count;

    private StockType type;

    private String reason;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public StockType getType() {
        return type;
    }

    public void setType(StockType type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
