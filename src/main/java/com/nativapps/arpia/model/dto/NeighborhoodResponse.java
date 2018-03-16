package com.nativapps.arpia.model.dto;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class NeighborhoodResponse extends NeighborhoodData {

    private Long id;

    public NeighborhoodResponse() { }

    public NeighborhoodResponse(Long id, String name) {
        super(name);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
