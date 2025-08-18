/*package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.*;

public class JCFMessageRepository implements MessageRepository {
    Map<UUID, Message> data = new HashMap<>();

    @Override
    public Message getMessage(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Message> getMessages() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<Message> validationMessage(UUID id) {
        Message message = data.get(id);
        if(message == null){
            System.out.println("해당 ID의 메세지를 찾을 수 없습니다 ");
        }
        return Optional.ofNullable(message);
    }


}

*/