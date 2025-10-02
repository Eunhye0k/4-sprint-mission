package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.entity.UserStatusType;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JCFUserStatusRepository implements UserStatusRepository {

        private final Map<UUID, UserStatus> data = new HashMap<>();

    @Override
    public UUID save(UserStatus userStatus) {
        data.put(userStatus.getId(), userStatus);
        return userStatus.getUserId();
    }

    @Override
    public UserStatus find(UUID id) {
        return data.get(id);
    }

    @Override
    public List<UserStatus> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        return data.values()
                .stream()
                .filter(userStatus -> userStatus.getId().equals(id))
                .findFirst();
    }

    @Override
    public UUID update(UserStatus userStatus) {
        data.put(userStatus.getId(), userStatus);
        return userStatus.getUserId();
    }

    @Override
    public UUID delete(UUID id) {
        data.remove(id);
        return id;
    }
}
