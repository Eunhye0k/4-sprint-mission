package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.user.UserUpdateDTO;
import com.sprint.mission.discodeit.dto.userStatus.UserStatusCreateDTO;
import com.sprint.mission.discodeit.dto.userStatus.UserStatusUpdateDTO;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.entity.UserStatusType;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import com.sprint.mission.discodeit.validate.UserStatusValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserStatusValidator userStatusValidator;


    @Override
    public UUID create(UserStatusCreateDTO dto) {
        userStatusValidator.validateUserStatus(dto.id());

        UserStatus userStatus = new UserStatus(dto.id());
        return userStatusRepository.save(userStatus);
    }

    @Override
    public UserStatus find(UUID id) {
        UserStatus findUserStatus = userStatusRepository.find(id);
        Optional.ofNullable(findUserStatus)
                .orElseThrow(() -> new NoSuchElementException("해당 UserStatus가 없습니다."));

        findUserStatus.isOnline();
        return findUserStatus;
    }

    @Override
    public List<UserStatus> findAll() {
        return userStatusRepository.findAll()
                .stream()
                .peek(UserStatus::isOnline)
                .toList();
    }

    @Override
    public UserStatus update(UserStatusUpdateDTO dto) {
        UserStatus findUserStatus = userStatusRepository.find(dto.id());
        findUserStatus.updateUserStatus(dto.type());
        userStatusRepository.save(findUserStatus);
        return findUserStatus;
    }

    @Override
    public UserStatus updateByUserId(UUID userId, UserStatusType type) {
        UserStatus findUserStatus = userStatusRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("해당 유저의 UserStatus가 없습니다."));

        findUserStatus.updateUserStatus(type);
        userStatusRepository.update(findUserStatus);
        return findUserStatus;
    }

    @Override
    public UUID delete(UUID id) {
        return userStatusRepository.delete(id);
    }
}
