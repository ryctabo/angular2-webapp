package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.DocumentRequest;
import com.nativapps.arpia.model.dto.DocumentResponse;
import com.nativapps.arpia.model.dto.ListResponse;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface DocumentService extends Service {

    /**
     * Return a document list paginated
     * 
     * @param start Initial index
     * @param size List size
     * @param search String to consult in document search
     * @return Document List
     */
    ListResponse<DocumentResponse> getAll(int start, int size, String search);
    
    /**
     * Return a document by provided ID
     * 
     * @param id Document ID
     * @return Searched document
     */
    DocumentResponse get(Long id);
    
    /**
     * Add a document in the database
     * 
     * @param data Document information
     * @return Added document
     */
    DocumentResponse add(DocumentRequest data);
    
    /**
     * Updates a document by provided ID
     * 
     * @param id Document ID
     * @param data Document information
     * @return Updated document
     */
    DocumentResponse update(Long id, DocumentRequest data);
    
    /**
     * Delete a document by provided ID
     * 
     * @param id Document ID
     * @return Deleted document
     */
    DocumentResponse delete(Long id);
}
