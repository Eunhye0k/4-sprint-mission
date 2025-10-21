package com.sprint.mission.discodeit.dto.user;

import com.sprint.mission.discodeit.entity.UserStatusType;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String username,
        String email,
        UserStatusType type
){ }
