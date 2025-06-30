package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public record BinaryContent(
        UUID id,
        Instant createAt,
        UUID messageId,
        UUID userId,
        byte[] data,
        String contentType,
        Long size
) {
    public BinaryContent(UUID userId, UUID messageId , byte[] data, String contentType, Long size) {
        this(UUID.randomUUID(),
                Instant.now(),
                messageId,
                userId,
                data,
                contentType = contentType,
                size = size)
        ;
    }
}
