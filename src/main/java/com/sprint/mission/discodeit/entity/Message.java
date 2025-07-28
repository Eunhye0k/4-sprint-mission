package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "MESSAGES")
@RequiredArgsConstructor
public class Message extends BaseUpdatableEntity{

  @Column(nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "CHANNEL_ID", nullable = false)
  private Channel channel;

  @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.REMOVE)
  @JoinColumn(name = "USER_ID", nullable = false)
  private User author;

  @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BinaryContent> attachmentIds;

  public Message(String content, Channel channel, User author, List<BinaryContent> attachmentIds) {
    this.content = content;
    this.channel = channel;
    this.author = author;
    this.attachmentIds = attachmentIds;

  }
  public void update(String newContent) {
    boolean anyValueUpdated = false;
    if (newContent != null && !newContent.equals(this.content)) {
      this.content = newContent;
      anyValueUpdated = true;
    }

    if (anyValueUpdated) {
      this.setUpdatedAt(Instant.now());
    }
  }
}
