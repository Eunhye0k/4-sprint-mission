package com.sprint.mission.discodeit.dto.response;

import java.util.UUID;

public record LoginResponseDto(
        UUID userId,
        String username,
        String email
) {
}
