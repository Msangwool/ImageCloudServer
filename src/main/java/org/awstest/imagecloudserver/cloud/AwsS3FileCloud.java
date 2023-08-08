package org.awstest.imagecloudserver.cloud;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AwsS3FileCloud implements FileCloud {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    @Override
    public String create(MultipartFile multipartFile) {
        String ext = getExt(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String fileName = UUID.randomUUID() + "." + ext;

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getInputStream().available());

            amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), objectMetadata);
            String fileUri = amazonS3.getUrl(bucket, fileName).toString();
            int lastIndex = fileUri.lastIndexOf("/");

            if (lastIndex < 0) {
                throw new IOException("파일을 저장하는데 실패했습니다.");
            }

            return fileName;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public byte[] read(String storedFileName) throws IOException {
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, storedFileName));
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();

        return IOUtils.toByteArray(s3ObjectInputStream);
    }

    @Override
    public void delete(String storedFileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, storedFileName));
    }

    @Override
    public String update(String storedFileName, MultipartFile multipartFile) {
        delete(storedFileName);
        return create(multipartFile);
    }

    @Override
    public String getExt(String fileName) {
        int dotIndex = fileName.trim().lastIndexOf(".");
        if (dotIndex >= 0) {
            return fileName.substring(dotIndex + 1);
        }
        return null;
    }
}
