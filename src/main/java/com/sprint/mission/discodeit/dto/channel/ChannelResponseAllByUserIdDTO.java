package com.sprint.mission.discodeit.dto.channel;

import com.sprint.mission.discodeit.entity.ChannelType;

import java.util.UUID;

public record ChannelResponseAllByUserIdDTO(
        UUID id,
        String name,
        String description,
        ChannelType type
) {
}
