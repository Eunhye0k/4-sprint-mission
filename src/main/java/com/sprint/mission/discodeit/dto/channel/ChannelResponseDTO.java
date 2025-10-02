package com.sprint.mission.discodeit.dto.channel;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ChannelResponseDTO(
        UUID id,
        String name,
        String description,
        Instant recentTime,
        List<UUID> ids
){
}
