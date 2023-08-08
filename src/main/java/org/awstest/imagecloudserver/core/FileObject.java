package org.awstest.imagecloudserver.core;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FileObject {
    private String originalFileName;
    private String storedFileName;
    private Float fileSize;
    private String ext;

    @Builder
    public FileObject(String originalFileName, String storedFileName, Float fileSize, String ext) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.fileSize = fileSize;
        this.ext = ext;
    }
}
