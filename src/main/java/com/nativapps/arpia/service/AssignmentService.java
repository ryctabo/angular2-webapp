package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.AssignmentRequest;
import com.nativapps.arpia.model.dto.AssignmentResponse;
import com.nativapps.arpia.rest.UserInfo;

import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface AssignmentService extends Service {

    /**
     * Get all messenger assigned to domicile.
     *
     * @param domicileExeId the domicile execute ID
     * @return messengers assigned to domicile.
     */
    List<AssignmentResponse> getAll(long domicileExeId);

    /**
     * Get a messenger assigned to domicile.
     *
     * @param domicileExeId the domicile execute ID
     * @param index         index of assigment
     * @return messenger assigned to domicile.
     */
    AssignmentResponse get(Long domicileExeId, Integer index);

    /**
     * Assign a messenger to domicile.
     *
     * @param domicileExeId the domicile execute ID
     * @param assignment    the assignment data
     * @param userInfo      user information
     * @return assigment data
     */
    AssignmentResponse assign(Long domicileExeId, AssignmentRequest assignment, UserInfo userInfo);

}
