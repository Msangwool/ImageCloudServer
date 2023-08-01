package org.awstest.imagecloudserver.controller;

import lombok.RequiredArgsConstructor;
import org.awstest.imagecloudserver.dto.FileDeleteDto;
import org.awstest.imagecloudserver.dto.FileNameDto;
import org.awstest.imagecloudserver.fileIO.FileStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/aws")
public class AwsController {

    private final FileStore fileStore;

    @PostMapping("/create")
    public ResponseEntity<?> uploadFile(MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok().body(fileStore.create(multipartFile));
    }

    @GetMapping("/read/{fileName}")
    public ResponseEntity<?> findFile(@PathVariable String fileName) throws IOException {
        return fileStore.read(fileName);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateFile(FileDeleteDto fileDeleteDto) throws IOException {
        return ResponseEntity.ok().body(fileStore.update(fileDeleteDto));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteFile(@RequestBody FileNameDto fileName) {
        fileStore.delete(fileName.getFileName());
        return ResponseEntity.ok().body(null);
    }
}
