package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ChannelService {
    Channel createChannel(Channel name);
    Channel getChannel(UUID name);
    List<Channel> getChannels();
    void updateChannel(UUID name, String updateChannel);
    void deleteChannel(UUID name);


    public Optional<Channel> findByChannelName(String title);
}
