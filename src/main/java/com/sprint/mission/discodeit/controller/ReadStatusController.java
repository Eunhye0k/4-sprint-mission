package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "ReadStatus Controller", description = "ReadStatus API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/readStatuses")
public class ReadStatusController {

  private final ReadStatusService readStatusService;

  @Operation(summary = "Message 읽음 상태 생성")
  @PostMapping()
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Message 읽음 상태 생성 성공",
                  content = @Content(
                          schema = @Schema(implementation = ReadStatusCreateRequest.class),
                          examples = {@ExampleObject(name = "Message 읽음 상태가 성공적으로 생성됨",
                                  summary = "정상적으로 Message 읽음 상태가 생성된 경우 예시",
                                  value = "{\n" +
                                          "    \"userId\": \"e23fed94-056d-4b42-ae52-ffc290fe7c27\",\n" +
                                          "    \"channelId\": \"ca0f7d7f-c96a-4808-b5b9-3f1e878a8158\",\n" +
                                          "    \"lastReadAt\": null,\n" +
                                          "    \"id\": \"7fee0cf7-fd0c-4526-a586-713dfee49ea1\",\n" +
                                          "    \"createdAt\": \"2025-07-10T07:34:12.936283400Z\",\n" +
                                          "    \"updatedAt\": null\n" +
                                          "}")}
                  )),

          @ApiResponse(responseCode = "400", description = "이미 읽음 상태가 존재함",
                  content = @Content(
                          mediaType = "application/json",
                          examples = {@ExampleObject(name = "Message 읽음 상태 생성 실패 예시",
                                  value = "{ \"message\": \"ReadStatus with userId {userId} and channelId {channelId} already exists\" }")}
                  )),

          @ApiResponse(responseCode = "404", description = "User 혹은 Channel 을 찾을 수 없음",
                  content = @Content(
                          mediaType = "application/json",
                          examples = {@ExampleObject(name = "Message 읽음 상태 생성 실패 예시",
                                  summary = "존재하지 않는 userId 혹은 ChannelId 를 입력 했을 경우 예시",
                                  value = "{ \"message\": \"User or Channel with {userId} does not exist\" }")}
                  ))
  })
  public ResponseEntity<ReadStatus> create(@RequestBody ReadStatusCreateRequest request) {
    ReadStatus createdReadStatus = readStatusService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdReadStatus);
  }

  @Operation(summary = "Message 읽음 상태 수정")
  @PatchMapping("/{readStatusId}")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Message 읽음 상태 수정 성공",
                  content = @Content(
                          schema = @Schema(implementation = ReadStatusCreateRequest.class),
                          examples = {@ExampleObject(name = "Message 읽음 상태가 성공적으로 수정됨",
                                  summary = "정상적으로 Message 읽음 상태가 수정된 경우 예시",
                                  value = "{\n" +
                                          "    \"userId\": \"e23fed94-056d-4b42-ae52-ffc290fe7c27\",\n" +
                                          "    \"channelId\": \"ca0f7d7f-c96a-4808-b5b9-3f1e878a8158\",\n" +
                                          "    \"lastReadAt\": \"2025-07-06T08:30:00Z\",\n" +
                                          "    \"id\": \"7fee0cf7-fd0c-4526-a586-713dfee49ea1\",\n" +
                                          "    \"createdAt\": \"2025-07-10T07:34:12.936283400Z\",\n" +
                                          "    \"updatedAt\": \"2025-07-10T07:34:48.495831Z\"\n" +
                                          "}")}
                  )),
          @ApiResponse(responseCode = "404", description = "message를 찾을 수 없음",
                  content = @Content(
                          mediaType = "application/json",
                          examples = {@ExampleObject(name = "Message 읽음 상태 수정 실패",
                                  summary = "존재하지 않는 messageId 를 입력 했을 경우 예시",
                                  value = "{ \"message\": \"ReadStatus with id {messageId} not found\" }")}
                  ))
  })
  public ResponseEntity<ReadStatus> update(@PathVariable("readStatusId")
                                           @Parameter(description = "변경할 messageId 입력")
                                             UUID readStatusId,
                                           @RequestBody ReadStatusUpdateRequest request) {
    ReadStatus updatedReadStatus = readStatusService.update(readStatusId, request);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(updatedReadStatus);
  }

  @Operation(summary = "User의 Message 읽음 상태 목록 조회")
  @GetMapping()
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User의 Message 읽음 상태 조회 성공",
                  content = @Content(
                          schema = @Schema(implementation = ReadStatusCreateRequest.class),
                          examples = {@ExampleObject(name = "User의 Message 읽음 상태가 성공적으로 조회됨",
                                  summary = "정상적으로 Message 읽음 상태가 조회된 경우 예시",
                                  value = "{\n" +
                                          "        \"userId\": \"e23fed94-056d-4b42-ae52-ffc290fe7c27\",\n" +
                                          "        \"channelId\": \"a05b827e-a1a0-467d-8112-0a85f64948e7\",\n" +
                                          "        \"lastReadAt\": null,\n" +
                                          "        \"id\": \"7e38195f-42fa-4afd-a724-50ca69bf4338\",\n" +
                                          "        \"createdAt\": \"2025-07-10T07:03:53.932259700Z\",\n" +
                                          "        \"updatedAt\": null\n" +
                                          "    }")}
                  ))
  })
  public ResponseEntity<List<ReadStatus>> findAllByUserId(@RequestParam("userId")
                                                          @Parameter(description = "조회할 userId")
                                                            UUID userId) {
    List<ReadStatus> readStatuses = readStatusService.findAllByUserId(userId);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(readStatuses);
  }
}
