package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class ReadStatus {
    private UUID id;
    private UUID userId;
    private UUID channelId;
    //
    private Instant createdAt;
    private Instant updatedAt;
    private Instant latReadAt;

    public ReadStatus(UUID id, UUID channelId) {
        this.id = id;
        this.userId = userId;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.channelId = channelId;
    }

    public void updateReadStatus(Instant time) {
        latReadAt = time;
        setUpdatedAt();

    }
    public void setUpdatedAt() {
        updatedAt = Instant.now();
    }
}
