package org.awstest.imagecloudserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.awstest.imagecloudserver.core.File;
import org.awstest.imagecloudserver.core.FileObject;
import org.awstest.imagecloudserver.dto.*;
import org.awstest.imagecloudserver.cloud.FileCloud;
import org.awstest.imagecloudserver.repository.FileRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileCloud fileStore;
    private final FileRepository fileRepository;

    @Override
    public void save(FileSaveReqDto fileSaveReqDto) {

        String userId = fileSaveReqDto.getUserId();
        MultipartFile multipartFile = fileSaveReqDto.getMultipartFile();
        try {
            // Cloud 저장소 저장
            String storedFileName = fileStore.create(multipartFile);

            // 파일 용량 (KB)
            Float fileSize = (multipartFile.getSize() / 1024F);

            // 확장자 분리
            String ext = fileStore.getExt(storedFileName);

            // 데이터베이스 저장
            String originalFileName = multipartFile.getOriginalFilename();
            fileRepository.save(userId, FileObject.builder()
                    .originalFileName(originalFileName)
                    .storedFileName(storedFileName)
                    .fileSize(fileSize)
                    .ext(ext)
                    .build());
        } catch (IOException e) {
            log.debug("IOException = {}", e.getMessage());
        } catch (Exception e) {
            log.debug("[save] Exception = {}, Message = {}", e.getClass(), e.getMessage());
        }
    }

    @Override
    public Optional<File> getFileInfo(FileReadReqDto fileReadReqDto) {
        return fileRepository.findFileByUserIdAndFileName(fileReadReqDto);
    }

    @Override
    public ResponseEntity<?> readFile(FileReadReqDto fileReadReqDto) throws IOException {

        try {
            // 파일에 접근하기 위한 정보 조회
            File file = fileRepository.findFileByUserIdAndFileName(fileReadReqDto).orElseThrow();
            String storedFileName = file.getFileInfo().getStoredFileName();

            // 파일 정보 (byte 배열)
            byte[] bytes = fileStore.read(storedFileName);

            // Request Header 생성
            List<MediaType> mediaTypeList = new ArrayList<>();
            mediaTypeList.add(MediaType.APPLICATION_OCTET_STREAM);
            mediaTypeList.add(new MediaType("image", Objects.requireNonNull(fileStore.getExt(storedFileName))));

            HttpHeaders httpHeaders = setContentTypes(mediaTypeList);
            httpHeaders.setContentLength(bytes.length);

            // 결과값 반환
            return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
        } catch (IOException e) {
            log.debug("IOException = {}", e.getMessage());
            return ResponseEntity.badRequest().body("입출력 오류");
        } catch (Exception e) {
            log.debug("[readFile] Exception = {}, Message = {}", e.getClass(), e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Override
    public void update(FileUpdateReqDto fileUpdateReqDto) {

        String userId = fileUpdateReqDto.getUserId();
        String originalFileName = fileUpdateReqDto.getOriginalFileName();
        MultipartFile multipartFile = fileUpdateReqDto.getMultipartFile();
        try {
            // 기존 저장된 File 탐색
            File file = fileRepository.findFileByUserIdAndFileName(FileReadReqDto.builder()
                    .userId(userId)
                    .originalFileName(originalFileName)
                    .build()).orElseThrow();


            // Cloud 저장소 수정 실행
            String oldStoredFileName = file.getFileInfo().getStoredFileName();
            String newStoredFileName = fileStore.update(oldStoredFileName, multipartFile);

            // 데이터베이스 저장소 수정 실행
            fileRepository.update(file.getFileSeq(), userId, FileObject.builder()
                    .originalFileName(originalFileName)
                    .storedFileName(newStoredFileName)
                    .fileSize(multipartFile.getSize() / 1024F)
                    .ext(fileStore.getExt(newStoredFileName))
                    .build());
        } catch (IOException e) {
            log.debug("[update] IOException = {}", e.getMessage());
        } catch (Exception e) {
            log.debug("[update] Exception = {}, Message = {}", e.getClass(), e.getMessage());
        }
    }

    @Override
    public void delete(FileDeleteReqDto fileDeleteReqDto) {

        String userId = fileDeleteReqDto.getUserId();
        String fileName = fileDeleteReqDto.getFileName();
        try {
            // 기존 파일 조회
            File file = fileRepository.findFileByUserIdAndFileName(FileReadReqDto.builder()
                    .userId(userId)
                    .originalFileName(fileName)
                    .build()).orElseThrow();

            // Cloud 저장소 삭제 실행
            String storedFileName = file.getFileInfo().getStoredFileName();
            fileStore.delete(storedFileName);

            // 데이터베이스 저장소 삭제 실행
            fileRepository.delete(storedFileName);
        } catch (Exception e) {
            log.debug("[delete] Exception = {}, Message = {}", e.getClass(), e.getMessage());
        }
    }

    private HttpHeaders setContentTypes(List<MediaType> mediaTypeList) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (MediaType mediaType : mediaTypeList) {
            httpHeaders.setContentType(mediaType);
        }
        return httpHeaders;
    }
}
