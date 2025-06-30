package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.channel.ChannelResponseAllByUserIdDTO;
import com.sprint.mission.discodeit.dto.channel.ChannelResponseDTO;
import com.sprint.mission.discodeit.dto.channel.ChannelUpdateDTO;
import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    UUID createPublic(String name, String description);
    UUID createPrivate(UUID userId);

    ChannelResponseDTO find(UUID channelId);
    List<ChannelResponseDTO> findAll();
    List<ChannelResponseAllByUserIdDTO> findAllByUserId(UUID id);
    Channel update(ChannelUpdateDTO channelUpdateDTO);
    void delete(UUID channelId);
}