package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Value;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class FileChannelRepository implements ChannelRepository {
    private final Path DIRECTORY;
    private final Path FILE_PATH;
    private final Map<UUID, Channel> data;

    public FileChannelRepository(@Value("${discodeit.repository.file.path}") String path) {
        this.DIRECTORY = Paths.get(System.getProperty("user.dir"), path, "Channel");
        this.FILE_PATH = DIRECTORY.resolve("channel.ser"); //두 경로 조합

        if (Files.notExists(DIRECTORY)) {
            try {
                Files.createDirectories(DIRECTORY);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create directory: " + DIRECTORY, e);
            }
        }
            this.data = loadDataFromFile();
    }

        @SuppressWarnings("unchecked")
        private Map<UUID, Channel> loadDataFromFile() {
            File file = FILE_PATH.toFile();

            if (!file.exists()) {
                return new HashMap<>();
            }
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (Map<UUID, Channel>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Failed to load data" + e.getMessage());
            }
        }

    private void saveDataToFile() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH.toFile()))) {
                oos.writeObject(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    public void clearFile() {
        File file = FILE_PATH.toFile();
        try (FileOutputStream fos = new FileOutputStream(file)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //파일 삭제
    public void deleteFile() {
        File file = FILE_PATH.toFile();
        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                System.out.println("파일 삭제에 실패했습니다.");
            }
        }
    }

    public UUID save(Channel channel) {
        data.put(channel.getId(), channel);
        saveDataToFile();
        return channel.getId();
    }


    public List<Channel> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public UUID update(Channel channel){
            data.put(channel.getId(), channel);
            saveDataToFile();
        return channel.getId();
    }

    public UUID delete(UUID id) {
        data.remove(id);
        saveDataToFile();
        return id;
    }

    @Override
    public void deleteById(UUID id) {
        data.remove(id);
        saveDataToFile();
    }

    @Override
    public Channel findById(UUID id) {
        return data.get(id);
    }

}
