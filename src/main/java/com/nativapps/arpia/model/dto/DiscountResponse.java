package com.nativapps.arpia.model.dto;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class DiscountResponse extends DiscountData {

    private Long id;

    private Integer countUsed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCountUsed() {
        return countUsed;
    }

    public void setCountUsed(Integer countUsed) {
        this.countUsed = countUsed;
    }

}
