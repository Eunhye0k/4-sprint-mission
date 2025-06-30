package com.sprint.mission.discodeit.validate;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserStatusValidator {
    private final UserRepository userRepository;

    public void validateUserStatus(UUID userId) {
        validateUserId(userId);
        checkDuplicateUserStatus(userId);
    }

    public void validateUserId(UUID userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("해당 유저가 없습니다"));
        Optional.ofNullable(findUser)
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 없습니다"));
    }

    private void checkDuplicateUserStatus(UUID userId) {
        if(userRepository.findById(userId).isPresent()){
            throw new IllegalStateException("중복된 UserStatus가 존재합니다. Userid : " + userId);
        }
    }
}

