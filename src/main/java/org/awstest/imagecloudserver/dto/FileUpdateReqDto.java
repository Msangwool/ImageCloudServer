package org.awstest.imagecloudserver.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileUpdateReqDto {
    private String userId;
    private String originalFileName;
    private MultipartFile multipartFile;
}
