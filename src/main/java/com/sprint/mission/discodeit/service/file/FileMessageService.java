/*
package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;

import java.io.*;
import java.util.*;

public class FileMessageService implements MessageService {
    private String filePath = "message.ser";

    public Message createMessage(Message message, User user, Channel channel) {
        if(message == null || user == null || channel == null){
            System.out.println(("message, user, channel은 null일 수 없습니다."));
        }
        Map<UUID, Message> data = readMessageFile();
        data.put(message.getId(), message);
        writeMessageFile(data);

        user.addMessage(message);
        channel.addMessage(message);
        channel.addUser(user);
        message.addUser(user);
        return message;
    }

    public Message getMessage(UUID id) {
        Map<UUID, Message> data = readMessageFile();
        return validationMessage(id, data).orElse(null);
    }

    public List<Message> getMessages() {
        Map<UUID, Message> data = readMessageFile();
        return new ArrayList<>(data.values());
    }

    public void updateMessage(UUID id, String newMessage) {
        Map<UUID, Message> data = readMessageFile();
        validationMessage(id, data).ifPresentOrElse(
                msg -> {
                    msg.updateContent(newMessage);
                    writeMessageFile(data);
                    System.out.println("메시지 업데이트 완료");
                },
                () -> System.out.println("수정할 메시지가 존재하지 않습니다.")
        );
    }

    public void deleteMessage(UUID id) {
        Map<UUID, Message> data = readMessageFile();
        if(data.containsKey(id)){
            data.remove(id);
            writeMessageFile(data);
            System.out.println("메시지 삭제 완료");
        } else {
            System.out.println("삭제할 메시지가 존재하지 않습니다.");
        }
    }

    public Optional<Message> findByMessage(String content) {
        Map<UUID, Message> data = readMessageFile();
        return data.values()
                .stream()
                .filter(msg -> msg.getContent().equals(content))
                .findFirst();
    }

    public Optional<Message> validationMessage(UUID id, Map<UUID, Message> data) {
        return Optional.ofNullable(data.get(id));
    }

    public Optional<Message> validationMessage(UUID id) {
        Map<UUID, Message> data = readMessageFile();
        return validationMessage(id, data);
    }


    //메세지 파일 READ
    @SuppressWarnings("unchecked")
    private Map<UUID, Message> readMessageFile() {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))){
            return (Map<UUID, Message>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("파일을 읽는 과정에서 에러 발생");
            return new HashMap<>();
        }
    }

    //메세지 파일에 WRITE
    private void writeMessageFile(Map<UUID, Message> data) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Message.ser"))){
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/