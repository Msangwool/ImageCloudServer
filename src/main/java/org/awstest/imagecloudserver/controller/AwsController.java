package org.awstest.imagecloudserver.controller;

import lombok.RequiredArgsConstructor;
import org.awstest.imagecloudserver.core.File;
import org.awstest.imagecloudserver.dto.*;
import org.awstest.imagecloudserver.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/aws")
public class AwsController {

    private final FileService fileService;

    @PostMapping("/create")
    public ResponseEntity<?> uploadFile(FileSaveReqDto fileSaveReqDto) {
        try {
            fileService.save(fileSaveReqDto);
            return ResponseEntity.ok().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/read/file")
    public ResponseEntity<?> findFileInfo(FileReadReqDto fileReadReqDto) {
        Optional<File> fileInfo = fileService.getFileInfo(fileReadReqDto);
        if (fileInfo.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(fileInfo.get());
    }

    @GetMapping("/read/resource")
    public ResponseEntity<?> readFile(FileReadReqDto fileReadReqDto) throws IOException {
        return fileService.readFile(fileReadReqDto);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateFile(FileUpdateReqDto fileUpdateReqDto) {
        fileService.update(fileUpdateReqDto);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteFile(@RequestBody FileDeleteReqDto fileDeleteReqDto) {
        fileService.delete(fileDeleteReqDto);
        return ResponseEntity.ok().body(null);
    }
}
