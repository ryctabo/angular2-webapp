package com.nativapps.arpia.model.dto;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class IssueDetailData {
    
    protected String description;
    
    protected Long issueType;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIssueType() {
        return issueType;
    }

    public void setIssueType(Long issueType) {
        this.issueType = issueType;
    }
}
