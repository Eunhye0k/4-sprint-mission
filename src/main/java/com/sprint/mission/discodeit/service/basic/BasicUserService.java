package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateDTO;
import com.sprint.mission.discodeit.dto.user.UserCreateDTO;
import com.sprint.mission.discodeit.dto.user.UserResponseDTO;
import com.sprint.mission.discodeit.dto.user.UserUpdateDTO;
import com.sprint.mission.discodeit.dto.userStatus.UserStatusCreateDTO;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.entity.UserStatusType;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.validate.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {

    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final UserStatusRepository userStatusRepository;

    private final UserValidator userValidator;


    @Override
    public UUID create(UserCreateDTO dto) {
        userValidator.validateUser(dto.userName(), dto.email(), dto.password());
        User user = new User(
                dto.userName(),
                dto.email(),
                dto.password()
        );

        if(dto.file() != null){
            BinaryContentCreateDTO binaryContentCreateDTO = new BinaryContentCreateDTO(user.getId(), null, dto.file());
            binaryContentRepository.create(binaryContentCreateDTO);
        }
        UUID userId = userRepository.save(user).getId();

        userStatusRepository.save(new UserStatus(userId));
        return userId;
    }

    @Override
    public UserResponseDTO find(UUID userId) {
            User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 없습니다."));

        UserStatus findUserStatus = userStatusRepository.findAll()
                .stream()
                .filter(userStatus -> userStatus.getUserId().equals(findUser.getId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("userId : " + findUser.getId() + "유저의  UserStatus가 없습니다."));

        return new UserResponseDTO(
                findUser.getId(),
                findUser.getUsername(),
                findUser.getEmail(),
                findUserStatus.getType());
    }

    @Override
    public List<UserResponseDTO> findAll() {
        List<User> users = userRepository.findAll();

        Map<UUID, UserStatusType> map = userStatusRepository.findAll()
                .stream()
                .collect(Collectors.toMap(UserStatus::getUserId, UserStatus::getType));

        return users.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        map.getOrDefault(user.getId(),null)
                )).toList();
    }

    @Override
    public User update(UserUpdateDTO dto) {
        userValidator.validateUpdateUser(dto.id(), dto.name(), dto.email());

        User findUser = userRepository.findById(dto.id()).orElseThrow(() -> new NoSuchElementException("해당 유저가 없습니다."));;

        findUser.setUser(dto.name(), dto.email());

        userRepository.update(findUser);

        BinaryContent findBinaryContent = binaryContentRepository.findAll()
                .stream()
                .filter(binaryContent -> binaryContent.userId().equals(findUser.getId()))
                .findFirst().orElse(null);

        if(dto.file() != null){
            if(findBinaryContent != null){
                binaryContentRepository.delete(findBinaryContent.id());
            }
            binaryContentRepository.create(new BinaryContentCreateDTO(findUser.getId(), null, dto.file()));
        }


        return findUser;
    }

    @Override
    public UUID delete(UUID userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("해당 유저가 없습니다."));;

        userStatusRepository.findAll()
                .stream()
                .filter(userStatus -> userStatus.getUserId().equals(findUser.getId()))
                .findFirst()
                .map(UserStatus::getUserId)
                .ifPresent(userStatusRepository::delete);

        binaryContentRepository.findAll()
                .stream()
                .filter(content -> content.userId().equals(findUser.getId()) && content.messageId() == null)
                .findFirst().map(BinaryContent::id)
                .ifPresent(binaryContentRepository::delete);


        return userRepository.deleteById(findUser.getId());
    }

    @Override
    public UUID updateUserOnline(UUID userId, UserStatusType type) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("해당 유저가 없습니다,"));
        Optional.ofNullable(findUser)
                .orElseThrow(() -> new NoSuchElementException("해당 유저가 없습니다."));

        return userId;
    }
}
