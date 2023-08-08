package org.awstest.imagecloudserver.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.awstest.imagecloudserver.core.File;
import org.awstest.imagecloudserver.dto.FileReadReqDto;
import org.awstest.imagecloudserver.core.FileObject;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FileRepositoryImpl implements FileRepository {

    private static final Map<Long, File> store = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    @Override
    public void save(String userId, FileObject fileObject) {
        Long fileSeq = ++sequence;
        try {
            store.put(fileSeq, File.builder()
                    .fileSeq(fileSeq)
                    .userId(userId)
                    .fileInfo(fileObject)
                    .build());
        } catch (Exception e) {
            log.debug("Exception = {}", e.getMessage());
        }
    }

    @Override
    public Optional<File> findFileByUserIdAndFileName(FileReadReqDto fileReadReqDto) {
        String userId = fileReadReqDto.getUserId();
        String originalFileName = fileReadReqDto.getOriginalFileName();
        return store.values().stream().filter(file -> file.getUserId().equals(userId) && file.getFileInfo().getOriginalFileName().equals(originalFileName)).findFirst();
    }

    @Override
    public void update(Long fileSeq, String userId, FileObject fileStoreDto) {
        try {
            store.replace(fileSeq, File.builder()
                    .fileSeq(fileSeq)
                    .userId(userId)
                    .fileInfo(fileStoreDto)
                    .build());
        } catch (Exception e) {
            log.debug("Exception = {}", e.getMessage());
        }
    }

    @Override
    public void delete(String storedFileName) {
        try {
            Long fileSeq = store.values().stream().filter(file -> file.getFileInfo().getStoredFileName().equals(storedFileName)).findFirst().orElseThrow().getFileSeq();
            store.remove(fileSeq);
        } catch (Exception e) {
            log.debug("Exception = {}", e.getMessage());
        }
    }
}
