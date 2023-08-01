package org.awstest.imagecloudserver.fileIO;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.awstest.imagecloudserver.dto.FileDeleteDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileStoreImpl implements FileStore {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    @Override
    public String create(MultipartFile multipartFile) throws IOException {
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());

        amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), objectMetadata);
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    @Override
    public ResponseEntity<?> read(String fileName) throws IOException {
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, fileName));
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();

        byte[] bytes = IOUtils.toByteArray(s3ObjectInputStream);
        int byteLength = bytes.length;

        List<MediaType> mediaTypeList = new ArrayList<>();

        mediaTypeList.add(MediaType.APPLICATION_OCTET_STREAM);
        mediaTypeList.add(new MediaType("image", getExt(fileName)));

        return new ResponseEntity<>(bytes, createHttpHeaders(byteLength, mediaTypeList), HttpStatus.OK);
    }

    @Override
    public void delete(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    @Override
    public String update(FileDeleteDto fileDeleteDto) throws IOException {
        delete(fileDeleteDto.getFileName());
        return create(fileDeleteDto.getMultipartFile());
    }

    private HttpHeaders createHttpHeaders(Integer byteLength, List<MediaType> mediaTypeList) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (MediaType mediaType : mediaTypeList) {
            httpHeaders.setContentType(mediaType);
        }
        httpHeaders.setContentLength(byteLength);
        return httpHeaders;
    }

    private String getExt(String fileName) {
        int dotIndex = fileName.trim().lastIndexOf(".");
        if (dotIndex >= 0) {
            return fileName.substring(dotIndex + 1);
        }
        return null;
    }
}
