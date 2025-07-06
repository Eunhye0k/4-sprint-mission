package com.sprint.mission.discodeit.dto.request;


import java.util.List;
import java.util.UUID;

public record ChannelCreateRequest (
        String name,
        String description,
        String type,
        List<UUID> participantIds
){
}
