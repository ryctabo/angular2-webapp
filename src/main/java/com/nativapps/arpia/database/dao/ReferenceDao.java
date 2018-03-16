package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Reference;

import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public interface ReferenceDao extends DataAccessObject<Reference, Reference.ReferencePK> {

    /**
     * Get all references from the given curriculum vitae ID.
     *
     * @param curriculumVitaeId the curriculum vitae ID
     * @return references
     */
    List<Reference> findAll(Long curriculumVitaeId);

    /**
     * Get the number of references that a curriculum vitae has.
     *
     * @param curriculumVitaeId the curriculum vitae ID
     * @return count of references
     */
    long count(Long curriculumVitaeId);

}
