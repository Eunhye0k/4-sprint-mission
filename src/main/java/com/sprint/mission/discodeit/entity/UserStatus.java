package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.*;
import java.util.UUID;

@Getter
public class UserStatus implements Serializable {
    private final UUID id;
    private UUID userId;

    private final Instant createdAt;
    private Instant updatedAt;
    private UserStatusType type;

    public UserStatus(UUID userId) {
       this.id = UUID.randomUUID();
       this.createdAt = Instant.now();
       this.updatedAt = Instant.now();

       this.userId = userId;
       type = UserStatusType.ONLINE;
    }

    public void setUpdateAt(){
        this.updatedAt = Instant.now();
    }

    public void updateUserStatus(UserStatusType type){
        this.type = type;
        setUpdateAt();
    }


    public boolean isOnline() {
        if (type != UserStatusType.ONLINE && Duration.between(updatedAt, Instant.now()).toMinutes() > 5) {
            return true;
        } else {
            type = UserStatusType.OFFLINE;
            return false;
        }
    }
}
