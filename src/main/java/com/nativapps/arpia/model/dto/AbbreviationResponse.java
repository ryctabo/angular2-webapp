package com.nativapps.arpia.model.dto;

/**
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class AbbreviationResponse extends AbbreviationData {

    private Long id;

    public AbbreviationResponse() { }

    public AbbreviationResponse(Long id, String shortText, String content) {
        super(shortText, content);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
