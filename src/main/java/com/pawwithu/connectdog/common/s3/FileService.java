package com.pawwithu.connectdog.common.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.pawwithu.connectdog.error.exception.custom.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static com.pawwithu.connectdog.error.ErrorCode.INVALID_FILE_UPLOAD;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket.name}")
    private String bucketName;

    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;

    public String uploadFile(MultipartFile multipartFile, String imageType) {
        if (multipartFile == null || multipartFile.isEmpty()) return null;

        String savedFileName = getSavedFileName(multipartFile, imageType);
        ObjectMetadata metadata = new ObjectMetadata();

        try (InputStream inputStream = multipartFile.getInputStream()) {
            metadata.setContentLength(multipartFile.getSize());
            amazonS3Client.putObject(bucketName, savedFileName, inputStream, metadata);
        } catch (IOException e) {
            log.error("Failed to upload image", e);
            throw new BadRequestException(INVALID_FILE_UPLOAD);
        }
        return getResourceUrl(savedFileName);
    }

    public void deleteFile(String fileUrl) {
        String fileName = getFileNameFromResourceUrl(fileUrl);
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }

    // imageType: volunteer -> "volunteer"
    private String getSavedFileName(MultipartFile multipartFile, String imageType) {
        return String.format("%s/%s-%s",
                imageType, getRandomUUID(), multipartFile.getOriginalFilename());
    }

    private String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private String getResourceUrl(String savedFileName) {
        return amazonS3Client.getResourceUrl(bucketName, savedFileName);
    }

    private String getFileNameFromResourceUrl(String fileUrl) {
        return fileUrl.replace(defaultUrl + "/", "");
    }
}