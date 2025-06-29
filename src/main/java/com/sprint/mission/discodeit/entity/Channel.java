package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Channel implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    //

    private ChannelType type;
    private String name;
    private String description;
    private List<UUID> userIds = new ArrayList<>();

    public Channel(String name, String description, ChannelType type) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        //
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public void addUsers(UUID userId){
        userIds.add(userId);
    }

    public void setUpdatedAt() {
        this.updatedAt = Instant.now();
    }

    public void setChannel(String name, String description) {
        if(name != null & !Objects.requireNonNull(name).equals(this.name)){
            this.name = name;
        }else{
            throw new IllegalArgumentException("입력한 채널 이름 : " + name + "이 기존 값과 같습니다.");
        }

        if(description != null & !Objects.requireNonNull(description).equals(this.description)){
            this.description = description;
        }else{
            throw new IllegalArgumentException("입력한 설명 : " + description + "이 기존 값과 같습니다.");
        }
        setUpdatedAt();
    }

    public void update(String newName, String newDescription) {
        boolean anyValueUpdated = false;
        if (newName != null && !newName.equals(this.name)) {
            this.name = newName;
            anyValueUpdated = true;
        }
        if (newDescription != null && !newDescription.equals(this.description)) {
            this.description = newDescription;
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updatedAt = Instant.now();
        }
    }
}
