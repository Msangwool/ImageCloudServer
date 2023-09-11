package org.awstest.imagecloudserver.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileSaveReqDto {
    private String userId;
    private MultipartFile multipartFile;
}
