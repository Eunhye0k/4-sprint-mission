package com.sprint.mission.discodeit.storage.s3;

import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "discodeit.storage.type", havingValue = "s3")
public class S3BinaryContentStorage implements BinaryContentStorage {

  String accessKey;
  String secretKey;
  String region;
  String bucket;

  public S3BinaryContentStorage(@Value("${aws.s3.access-key}") String accessKey,
      @Value("${aws.s3.secret-key}") String secretKey,
      @Value("${aws.s3.region}") String region,
      @Value("${aws.s3.bucket}") String bucket) {
    this.accessKey = accessKey;
    this.secretKey = secretKey;
    this.region = region;
    this.bucket = bucket;
  }

  @Override
  public UUID put(UUID binaryContentId, byte[] bytes) {
    try(S3Client s3 = getS3Client()) {
      String key = binaryContentId.toString();
      s3.putObject(PutObjectRequest
              .builder()
              .bucket(bucket)
              .key(key)
              .build(),
          RequestBody.fromBytes(bytes));
    return binaryContentId;
    }
  }

  @Override
  public InputStream get(UUID binaryContentId) {
    return null;
  }

  @Override
  public ResponseEntity<Void> download(BinaryContentDto metaData) {
    return null;
  }

  public S3Client getS3Client() {
    return null;
  }

  public String generatePresignedUrl(String key, String contentType) {
    return null;
  }
}
