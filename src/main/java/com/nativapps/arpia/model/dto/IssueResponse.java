package com.nativapps.arpia.model.dto;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class IssueResponse extends IssueData {

    private Long id;

    public IssueResponse() {
    }

    public IssueResponse(Long id, String name, String shortName) {
        super(name, shortName);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
