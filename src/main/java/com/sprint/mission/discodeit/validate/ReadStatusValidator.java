package com.sprint.mission.discodeit.validate;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReadStatusValidator {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final ReadStatusRepository readStatusRepository;

    public void validateReadStatus(UUID userId, UUID channelId){
        validateUserId(userId);
        validateChannelId(channelId);
        checkDuplicateReadStatus(userId, channelId);

    }

    public void validateUserId(UUID userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("해당 유저가 없습니다."));
        Optional.ofNullable(findUser)
                .orElseThrow(() -> new NoSuchElementException("해당 채널이 없습니다."));
    }

    public void validateChannelId(UUID channelId) {
        Channel findChannel = channelRepository.findById(channelId);
        Optional.ofNullable(findChannel)
                .orElseThrow(() -> new NoSuchElementException("해당 채널이 없습니다."));
    }

    private void checkDuplicateReadStatus(UUID userId, UUID channelId) {
        if(readStatusRepository.findByUserIdAndChannelId(userId, channelId).isPresent()){
            throw new IllegalStateException("중복된 ReadStatus가 존재합니다. Userid : " + userId + ", channelId : " + channelId);
        }

    }
}
