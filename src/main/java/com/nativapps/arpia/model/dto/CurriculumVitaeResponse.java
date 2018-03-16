package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.AcademicLevel;
import com.nativapps.arpia.database.entity.CivilStatus;
import java.util.List;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 2.1.0
 */
public class CurriculumVitaeResponse extends CurriculumVitaeData {

    private Long id;

    private List<ReferenceResponse> references;

    public CurriculumVitaeResponse() {
    }

    public CurriculumVitaeResponse(Long id, List<ReferenceResponse> references,
            SocialSecurityData socialSecurity, HomeData home,
            CivilStatus civilStatus, String birthCity,
            AcademicLevel academicLevel, String militaryCard, String district) {
        super(socialSecurity, home, civilStatus, birthCity, academicLevel,
                militaryCard, district);
        this.id = id;
        this.references = references;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ReferenceResponse> getReferences() {
        return references;
    }

    public void setReferences(List<ReferenceResponse> references) {
        this.references = references;
    }

}
