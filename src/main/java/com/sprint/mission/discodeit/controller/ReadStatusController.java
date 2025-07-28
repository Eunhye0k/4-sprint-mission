package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.controller.api.ReadStatusApi;
import com.sprint.mission.discodeit.dto.data.ReadStatusDto;
import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.response.ReadStatusResponseDto;
import com.sprint.mission.discodeit.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/readStatuses")
public class ReadStatusController implements ReadStatusApi {

  private final ReadStatusService readStatusService;
  private final ReadStatusMapper readStatusMapper;

  @PostMapping
  public ResponseEntity<ReadStatusResponseDto> create(@RequestBody ReadStatusCreateRequest request) {
    ReadStatusDto createdReadStatus = readStatusService.create(request);
    ReadStatusResponseDto readStatusResponseDto = readStatusMapper.toResponseDto(createdReadStatus);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(readStatusResponseDto);
  }

  @PatchMapping(path = "{readStatusId}")
  public ResponseEntity<ReadStatusResponseDto> update(@PathVariable("readStatusId") UUID readStatusId,
                                                      @RequestBody ReadStatusUpdateRequest request) {
    ReadStatusDto updatedReadStatus = readStatusService.update(readStatusId, request);
    ReadStatusResponseDto readStatusResponseDto = readStatusMapper.toResponseDto(updatedReadStatus);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(readStatusResponseDto);
  }

  @GetMapping
  public ResponseEntity<List<ReadStatusResponseDto>> findAllByUserId(@RequestParam("userId") UUID userId) {
    List<ReadStatusDto> readStatuses = readStatusService.findAllByUserId(userId);
    List<ReadStatusResponseDto> readStatusResponseDto = readStatuses.stream().map(readStatusMapper::toResponseDto).toList();
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(readStatusResponseDto);
  }
}
