package org.awstest.imagecloudserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileReadReqDto {
    private String userId;
    private String originalFileName;

    @Builder
    public FileReadReqDto(String userId, String originalFileName) {
        this.userId = userId;
        this.originalFileName = originalFileName;
    }
}
