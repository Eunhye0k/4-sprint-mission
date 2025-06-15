package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.*;
import java.util.*;

public class FileMessageRepository implements MessageRepository {

    private final String filePath = "message.ser";

    @Override
    public Message getMessage(UUID id) {
        return readMessageFile().get(id);
    }

    @Override
    public List<Message> getMessages() {
        return new ArrayList<>(readMessageFile().values());
    }

    @Override
    public Optional<Message> validationMessage(UUID id) {
        Message message = readMessageFile().get(id);
        if (message == null) {
            System.out.println("해당 ID의 메세지를 찾을 수 없습니다.");
        }
        return Optional.ofNullable(message);
    }


    public void save(Message message) {
        Map<UUID, Message> data = readMessageFile();
        data.put(message.getId(), message);
        writeMessageFile(data);
    }

    public void delete(UUID id) {
        Map<UUID, Message> data = readMessageFile();
        if (data.containsKey(id)) {
            data.remove(id);
            writeMessageFile(data);
        } else {
            System.out.println("삭제할 메세지가 존재하지 않습니다.");
        }
    }


    @SuppressWarnings("unchecked")
    private Map<UUID, Message> readMessageFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Map<UUID, Message>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }


    private void writeMessageFile(Map<UUID, Message> data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}