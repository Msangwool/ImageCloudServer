package org.awstest.imagecloudserver.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileCloud {

    /**
     * create - FileStore
     * 파일을 AWS S3에 업로드합니다.
     * @param multipartFile MultipartFile 형식의 파일을 받아옵니다.
     * @return 자원에 접근할 수 있는 URI 를 받아옵니다.
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
    byte[] read(String fileName) throws IOException;

    /**
     * delete - FileStore
     * AWS S3에 저장되어 있는 파일을 삭제합니다.
     * @param fileName 파일 이름을 받아옵니다.
     */
    void delete(String fileName);

    /**
     * update - FileStore
     * AWS S3에 저장되어 있는 기존 파일을 삭제하고, 새로운 파일을 넣습니다.
     * @param fileName 파일 이름을 받아옵니다.
     * @param multipartFile 새롭게 저장할 파일 정보를 받아옵니다.
     * @return 새롭게 저장한 파일의 파일 명을 반환합니다.
     * @throws IOException
     */
    String update(String fileName, MultipartFile multipartFile) throws IOException;

    String getExt(String fileName);
}
