package org.awstest.imagecloudserver.fileIO;

import org.awstest.imagecloudserver.dto.FileDeleteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStore {

    /**
     * create - FileStore
     * 파일을 AWS S3에 업로드합니다.
     * @param multipartFile MultipartFile 형식의 파일을 받아옵니다.
     * @return 자원에 접근할 수 있는 URI를 받아옵니다.
     * @throws IOException 입출력 에러를 던질 수 있습니다.
     */
    String create(MultipartFile multipartFile) throws IOException;

    /**
     * read - FileStore
     * 파일을 AWS S3에서 읽습니다.
     * @param fileName 파일 이름을 받아옵니다.
     * @return ResponseEntity를 만들어 반환합니다.
     * @throws IOException 입출력 에러를 던질 수 있습니다.
     */
    ResponseEntity<?> read(String fileName) throws IOException;

    /**
     * delete - FileStore
     * AWS S3에 저장되어 있는 파일을 삭제합니다.
     * @param fileName 파일 이름을 받아옵니다.
     */
    void delete(String fileName);

    /**
     * update - FileStore
     * @param fileDeleteDto 파일 수정을 위해 파일 이름과 파일이 담긴 객체를 받아옵니다.
     * @return 새롭게 수정된 자원에 접근할 수 있는 URI를 반환합니다.
     * @throws IOException 입출력 에러를 던질 수 있습니다.
     */
    String update(FileDeleteDto fileDeleteDto) throws IOException;
}
