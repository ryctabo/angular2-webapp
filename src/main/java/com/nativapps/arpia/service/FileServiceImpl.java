package com.nativapps.arpia.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class FileServiceImpl implements FileService {

    public PathBuild getPathBuild() {
        return new PathBuild();
    }

    @Override
    public String getPathName(FileType type, String fileName) {
        return getPathBuild()
                .path(PATH_BASE)//, FOLDER_BASE
                .path((type == FileType.DOCUMENT ? FOLDER_DOCUMENTS : FOLDER_IMAGE))
                .build(fileName);
    }

    @Override
    public void write(InputStream in, final OutputStream out) throws IOException {
        int read;
        byte[] bytes = new byte[1024];
        while ((read = in.read(bytes)) != -1)
            out.write(bytes, 0, read);
        out.flush();
    }

    protected class PathBuild {

        private String path = "";

        public PathBuild path(String... paths) {
            for (String pathItem : paths)
                this.path += pathItem + System.getProperty("file.separator");
            return this;
        }

        public String build(String fileName) {
            return this.path + fileName;
        }

        public String build() {
            return this.path;
        }

    }

}
