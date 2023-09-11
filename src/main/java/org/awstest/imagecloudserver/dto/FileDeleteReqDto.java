package org.awstest.imagecloudserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDeleteReqDto {
    private String userId;
    private String fileName;
}
