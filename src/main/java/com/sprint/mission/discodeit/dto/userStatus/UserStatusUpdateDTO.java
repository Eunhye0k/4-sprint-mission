package com.sprint.mission.discodeit.dto.userStatus;

import com.sprint.mission.discodeit.entity.UserStatusType;

import java.util.UUID;

public record UserStatusUpdateDTO (
        UUID id,
        UserStatusType type
){
}
