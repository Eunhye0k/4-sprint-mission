package com.sprint.mission.discodeit.dto.response;

import java.util.UUID;

public record BinaryContentResponseDto(
        UUID id,
        String fileName,
        String contentType
) {
}
