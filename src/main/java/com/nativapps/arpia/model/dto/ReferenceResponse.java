package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Relationship;
import com.nativapps.arpia.database.entity.TypeReference;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 2.0.1
 */
public class ReferenceResponse extends ReferenceData {

    private Integer index;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
