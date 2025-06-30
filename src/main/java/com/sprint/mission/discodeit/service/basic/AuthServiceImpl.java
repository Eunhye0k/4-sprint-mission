package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.auth.AuthLoginDTO;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.AuthService;

import java.util.NoSuchElementException;

public class AuthServiceImpl implements AuthService {
    public UserRepository userRepository;

    @Override
    public User login(AuthLoginDTO dto) {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getUsername().equals(dto.username()) &&
                        user.getPassword().equals(dto.password())).findFirst().orElseThrow(() -> new NoSuchElementException("로그인 정보 불일치"));

    }
}
