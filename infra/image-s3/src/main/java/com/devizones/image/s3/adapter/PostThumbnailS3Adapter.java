package com.devizones.image.s3.adapter;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devizones.application.post.port.ThumbnailImagePort;
import com.devizones.image.s3.adapter.config.S3Properties;
import com.devizones.image.s3.adapter.exception.AwsS3Exception;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import static com.devizones.image.s3.adapter.exception.AwsS3ErrorCode.UPLOAD_FAIL;

@Component
@RequiredArgsConstructor
public class PostThumbnailS3Adapter implements ThumbnailImagePort {
    private final S3Properties s3Properties;
    private final AmazonS3Client amazonS3Client;
    @Override
    public void upload(String fileName, InputStream stream) {
        try (InputStream inputStream = stream) {
            String path = s3Properties.getPostPrefix() + fileName;
            ObjectMetadata metaData = createMetaData(fileName, stream);
            amazonS3Client.putObject(
                    new PutObjectRequest(s3Properties.getBucket(), path, inputStream, metaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new AwsS3Exception(UPLOAD_FAIL);
        }
    }

    private ObjectMetadata createMetaData(String fileName, InputStream stream) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(stream.available());
        objectMetadata.setContentType("image/" + FilenameUtils.getExtension(fileName));
        return objectMetadata;
    }

//    @Override
//    public void remove(String fileName) {
//        amazonS3Client.deleteObject(new DeleteObjectRequest(profileS3Properties.getBucket(), fileName));
//    }
//
//    @Override
//    public boolean existImage(String fileName) {
//        return amazonS3Client.doesObjectExist(profileS3Properties.getBucket(), fileName);
//    }
}
