package com.sprint.mission.discodeit.storage.s3;

import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.io.InputStream;
import java.time.Duration;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Component
@ConditionalOnProperty(name = "discodeit.storage.type", havingValue = "s3")
public class S3BinaryContentStorage implements BinaryContentStorage {

  String accessKey;
  String secretKey;
  String region;
  String bucket;

  public S3BinaryContentStorage(@Value("${discodeit.storage.access-key}") String accessKey,
      @Value("${discodeit.storage.secret-key}") String secretKey,
      @Value("${discodeit.storage.region}") String region,
      @Value("${discodeit.storage.bucket}") String bucket) {
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
    S3Client s3 = getS3Client();
    return s3.getObject(GetObjectRequest.builder()
        .bucket(bucket)
        .key(binaryContentId.toString())
        .build());
  }

  @Override
  public ResponseEntity<Void> download(BinaryContentDto metaData) {
    String url = generatePresignedUrl(metaData.id().toString(), metaData.contentType());
    return ResponseEntity.status(302).header("Location", url).build();
  }

  public S3Client getS3Client() {
    return S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
        .build();
  }

  public String generatePresignedUrl(String key, String contentType) {
    try(S3Presigner presigner = S3Presigner.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
        .build()) {

      GetObjectRequest getObjectRequest = GetObjectRequest.builder()
          .bucket(bucket)
          .key(key)
          .responseContentType(contentType)
          .build();

      GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
          .signatureDuration(Duration.ofHours(10))
          .getObjectRequest(getObjectRequest)
          .build();

    return presigner.presignGetObject(presignRequest).url().toString();
    }
  }
}
