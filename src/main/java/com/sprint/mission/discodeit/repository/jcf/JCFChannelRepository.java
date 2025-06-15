/*package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {
    Map<UUID, Channel> data = new HashMap<>();

    @Override
    public Channel getChannel(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Channel> getChannels() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateChannel(UUID name, String Channel) {
        Channel channel = data.get(name);
        if(name != null){
            channel.updateChannel(Channel);
        }
    }


}
*/