package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class FileUserService implements UserService {
    private final String filePath = "users.ser";

    public User createUser(User user) {
        Map<UUID, User> data = readUserFile();
        findByUserId(user.getId(), data).ifPresentOrElse(
                existingUser -> System.out.println("이미 존재하는 유저 입니다."),
                () -> {
                    data.put(user.getId(), user);
                    writeUserFile(data);
                    System.out.println("users.ser 파일에 작성을 완료했습니다");
                }
        );
        return user;
    }

    public User getUser(UUID id) {
        Map<UUID, User> data = readUserFile();
        return findByUserId(id, data).orElse(null);

    }

    public List<User> getUsers() {
        Map<UUID, User> data = readUserFile();
        return new ArrayList<>(data.values());
    }

    public void updateUser(UUID id, String username) {
        Map<UUID, User> data = readUserFile();
        findByUserId(id, data).ifPresentOrElse(
            user -> {
                    user.updateName(username);
                    writeUserFile(data);
                    System.out.println("유저 업데이트 완료");
               },
               () -> System.out.println("존재하지 않는 유저입니다.")
            );
    }

    public void deleteUser(UUID id) {
        Map<UUID, User> data = readUserFile();
        findByUserId(id, data).ifPresentOrElse(
                user -> {
                    data.remove(id);
                    writeUserFile(data);
                    System.out.println("유저 삭제 완료");
                },
                () -> System.out.println("삭제할 유저가 없습니다: " + id)
        );
    }

    public Optional<User> findByUserId(UUID id , Map<UUID, User> data) {
        return Optional.ofNullable(data.get(id));
    }


    public Optional<User> findByUserId(UUID id) {
        Map<UUID, User> data = readUserFile();
        return findByUserId(id, data);
    }

    //유저 파일 READ
    @SuppressWarnings("unchecked")
    private Map<UUID, User> readUserFile() {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))){
            return (Map<UUID, User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    //유저 파일에 WRITE
    private void writeUserFile(Map<UUID, User> data) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("users.ser"))){
            out.writeObject(data);
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
}

