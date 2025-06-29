package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateDTO;
import com.sprint.mission.discodeit.dto.message.MessageCreateDTO;
import com.sprint.mission.discodeit.dto.message.MessageUpdateDTO;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.validate.MessageValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final MessageValidator messageValidator;

    @Override
    public UUID create(MessageCreateDTO dto) {
       messageValidator.validateMessage(dto.content(), dto.userId(), dto.channelId());
       Message message = new Message(dto.content(), dto.userId(), dto.channelId());

       if(dto.files() != null && !dto.files().isEmpty()) {
           for(MultipartFile file : dto.files()) {
               UUID binaryId = binaryContentRepository.create(new BinaryContentCreateDTO(
                       dto.userId(),
                       dto.channelId(),
                       file));
               message.addBinaryContent(binaryId);
           }
       }
        return messageRepository.save(message).getId();
    }

    @Override
    public Message find(UUID messageId) {
        Message findMessage = messageRepository.findById(messageId).orElseThrow(() -> new NoSuchElementException("해당 아이디가 없습니다."));
        return Optional.ofNullable(findMessage).orElseThrow(() -> new NoSuchElementException("해당 메세지가 없습니다."));
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> findAllByChannelId(UUID channelId) {
        return messageRepository.findAllByChannelId(channelId);
    }

    @Override
    public Message update(MessageUpdateDTO dto) {
        Message findMessage = messageRepository.findById(dto.id()).orElseThrow(() -> new NoSuchElementException("해당 메세지가 없습니다."));
        findMessage.update(dto.content());
        messageRepository.save(findMessage);
        return findMessage;
    }

    @Override
    public UUID delete(UUID messageId) {
        Message findMessage = messageRepository.findById(messageId).orElseThrow(() -> new NoSuchElementException("해당 메세지가 없습니다."));

        for(UUID binaryContentId : findMessage.getBinaryContentId()){
            binaryContentRepository.delete(binaryContentId);
        }
        return messageRepository.deleteById(findMessage.getId());

    }
}
