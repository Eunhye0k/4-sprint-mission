package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    //
    private String content;
    //
    private UUID channelId;
    private UUID authorId;
    List<UUID> binaryContentId;

    public Message(String content, UUID channelId, UUID authorId) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        //
        this.content = content;
        this.channelId = channelId;
        this.authorId = authorId;
        binaryContentId = new ArrayList<>();
    }

    public void setUpdatedAt() {
        this.updatedAt = Instant.now();
    }

    public void setMessage(String content){
        if(content != null && !content.equals(this.content)){
            this.content = content;
        }else{
            throw new IllegalArgumentException("입력한 메세지 : " + content + "가 기존과 같습니다.");
        }
        setUpdatedAt();
    }

    public static boolean validation(String content){
        if(content == null || content.isEmpty()){
            return false;
        }
        return true;
    }

    public void addBinaryContent(UUID attachmentId){
        binaryContentId.add(attachmentId);
    }

    public void update(String newContent) {
        boolean anyValueUpdated = false;
        if (newContent != null && !newContent.equals(this.content)) {
            this.content = newContent;
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            this.updatedAt = Instant.now();
        }
    }
}
