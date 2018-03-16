package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Document;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface DocumentDao extends DataAccessObject<Document, Long> {
    
    /**
     * Return a paginated document list
     * 
     * @param start Initial index
     * @param size List size
     * @param search String to consult in document search
     * @return Document list
     */
    List<Document> findAll(int start, int size, String search);
    
    /**
     * Find a document by provided name
     * 
     * @param name Document name
     * @return Searched document
     */
    Document findByName(String name);
    
    /**
     * Find a document by provided url
     * 
     * @param url Document url
     * @return Searched document
     */
    Document findByUrl(String url);
    
    /**
     * Return the find all result count 
     * 
     * @param search String to consult in document search
     * @return result count
     */
    Long findAllCount(String search);
}
