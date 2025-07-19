package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
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

@Tag(name = "Channel Controller" ,description = "Channel API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/channels")
public class ChannelController {

  private final ChannelService channelService;


  @PostMapping("/public")
  @Operation(summary = "Public Channel 생성")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Public Channel 생성 성공",
                  content = @Content(
                          schema = @Schema(implementation = PublicChannelCreateRequest.class),
                          examples = @ExampleObject(name = "Channel 생성 성공 예시",
                                  summary = "정상적으로 Channel이 생성된 경우 예시",
                                  value = "{\n" +
                                          "    \"type\": \"PUBLIC\",\n" +
                                          "    \"name\": \"discodeit\",\n" +
                                          "    \"description\": \"discodeChannel\",\n" +
                                          "    \"id\": \"a05b827e-a1a0-467d-8112-0a85f64948e7\",\n" +
                                          "    \"createdAt\": \"2025-07-10T04:23:46.092576500Z\",\n" +
                                          "    \"updatedAt\": null\n" +
                                          "}")
                  ))
  })
  public ResponseEntity<Channel> create(@RequestBody PublicChannelCreateRequest request) {
    Channel createdChannel = channelService.create(request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(createdChannel);
  }

  @PostMapping("/private")
  @Operation(summary = "Private Channel 생성")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Public Channel 생성 성공",
                  content = @Content(
                          schema = @Schema(implementation = PrivateChannelCreateRequest.class),
                          examples = @ExampleObject(name = "Channel 생성 성공 예시",
                                  summary = "정상적으로 Channel이 생성된 경우 예시",
                                  value = "{\n" +
                                          "    \"type\": \"PRIVATE\",\n" +
                                          "    \"name\": null,\n" +
                                          "    \"description\": null,\n" +
                                          "    \"id\": \"7ea49f9e-13f6-412e-9042-a7c2ba2c5e7c\",\n" +
                                          "    \"createdAt\": \"2025-07-10T04:41:56.896936700Z\",\n" +
                                          "    \"updatedAt\": null\n" +
                                          "}")
                  ))
  })
  public ResponseEntity<Channel> create(@RequestBody PrivateChannelCreateRequest request) {
    Channel createdChannel = channelService.create(request);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(createdChannel);
  }

  @PatchMapping("/{channelId}")
  @Operation(summary = "Public Channel 업데이트")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Public Channel 수정 성공",
                  content = @Content(
                          schema = @Schema(implementation = PublicChannelUpdateRequest.class),
                          examples = @ExampleObject(name = "Public Channel 수정 성공 예시",
                                  summary = "정상적으로 Public Channel이 수정된 경우 예시",
                                  value = "{\n" +
                                          "    \"type\": \"PUBLIC\",\n" +
                                          "    \"name\": \"hellocodeit\",\n" +
                                          "    \"description\": \"new discord\",\n" +
                                          "    \"id\": \"015de460-ba32-4dab-9f30-5ac4b8798c9a\",\n" +
                                          "    \"createdAt\": \"2025-07-10T04:37:50.858166800Z\",\n" +
                                          "    \"updatedAt\": \"2025-07-10T04:47:31.423764400Z\"\n" +
                                          "}")
                  )),
          @ApiResponse(responseCode = "404", description = "Channel을 찾을 수 없음",
                  content = @Content(
                          mediaType = "application/json",
                          examples = {@ExampleObject(name = "Channel 삭제 실패 예시",
                                  summary = "존재하지 않는 ChannelId 값을 입력 했을 경우 예시",
                                  value = "{ \"message\": \"User with id {ChannelId} not found\" }")}))
  })
  public ResponseEntity<Channel> update(
                   @PathVariable("channelId")
                   @Parameter(description = "수정할 ChannelId")
                   UUID channelId,
                  @RequestBody PublicChannelUpdateRequest request) {
    Channel udpatedChannel = channelService.update(channelId, request);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(udpatedChannel);
  }

  @DeleteMapping("/{channelId}")
  @Operation(summary = "Channel 삭제")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Channel 삭제 성공",
                  content = @Content(
                          schema = @Schema(implementation = ChannelDto.class),
                          examples = @ExampleObject(name = "Channel 삭제 성공 예시",
                                  summary = "정상적으로 Channel이 삭제 경우 예시",
                                  value = "1")
                  )),
          @ApiResponse(responseCode = "404", description = "Channel을 찾을 수 없음",
                  content = @Content(
                          mediaType = "application/json",
                          examples = {@ExampleObject(name = "Channel 삭제 실패 예시",
                                  summary = "존재하지 않는 ChannelId 값을 입력 했을 경우 예시",
                                  value = "{ \"message\": \"User with id {ChannelId} not found\" }")}))
  })
  public ResponseEntity<Void> delete(@PathVariable("channelId")
                                     @Parameter(description = "삭제할 ChannelId")
                                       UUID channelId) {
    channelService.delete(channelId);
    return ResponseEntity
            .noContent().build();
  }

  @Operation(summary = "유저가 참여중인 Channel 목록 조회")
  @GetMapping()
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "유저가 참여중인 Channel 목록 조회 성공",
                  content = @Content(
                          examples = {@ExampleObject(name = "Channel 목록 조회 성공 예시",
                                  summary = "정상적으로 Channel 목록 조회가 된 경우 예시",
                                  value = "{\n" +
                                          "        \"id\": \"9c1a7b27-c60c-4003-b15a-8a3b4ed6291f\",\n" +
                                          "        \"type\": \"PRIVATE\",\n" +
                                          "        \"name\": null,\n" +
                                          "        \"description\": null,\n" +
                                          "        \"participantIds\": [\n" + "\"5ab8bf07-3747-4054-a3d2-503ec9f17dd6\"\n" +
                                          "        ],\n" +
                                          "        \"lastMessageAt\": \"-1000000000-01-01T00:00:00Z\"\n" +
                                          "    },\n" +
                                          "    {\n" +
                                          "        \"id\": \"a05b827e-a1a0-467d-8112-0a85f64948e7\",\n" +
                                          "        \"type\": \"PUBLIC\",\n" +
                                          "        \"name\": \"discodeit\",\n" +
                                          "        \"description\": \"discodeChannel\",\n" +
                                          "        \"participantIds\": [],\n" +
                                          "        \"lastMessageAt\": \"-1000000000-01-01T00:00:00Z\"\n" +
                                          "    }"
                          )})
          )
  })
  public ResponseEntity<List<ChannelDto>> findAll(@RequestParam("userId")
                                                  @Parameter(description = "조회할 UserId")
                                                  UUID userId) {
    List<ChannelDto> channels = channelService.findAllByUserId(userId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(channels);
  }
}
