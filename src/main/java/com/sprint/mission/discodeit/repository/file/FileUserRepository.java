package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.util.*;

public class FileUserRepository implements UserRepository {
    private final String filePath = "users.ser";

    public User getUser(UUID id) {
        Map<UUID, User> data = readUserFile();
        return findByUserId(id, data).orElse(null);
    }

    public List<User> getUsers() {
        Map<UUID, User> data = readUserFile();
        return new ArrayList<>(data.values());
    }

    public Optional<User> findByUserId(UUID id , Map<UUID, User> data) {
        return Optional.ofNullable(data.get(id));
    }

    public Optional<User> findByUserId(UUID id) {
        Map<UUID, User> data = readUserFile();
        return findByUserId(id, data);
    }

    public void save(User user){
        Map<UUID, User> data = readUserFile();
        data.put(user.getId(), user);
        writeUserFile(data);
    }

    public void delete(UUID id){
        Map<UUID, User> data = readUserFile();
        if(data.containsKey(id)) {
            data.remove(id);
            writeUserFile(data);
        }else{
            System.out.println("삭제할 유저가 존재하지 않습니다.");
        }
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
