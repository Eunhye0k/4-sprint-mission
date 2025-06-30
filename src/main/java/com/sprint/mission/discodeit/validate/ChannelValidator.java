package com.sprint.mission.discodeit.validate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelValidator {

    public void validateChannel(String channelName, String channelDescription) {
        validateName(channelName);
        validateDescription(channelDescription);
    }

    public void validateName(String channelName) {
        if (channelName == null || channelName.trim().isEmpty()) {
            throw new IllegalArgumentException("채널 이름은 null 이거나 공백일 수 없습니다.");
        }
    }

    public void validateDescription(String channelDescription) {
        if (channelDescription == null || channelDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("채널 설명은 null 이거나 공백일 수 없습니다.");
        }
    }
}
