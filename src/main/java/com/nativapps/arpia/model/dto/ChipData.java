package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.ChipType;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@XmlDiscriminatorNode("ctype")
public class ChipData {

    protected ChipType type;

    public ChipType getType() {
        return type;
    }

    public void setType(ChipType type) {
        this.type = type;
    }
}
