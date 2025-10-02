package com.sprint.mission.discodeit.validate;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateDTO;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BinaryContentValidator {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public void validateBinaryContent(UUID userId, UUID MessageId){
        validateUserId(userId);
        validateMessageId(MessageId);
    }

    public void validateUserId(UUID userId){
        if(userId != null){
            User findUser = userRepository.findById(userId).orElseThrow(()->new RuntimeException("해당 메세지가 없습니다"));
            Optional.ofNullable(findUser)
                    .orElseThrow(()->new RuntimeException("해당 유저가 없습니다"));
        }
    }

    public void validateMessageId(UUID messageId){
        if(messageId != null){
            Message findMessage = messageRepository.findById(messageId).orElseThrow(()->new RuntimeException("해당 메세지가 없습니다"));
            Optional.ofNullable(findMessage)
                    .orElseThrow(()->new RuntimeException("해당 유저가 없습니다."));
        }

    }
}
