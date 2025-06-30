package com.sprint.mission.discodeit.dto.binarycontent;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record BinaryContentCreateDTO(
        UUID userId,
        UUID messageId,
        MultipartFile file
) {
}
