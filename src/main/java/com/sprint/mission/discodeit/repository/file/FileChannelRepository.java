package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.util.*;

public class FileChannelRepository implements ChannelRepository {
    private final String filePath = "channels.ser";


    public Channel getChannel(UUID id) {
        Map<UUID, Channel> data = readChannelFile();
        return data.get(id);
    }

    public List<Channel> getChannels() {
        return new ArrayList<>(readChannelFile().values());
    }

    @Override
    public void save(Channel channel) {
        Map<UUID, Channel> data = readChannelFile();
        data.put(channel.getId(), channel);
        writeChannelFile(data);
    }

    @Override
    public void delete(UUID id) {
        Map<UUID, Channel> data = readChannelFile();
        data.remove(id);
        writeChannelFile(data);
    }


    public Optional<Channel> findByChannelName(String title) {
        Map<UUID, Channel> data = readChannelFile();
        return data.values()
                .stream()
                .filter(channel -> channel.getChannel().equals(title))
                .findFirst();
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
