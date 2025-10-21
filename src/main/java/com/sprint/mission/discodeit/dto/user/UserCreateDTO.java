package com.sprint.mission.discodeit.dto.user;

import org.springframework.web.multipart.MultipartFile;


public record UserCreateDTO(
        String userName,
        String email,
        String password,
        MultipartFile file
) {
}
