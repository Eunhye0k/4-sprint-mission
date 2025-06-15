package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.io.IOException;
import java.util.*;

public class JCFUserService implements UserService {
    private final Map<UUID , User> data = new HashMap<>();

    @Override
    public User createUser(User user){
        Optional<User> optionalUser = findByUserId(user.getId());
        if(optionalUser.isPresent()){
            System.out.println("이미 존재하는 ID 입니다.");
        }else {
            data.put(user.getId(), user);
        }
        return user;
    }

    @Override
    public User getUser(UUID id){
        Optional<User> optionalUser = findByUserId(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            System.out.println("존재하지 않는 아이디입니다.");
            return null;
        }
    }

    @Override
    public List<User> getUsers(){
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateUser(UUID id, String username){
        findByUserId(id).ifPresentOrElse(
                    user -> user.updateName(username),
                    () -> System.out.println("존재하지 않는 아이디입니다.")
            );
    }

    @Override
    public void deleteUser(UUID id) {
        Optional<User> user = findByUserId(id);
        if(user != null) {
            data.remove(id);
            System.out.println("유저 삭제 성공");
        }else{
            System.out.println("삭제 할 유저가 없습니다.");
        }
    }

    public Optional<User> findByUserId(UUID id) {
        return Optional.ofNullable(data.get(id));
    }


}