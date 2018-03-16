package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ReferenceRequest;
import com.nativapps.arpia.model.dto.ReferenceResponse;

import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 3.0
 */
public interface ReferenceService extends Service {

    /**
     * Get all reference from the given curriculum vitae ID.
     *
     * @param curriculumVitaeId the curriculum vitae ID
     * @return list of references
     */
    List<ReferenceResponse> get(Long curriculumVitaeId);

    /**
     * Get reference from the index and curriculum vitae ID provided.
     *
     * @param curriculumVitaeId the curriculum vitae ID
     * @param index             the index
     * @return the reference data
     */
    ReferenceResponse get(Long curriculumVitaeId, Integer index);

    /**
     * Create new reference from the given data.
     *
     * @param curriculumVitaeId curriculum vitae ID
     * @param reference         reference information
     * @return the reference data
     */
    ReferenceResponse add(Long curriculumVitaeId, ReferenceRequest reference);

    /**
     * Update the reference data from the given ID.
     *
     * @param index             the index of element
     * @param curriculumVitaeId the curriculum vitae ID
     * @param reference         the reference information
     * @return the reference updated
     */
    ReferenceResponse update(Long curriculumVitaeId, Integer index, ReferenceRequest reference);

    /**
     * Remove the reference information provided by the index and corresponding
     * to a messenger.
     *
     * @param index             the index of element
     * @param curriculumVitaeId the curriculum vitae ID
     * @return the reference deleted
     */
    ReferenceResponse delete(Long curriculumVitaeId, Integer index);

}
