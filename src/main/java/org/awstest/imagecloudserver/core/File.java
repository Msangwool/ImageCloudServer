package org.awstest.imagecloudserver.core;

import lombok.Builder;
import lombok.Getter;

@Getter
public class File {
    private Long fileSeq;
    private String userId;
    private FileObject fileInfo;

    @Builder
    public File(Long fileSeq, String userId, FileObject fileInfo) {
        this.fileSeq = fileSeq;
        this.userId = userId;
        this.fileInfo = fileInfo;
    }
}
