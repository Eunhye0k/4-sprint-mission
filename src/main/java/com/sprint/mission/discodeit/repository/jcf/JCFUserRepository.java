/*package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.*;

public class JCFUserRepository implements UserRepository {
    private final Map<UUID , User> data = new HashMap<>();

    @Override
    public User getUser(UUID id){
        Optional<User> optionalUser = validationId(id);
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

    public Optional<User> findByUserId(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    public Optional<User> validationId(UUID id){
        return Optional.ofNullable(data.get(id));
    }

}
*/