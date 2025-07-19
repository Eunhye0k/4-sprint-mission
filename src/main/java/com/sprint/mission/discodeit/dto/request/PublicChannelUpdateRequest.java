package com.sprint.mission.discodeit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Channel 수정 정보")
public record PublicChannelUpdateRequest(
    String newName,
    String newDescription
) {

}
