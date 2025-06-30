package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository {

    UUID save(Channel channel);
    Channel findById(UUID id);
    List<Channel> findAll();

    UUID update(Channel channel);
    UUID delete(UUID id);
    void deleteById(UUID id);
}
