package com.nativapps.arpia.model.dto;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 *
 * @version 1.0
 */
public class ProhibitionRequest extends ProhibitionData {

    public ProhibitionRequest() {
    }

    public ProhibitionRequest(Boolean document, Boolean shopping, Boolean rent,
            Boolean money, Boolean liqueur, Boolean food, Boolean soat,
            Boolean bank) {
        super(document, shopping, rent, money, liqueur, food, soat, bank);
    }

}
