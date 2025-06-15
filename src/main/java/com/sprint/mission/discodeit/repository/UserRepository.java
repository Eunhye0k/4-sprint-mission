package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
//  User createUser(User user);
    User getUser(UUID id);
    List<User> getUsers();
//  void updateUser (UUID id, String username);
//  void deleteUser (UUID id);
    Optional<User> findByUserId(UUID id);
    void save(User user);  // create or update
    void delete(UUID id);
}
