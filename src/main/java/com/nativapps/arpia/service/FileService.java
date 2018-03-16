package com.nativapps.arpia.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public interface FileService {

    String PATH_BASE = System.getProperty("user.dir");
    
    String FOLDER_BASE = "com.nativapps.arpia.service";
    
    String FOLDER_IMAGE = "images";
    
    String FOLDER_DOCUMENTS = "documents";
    
    String FORMAT_FILE_IMAGE = "IMG_%08d.%s";
    
    String FORMAT_FILE_DOCUMENT = "DOC_%s.%s"; 
    
    String getPathName(FileType type, String fileName);
    
    void write(InputStream in, final OutputStream out) throws IOException;
    
    enum FileType {
        DOCUMENT, IMAGE
    }
}
