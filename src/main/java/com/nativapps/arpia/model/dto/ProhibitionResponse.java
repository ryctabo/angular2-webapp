package com.nativapps.arpia.model.dto;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 *
 * @version 1.0
 */
public class ProhibitionResponse extends ProhibitionData {

    private Long id;

    public ProhibitionResponse() {
    }

    public ProhibitionResponse(Long id, Boolean document, Boolean shopping,
            Boolean rent, Boolean money, Boolean liqueur, Boolean food,
            Boolean soat, Boolean bank) {
        super(document, shopping, rent, money, liqueur, food, soat, bank);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
