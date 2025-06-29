package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.channel.ChannelResponseAllByUserIdDTO;
import com.sprint.mission.discodeit.dto.channel.ChannelResponseDTO;
import com.sprint.mission.discodeit.dto.channel.ChannelUpdateDTO;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusCreateDTO;
import com.sprint.mission.discodeit.dto.user.UserResponseDTO;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.validate.ChannelValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;

    private final UserRepository userRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;
    private final ChannelValidator channelValidator;

    @Override
    public UUID createPublic(String name, String description) {
        channelValidator.validateChannel(name, description);
        Channel channel = new Channel(name, description, ChannelType.PUBLIC);
        return channelRepository.save(channel);
    }

    @Override
    public UUID createPrivate(UUID userId) {
        Channel channel = new Channel(null, null, ChannelType.PRIVATE);
        channelRepository.save(channel);
        channel.addUsers(userId);

        return channelRepository.save(channel);
    }

    @Override
    public ChannelResponseDTO find(UUID channelId) {
        Channel findChannel = channelRepository.findById(channelId);
        Optional.ofNullable(findChannel)
                .orElseThrow(() -> new IllegalArgumentException("Channel not found"));

        Instant recentTime = messageRepository.findAllByChannelId(findChannel.getId()).stream()
                .map(Message::getCreatedAt)
                .max(Comparator.naturalOrder())
                .orElse(null);

        List<UUID> userIds = (findChannel.getType() == ChannelType.PRIVATE) ? findChannel.getUserIds() : Collections.emptyList();

        return new ChannelResponseDTO(
                findChannel.getId(),
                findChannel.getName(),
                findChannel.getDescription(),
                recentTime,
                userIds);
    }

    @Override
    public List<ChannelResponseDTO> findAll() {
        List<ChannelResponseDTO> list = channelRepository.findAll().stream()
                .map(channel -> {

                    Instant recentTime = messageRepository.findAllByChannelId(channel.getId()).stream()
                            .map(Message::getCreatedAt)
                            .max(Comparator.naturalOrder())
                            .orElse(null);

                    List<UUID> userIds = (channel.getType() == ChannelType.PRIVATE) ? channel.getUserIds() : Collections.emptyList();


                    return new ChannelResponseDTO(
                            channel.getId(),
                            channel.getName(),
                            channel.getDescription(),
                            recentTime,
                            userIds);
                }).toList();
        return list;
    }

    @Override
    public List<ChannelResponseAllByUserIdDTO> findAllByUserId(UUID id) {
        return channelRepository.findAll()
                .stream()
                .filter(channel -> channel.getType() != ChannelType.PRIVATE || channel.getUserIds().contains(id))
                .map(channel -> new ChannelResponseAllByUserIdDTO(
                        channel.getId(),
                        channel.getName(),
                        channel.getDescription(),
                        channel.getType()
                )).toList();
    }

    @Override
    public Channel update(ChannelUpdateDTO dto) {
        Channel channel = channelRepository.findById(dto.id());
        if(channel.getType() == ChannelType.PRIVATE) {
            throw new IllegalArgumentException("Private 채널은 업데이트 할 수 없습니다.");
        }else{
            channel.update(dto.name(), dto.description());
            channelRepository.update(channel);
        }
        return channel;
    }

    @Override
    public void delete(UUID channelId) {
        Channel channel = channelRepository.findById(channelId);
        channelRepository.deleteById(channel.getId());
    }
}
