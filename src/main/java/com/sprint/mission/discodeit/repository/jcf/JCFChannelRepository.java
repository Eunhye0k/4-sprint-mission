package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JCFChannelRepository implements ChannelRepository {
    private final Map<UUID, Channel> data;

    public JCFChannelRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public UUID save(Channel channel) {
        this.data.put(channel.getId(), channel);
        return channel.getId();
    }

    @Override
    public Channel findById(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Channel> findAll() {
        return this.data.values().stream().toList();
    }

    @Override
    public UUID update(Channel channel) {
        data.put(channel.getId(), channel);
        return channel.getId();
    }

    @Override
    public UUID delete(UUID id) {
        data.remove(id);
        return id;
    }

    @Override
    public void deleteById(UUID id) {
        this.data.remove(id);
    }
}
