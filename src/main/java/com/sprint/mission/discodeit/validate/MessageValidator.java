package com.sprint.mission.discodeit.validate;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageValidator {
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;


    public void validateMessage(String content, UUID userId, UUID channerId){
        validateContent(content);
        validateUserId(userId);
        validateChanner(channerId);
    }

    public void validateContent(String message){
        if(message == null || message.isEmpty()){
            throw new IllegalArgumentException("입력값은 null 이거나 공백일 수 없습니다.");
        }
    }
    public void validateUserId(UUID userid){
        User findUser = userRepository.findById(userid).orElse(null);
        Optional.ofNullable(findUser)
                .orElseThrow(() -> new NoSuchElementException("해당 User가 없습니다."));
    }

    public void validateChanner(UUID channerId){
        Channel findChannel = channelRepository.findById(channerId);
        Optional.ofNullable(findChannel)
                .orElseThrow(() -> new NoSuchElementException("해당 Channel이 없습니다."));
    }
}
