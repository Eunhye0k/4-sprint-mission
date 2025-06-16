package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.io.*;
import java.util.*;

public class FileChannelService implements ChannelService {
    private final String filePath = "channels.ser";

    @Override
    public Channel createChannel(Channel channel) {
        Map<UUID, Channel> data = readChannelFile();

        Optional<Channel> optionalChannel = findByChannelName(channel.getChannel());
        if (optionalChannel.isPresent()) {
            System.out.println("이미 존재하는 채널명입니다: " + channel.getChannel());
            return optionalChannel.get();
        }

        data.put(channel.getId(), channel);
        writeChannelFile(data);
        System.out.println("channels.ser 파일에 작성을 완료했습니다.");
        return channel;
    }

    @Override
    public Channel getChannel(UUID id) {
        return validationChannel(id).orElse(null);
    }

    @Override
    public List<Channel> getChannels() {
        return new ArrayList<>(readChannelFile().values());
    }

    @Override
    public void updateChannel(UUID id, String updateTitle) {
        Map<UUID, Channel> data = readChannelFile();
        validationChannel(id).ifPresentOrElse(
                channel -> {
                    channel.updateChannel(updateTitle);
                    writeChannelFile(data);
                    System.out.println("채널 업데이트 완료: " + updateTitle);
                },
                () -> System.out.println("존재하지 않는 채널입니다.")
        );
    }

    @Override
    public void deleteChannel(UUID id) {
        Map<UUID, Channel> data = readChannelFile();
        validationChannel(id).ifPresentOrElse(
                channel -> {
                    data.remove(id);
                    writeChannelFile(data);
                    System.out.println("채널 삭제 완료");
                },
                () -> System.out.println("삭제할 채널이 없습니다.")
        );
    }

    @Override
    public Optional<Channel> findByChannelName(String title) {
        Map<UUID, Channel> data = readChannelFile();
        return data.values().stream()
                .filter(channel -> channel.getChannel().equals(title))
                .findFirst();
    }

    public Optional<Channel> validationChannel(UUID id) {
        return Optional.ofNullable(readChannelFile().get(id));
    }

    //채널 파일 READ
    private Map<UUID, Channel> readChannelFile() {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))){
            return (Map<UUID, Channel>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    //채널 파일에 WRITE
    private void writeChannelFile(Map<UUID, Channel> data) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("channels.ser"))){
            out.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
