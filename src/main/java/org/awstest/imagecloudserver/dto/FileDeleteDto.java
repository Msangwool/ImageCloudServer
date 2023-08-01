package org.awstest.imagecloudserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class FileDeleteDto {
    private String fileName;
    private MultipartFile multipartFile;

    @Builder
    public FileDeleteDto(String fileName, MultipartFile multipartFile) {
        this.fileName = fileName;
        this.multipartFile = multipartFile;
    }
}
