package org.awstest.imagecloudserver.service;

import org.awstest.imagecloudserver.core.File;
import org.awstest.imagecloudserver.dto.FileDeleteReqDto;
import org.awstest.imagecloudserver.dto.FileReadReqDto;
import org.awstest.imagecloudserver.dto.FileSaveReqDto;
import org.awstest.imagecloudserver.dto.FileUpdateReqDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Optional;

public interface FileService {

    void save(FileSaveReqDto fileSaveReqDto);

    Optional<File> getFileInfo(FileReadReqDto fileReadReqDto);

    ResponseEntity<?> readFile(FileReadReqDto fileReadReqDto) throws IOException;

    void update(FileUpdateReqDto fileUpdateReqDto);

    void delete(FileDeleteReqDto fileDeleteReqDto);
}
