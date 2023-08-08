package org.awstest.imagecloudserver.repository;

import org.awstest.imagecloudserver.core.File;
import org.awstest.imagecloudserver.dto.FileReadReqDto;
import org.awstest.imagecloudserver.core.FileObject;

import java.util.Optional;

public interface FileRepository {

    void save(String userId, FileObject fileStoreDto);

    Optional<File> findFileByUserIdAndFileName(FileReadReqDto fileReadReqDto);

    void update(Long fileSeq, String userId, FileObject fileStoreDto);

    void delete(String storedFileName);
}
