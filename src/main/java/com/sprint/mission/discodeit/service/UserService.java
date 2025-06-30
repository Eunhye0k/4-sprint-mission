package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.user.UserCreateDTO;
import com.sprint.mission.discodeit.dto.user.UserResponseDTO;
import com.sprint.mission.discodeit.dto.user.UserUpdateDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatusType;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UUID create(UserCreateDTO userCreateDto);
    UserResponseDTO find(UUID userId);
    List<UserResponseDTO> findAll();
    User update(UserUpdateDTO userServiceUpdateDTO);
    UUID updateUserOnline(UUID userId, UserStatusType type);
    UUID delete(UUID userId);
}
