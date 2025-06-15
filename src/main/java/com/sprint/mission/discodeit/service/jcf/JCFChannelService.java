package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {
    Map<UUID, Channel> data = new HashMap<>();

    @Override
    public Channel createChannel(Channel channel) {
        //내가 만든 어플에서는 중복이 안되지만, 다른사람이 만든 어플에서는 중복이 될 수 있음 -> 핵심 비즈니스 로직 중 1개가 될수있음
        //채널이름 중복체크
        //1. 받아온 파라미터로 채널 생성 시도
        //2. 중복되는 채널이 있는지 리스트에서 조회 (파라미터로 받은 채널의 이름이 있는지 조회)
        //3. 중복되는 채널 있으면 저장 X
        //4. 중복되는 채널 없으면 저장 O
        Optional<Channel> optionalChannel = findByChannelName(channel.getChannel());
        if (optionalChannel.isPresent()) {
            System.out.println("이미 존재하는 채널입니다.");
        } else {
            data.put(channel.getId(), channel);
        }
        return channel;
    }

    @Override
    public Channel getChannel(UUID id) {
        return validationChannel(id);
    }

    @Override
    public List<Channel> getChannels() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateChannel(UUID id, String updateTitle) {
        //조회 -> 검증한 후 -> 반영한 후 -> 저장
        Channel channel = validationChannel(id);
        channel.updateChannel(updateTitle);
    }


    @Override
    public void deleteChannel(UUID id) {
        Channel channel = validationChannel(id);
        if(channel != null){
            data.remove(id);
            System.out.println("채널 삭제 성공");
        }
    }


    public Optional<Channel> findByChannelName(String title) {
        return data.values()
                .stream()
                .filter(channel -> channel.getChannel().equals(title))
                .findFirst();
    }

    private Channel validationChannel(UUID id) {
        Channel channel = data.get(id);
        if (channel == null) {
            System.out.println("존재하지 않는 채널입니다.");
        }
        return channel;
    }
}