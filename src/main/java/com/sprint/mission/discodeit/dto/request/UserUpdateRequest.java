package com.sprint.mission.discodeit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(description = "User 수정 정보")
@Getter
@Setter
@RequiredArgsConstructor
public class UserUpdateRequest {
    String newUsername;
    String newEmail;
    String newPassword;
}


