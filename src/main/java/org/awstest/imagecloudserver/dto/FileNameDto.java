package org.awstest.imagecloudserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileNameDto {
    private String fileName;

    @Builder
    public FileNameDto(String fileName) {
        this.fileName = fileName;
    }
}
