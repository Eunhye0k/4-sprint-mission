package com.sprint.mission.discodeit.validate;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserValidator {
    public final UserRepository userRepository;

    public void validateUser(String username, String email, String password){
        validateUserName(username);
        validateUserEmail(email);
        validateUserPassword(password);
        checkDuplicateUser(username, email);
    }

    public void validateUpdateUser(UUID userid, String username, String email){
        validateUserId(userid);
        validateUserName(username);
        validateUserEmail(email);
        checkDuplicateUser(username,email);
    }

    public void validateUserId(UUID userId){
        Optional<User> user  = userRepository.findById(userId);
        Optional.ofNullable(user)
                .orElseThrow(() -> new NoSuchElementException("해당 User가 없습니다."));
    }

    public void validateUserName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("입력 값은 null이거나 공백일 수 없습니다.");
        }
    }

    public void validateUserEmail(String email){
        if(email == null || email.trim().isEmpty()){
            throw new IllegalArgumentException("입력 값은 null이거나 공백일 수 없습니다.");
        }
    }
    public void validateUserPassword(String password){
        if(password == null || password.trim().isEmpty()){
            throw new IllegalArgumentException("입력 값은 null이거나 공백일 수 없습니다.");
        }
    }

    public void checkDuplicateUser(String userName, String userEmail){
        boolean check = userRepository.findAll()
                .stream().anyMatch(user -> user.getUsername().equals(userName) || user.getEmail().equals(userEmail));
        if(check){
            throw new IllegalArgumentException("이름 혹은 이메일이 중복입니다.");
        }

    }
}
