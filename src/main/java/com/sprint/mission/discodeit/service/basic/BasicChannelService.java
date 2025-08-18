package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.exception.channel.ChannelNotFoundException;
import com.sprint.mission.discodeit.exception.channel.PrivateChannelUpdateException;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BasicChannelService implements ChannelService {

  private final ChannelRepository channelRepository;
  //
  private final ReadStatusRepository readStatusRepository;
  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final ChannelMapper channelMapper;

  @Transactional
  @Override
  public ChannelDto create(PublicChannelCreateRequest request) {
    log.debug("public Channel 생성 요청 : request = {}", request);
    String name = request.name();
    String description = request.description();
    Channel channel = new Channel(ChannelType.PUBLIC, name, description);

    channelRepository.save(channel);
    log.info("public Channel 생성 성공 : name = {}", request.name());
    return channelMapper.toDto(channel);
  }

  @Transactional
  @Override
  public ChannelDto create(PrivateChannelCreateRequest request) {
    log.debug("private Channel 생성 요청 : request = {}", request);
    Channel channel = new Channel(ChannelType.PRIVATE, null, null);
    channelRepository.save(channel);
    log.info("private Channel 생성 성공 : channel = {}", channel);

    List<ReadStatus> readStatuses = userRepository.findAllById(request.participantIds()).stream()
        .map(user -> new ReadStatus(user, channel, channel.getCreatedAt()))
        .toList();
    readStatusRepository.saveAll(readStatuses);

    return channelMapper.toDto(channel);
  }

  @Transactional(readOnly = true)
  @Override
  public ChannelDto find(UUID channelId) {
    return channelRepository.findById(channelId)
        .map(channelMapper::toDto)
        .orElseThrow(
            () -> new ChannelNotFoundException(channelId));
  }

  @Transactional(readOnly = true)
  @Override
  public List<ChannelDto> findAllByUserId(UUID userId) {
    List<UUID> mySubscribedChannelIds = readStatusRepository.findAllByUserId(userId).stream()
        .map(ReadStatus::getChannel)
        .map(Channel::getId)
        .toList();

    return channelRepository.findAllByTypeOrIdIn(ChannelType.PUBLIC, mySubscribedChannelIds)
        .stream()
        .map(channelMapper::toDto)
        .toList();
  }

  @Transactional
  @Override
  public ChannelDto update(UUID channelId, PublicChannelUpdateRequest request) {
    log.debug("Channel 수정 요청 : request = {} ", request);
    String newName = request.newName();
    String newDescription = request.newDescription();

    Channel channel = channelRepository.findById(channelId)
        .orElseThrow(() -> {
             log.error("Channel 을 찾지 못함 : {}", channelId);
             return new ChannelNotFoundException(channelId);
          }
        );

    if (channel.getType().equals(ChannelType.PRIVATE)) {
      log.debug("private Channel 채널 수정 불가 : getType = {}", channel.getType());
      throw new PrivateChannelUpdateException(channelId);
    }
    channel.update(newName, newDescription);
    log.info("public Channel 수정 성공 : newName = {}, newDescription = {}", request.newName(), request.newDescription());
    return channelMapper.toDto(channel);
  }

  @Transactional
  @Override
  public void delete(UUID channelId) {
    log.debug("Channel 삭제 요청 : channelId = {}", channelId);
    if (!channelRepository.existsById(channelId)) {
      log.error("Channel 삭제 실패 - 존재하지 않음 : channelId = {}", channelId);
      throw new ChannelNotFoundException(channelId);
    }

    log.debug("message 삭제 시작: = {}", channelId);
    messageRepository.deleteAllByChannelId(channelId);
    log.debug("message 삭제 완료: = {}", channelId);

    log.debug("읽음 상태 삭제 시작: = {}", channelId);
    readStatusRepository.deleteAllByChannelId(channelId);
    log.debug("읽음 상태 삭제 완료: = {}", channelId);

    channelRepository.deleteById(channelId);
    log.info("Channel 삭제 성공 : channelId = {}", channelId);
  }
}
