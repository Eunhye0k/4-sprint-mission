package com.sprint.mission.discodeit.dto.message;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record MessageCreateDTO(
        String content,
        UUID userId,
        UUID channelId,
        List<MultipartFile> files
) {
}
