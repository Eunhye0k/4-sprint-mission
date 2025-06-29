package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private UUID profileId;
    private Instant createdAt;
    private Instant updatedAt;
    //
    private String username;
    private String email;
    private String password;

    public User(String username, String email, String password) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        //
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void setUpdatedAt() {
        this.updatedAt = updatedAt;
    }

    public void setUser(String username, String email){
        if(username != null && !username.equals(this.username)){
            this.username = username;
        }else{
            throw new IllegalArgumentException("입력한 Username : "+ username + "이 기존 값과 같습니다.");
        }

        if(email != null && !email.equals(this.email)){
            this.email = email;
        }else{
            throw new IllegalArgumentException("입력한 Email : " + email + "이 기존 값과 같습니다.");
        }
        setUpdatedAt();
    }

    public void setUsername(String username){
        if(username == null && username.equals(this.username)){
            throw new IllegalArgumentException("입력한 값이 null 혹은 중복입니다.");
        }
        this.username = username;
        setUpdatedAt();
    }

    public void setEmail(String email){
        if(email == null && email.equals(this.email)){
            throw new IllegalArgumentException("입력한 값이 null 혹은 중복입니다.");
        }
        this.email = email;
        setUpdatedAt();
    }

    public void update(String newUsername, String newEmail, String newPassword) {
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

        if (anyValueUpdated) {
            this.updatedAt = Instant.now();
        }


    }
}
