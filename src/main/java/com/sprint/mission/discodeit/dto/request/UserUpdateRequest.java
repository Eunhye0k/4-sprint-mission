package com.sprint.mission.discodeit.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
    String newUsername,

    @Email(message = "올바른 형식이여야 합니다.")
    String newEmail,

    @Size(min = 8, message = "비밀번호는 최소 8자리여야 합니다.")
    String newPassword
) {

}
