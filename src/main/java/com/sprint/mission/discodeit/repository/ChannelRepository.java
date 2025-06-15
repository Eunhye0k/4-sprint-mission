package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository {
//  Channel createChannel(Channel name);
    Channel getChannel(UUID name);
    List<Channel> getChannels();
//  void updateChannel(UUID name, String updateChannel);
//  void deleteChannel(UUID name);
//Optional<Channel> findByChannelName(String title);
    Optional<Channel> findByChannelName(String title);
    void save(Channel channel);  // create or update
    void delete(UUID id);
}
