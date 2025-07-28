package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "USERS")
@RequiredArgsConstructor
public class User extends BaseUpdatableEntity{

  @Column(nullable = false)
  private String username;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String password;

  @OneToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "profile_id")
  private BinaryContent profile;     // BinaryContent

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private UserStatus userStatus;

  public User(String username, String email, String password, BinaryContent profile) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.profile = profile;
  }

  public void update(String newUsername, String newEmail, String newPassword, BinaryContent newProfileId) {
    boolean anyValueUpdated = false;
    if (newUsername != null && !newUsername.equals(this.username)) {
      this.username = newUsername;
      anyValueUpdated = true;
    }
    if (newEmail != null && !newEmail.equals(this.email)) {
      this.email = newEmail;
      anyValueUpdated = true;
    }
    if (newPassword != null && !newPassword.equals(this.password)) {
      this.password = newPassword;
      anyValueUpdated = true;
    }
    if (newProfileId != null && !newProfileId.equals(this.profile)) {
      this.profile = newProfileId;
      anyValueUpdated = true;
    }

    if (anyValueUpdated) {
      this.setUpdatedAt(Instant.now());
    }
  }
}
