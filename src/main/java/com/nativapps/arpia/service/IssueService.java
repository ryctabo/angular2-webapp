package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.IssueRequest;
import com.nativapps.arpia.model.dto.IssueResponse;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface IssueService extends Service {
    
    /**
     * Returns the issue list in a paginated way
     * 
     * @param start Initial index
     * @param size List size
     * @return Issue list
     */
    ListResponse<IssueResponse> getAll(int start, int size);
    
    /**
     * Returns a specific issue entity
     * 
     * @param id Novelty ID
     * @return Issue entity
     */
    IssueResponse get(Long id);
    
    /**
     * Add a new issue entity
     * 
     * @param data Issue information
     * @return Added issue entity
     */
    IssueResponse add(IssueRequest data);
    
    /**
     * Updates a issue entity
     * 
     * @param id Issue ID
     * @param data Issue information
     * @return Updated issue entity
     */
    IssueResponse update(Long id, IssueRequest data);
    
    /**
     * delete a issue entity
     * 
     * @param id Issue ID
     * @return Deleted issue entity
     */
    IssueResponse delete(Long id);
}
