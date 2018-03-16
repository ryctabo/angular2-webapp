package com.nativapps.arpia.model.dto;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class IssueDetailResponse extends IssueDetailData {

    private String name;
    
    private String shortName;

    public IssueDetailResponse() {
    }

    public IssueDetailResponse(Long type, String description, String name, 
            String shortName) {
        this.issueType = type;
        this.description = description;
        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
