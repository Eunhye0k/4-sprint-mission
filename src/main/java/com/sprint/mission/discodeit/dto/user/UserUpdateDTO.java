package com.sprint.mission.discodeit.dto.user;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record UserUpdateDTO(
        UUID id,
        String name,
        String email,
        MultipartFile file
) {
}
