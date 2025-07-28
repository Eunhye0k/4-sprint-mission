package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.controller.api.ChannelApi;
import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.response.ChannelResponseDto;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/channels")
public class ChannelController implements ChannelApi {

  private final ChannelService channelService;
  private final ChannelMapper channelMapper;

  @PostMapping(path = "public")
  public ResponseEntity<ChannelResponseDto> create(@RequestBody PublicChannelCreateRequest request) {
    ChannelDto createdChannel = channelService.create(request);
    ChannelResponseDto channelResponseDto = channelMapper.toResponseDto(createdChannel);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(channelResponseDto);
  }

  @PostMapping(path = "private")
  public ResponseEntity<ChannelResponseDto> create(@RequestBody PrivateChannelCreateRequest request) {
    ChannelDto createdChannel = channelService.create(request);
    ChannelResponseDto channelResponseDto = channelMapper.toResponseDto(createdChannel);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(channelResponseDto);
  }

  @PatchMapping(path = "{channelId}")
  public ResponseEntity<ChannelResponseDto> update(@PathVariable("channelId") UUID channelId,
                                                   @RequestBody PublicChannelUpdateRequest request) {
    ChannelDto updatedChannel = channelService.update(channelId, request);
    ChannelResponseDto channelResponseDto = channelMapper.toResponseDto(updatedChannel);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(channelResponseDto);
  }

  @DeleteMapping(path = "{channelId}")
  public ResponseEntity<Void> delete(@PathVariable("channelId") UUID channelId) {
    channelService.delete(channelId);
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
  }

  @GetMapping
  public ResponseEntity<List<ChannelResponseDto>> findAll(@RequestParam("userId") UUID userId) {
    List<ChannelDto> channels = channelService.findAllByUserId(userId);
    List<ChannelResponseDto> channelResponseDto = channels.stream().map(channelMapper::toResponseDto).toList();
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(channelResponseDto);
  }
}
