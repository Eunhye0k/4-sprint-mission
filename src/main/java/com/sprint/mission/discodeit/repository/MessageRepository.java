package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {
    //  Message createMessage(Message create, User user, Channel channel);
    Message getMessage(UUID id);
    List<Message> getMessages();
    //  void updateMessage (UUID id, String message);
    //  void deleteMessage (UUID id);
    //Optional<Message> findByMessage(String content);
    Optional<Message> validationMessage(UUID id);
    void save(Message message);
    void delete(UUID id);
}
