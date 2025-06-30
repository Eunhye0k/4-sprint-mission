package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.message.MessageCreateDTO;
import com.sprint.mission.discodeit.dto.message.MessageUpdateDTO;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    UUID create(MessageCreateDTO messageCreateDTO);
    Message find(UUID messageId);
    List<Message> findAll();
    List<Message> findAllByChannelId(UUID channelId);
    Message update(MessageUpdateDTO messageUpdateDTO);
    UUID delete(UUID messageId);
}
