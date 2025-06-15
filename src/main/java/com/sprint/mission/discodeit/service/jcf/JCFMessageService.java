/*package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.*;

public class JCFMessageService implements MessageService {
    Map<UUID, Message> data = new HashMap<>();

    @Override
    public Message createMessage(Message message, User user, Channel channel) {
        if(message == null || user == null || channel == null){
            System.out.println(("message, user, channel은 null일 수 없습니다."));
        }
        data.put(message.getId(), message);

        user.addMessage(message);
        channel.addMessage(message);
        channel.addUser(user);
        message.addUser(user);
        return message;
    }

    @Override
    public Message getMessage(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Message> getMessages() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateMessage(UUID id, String newMessage) {
        Optional.ofNullable(data.get(id))
                .ifPresentOrElse(msg -> msg.updateContent(newMessage),
                        () -> System.out.println("수정할 메세지가 존재하지 않습니다."));
    }

    @Override
    public void deleteMessage(UUID id) {
        Message message = validationMessage(id);
        data.remove(id);

    }
    public Optional<Message> findByMessage(String content) {
        return data.values()
                .stream()
                .filter(msg -> msg.getContent().equals(content))
                .findFirst();
    }

    public Message validationMessage(UUID id) {
        Message message = data.get(id);
        if(message == null){
            System.out.println("해당 ID의 메세지를 찾을 수 없습니다 ");
        }
        return message;
    }
}*/