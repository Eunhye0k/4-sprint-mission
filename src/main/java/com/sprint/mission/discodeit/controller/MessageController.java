package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Message Controller", description = "Message API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class MessageController {

  private final MessageService messageService;

  @PostMapping(
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE
  )
  @Operation(summary = "Message 생성")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Message 생성 성공",
                  content = @Content(
                          schema = @Schema(implementation = MessageCreateRequest.class),
                          examples = @ExampleObject(name = "Message 생성 성공 예시",
                                  summary = "정상적으로 Message가 생성된 경우 예시",
                                  value = "{\n" +
                                          "    \"content\": \"테스트1\",\n" +
                                          "    \"channelId\": \"a05b827e-a1a0-467d-8112-0a85f64948e7\",\n" +
                                          "    \"authorId\": \"ccc5fcbe-fd01-4ec2-8d22-32d49abc339f\",\n" +
                                          "    \"attachmentIds\": [\n" +
                                          "        \"9c043986-e64d-4a1c-9217-c200263e22af\"\n" +
                                          "    ],\n" +
                                          "    \"id\": \"a6903719-8026-490f-9c06-8a3264d63252\",\n" +
                                          "    \"createdAt\": \"2025-07-10T05:28:19.001270300Z\",\n" +
                                          "    \"updatedAt\": null\n" +
                                          "}")
                  )),
          @ApiResponse(responseCode = "404", description = "ChannelId 혹은 authorId을 찾을 수 없음",
                  content = @Content(
                          mediaType = "application/json",
                          examples = {@ExampleObject(name = "Message가 생성 실패 예시",
                                  summary = "존재하지 않는 ChannelId 혹은 authorId 값을 입력 했을 경우 예시",
                                  value = "{ \"message\": \"Channel with id {ChannelId} does not exist\" }")}))
  })
  public ResponseEntity<Message> create(
      @RequestPart("messageCreateRequest") MessageCreateRequest messageCreateRequest,
      @RequestPart(value = "attachments", required = false)
      @Parameter(description = "전송할 이미지 Message (선택)")
      List<MultipartFile> attachments
  ) {
    List<BinaryContentCreateRequest> attachmentRequests = Optional.ofNullable(attachments)
        .map(files -> files.stream()
            .map(file -> {
              try {
                return new BinaryContentCreateRequest(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
                );
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            })
            .toList())
        .orElse(new ArrayList<>());
    Message createdMessage = messageService.create(messageCreateRequest, attachmentRequests);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(createdMessage);
  }

  @PatchMapping("/{messageId}")
  @Operation(summary = "Message 업데이트")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Message 수정 성공",
                  content =  @Content(
                          schema = @Schema(implementation = MessageUpdateRequest.class),
                          examples = {@ExampleObject(name = "Message 수정 성공 예시",
                                  summary = "정상적으로 Message 수정 된 예시",
                                  value = "{\n" +
                                          "    \"content\": \"hello again test\",\n" +
                                          "    \"channelId\": \"a05b827e-a1a0-467d-8112-0a85f64948e7\",\n" +
                                          "    \"authorId\": \"ccc5fcbe-fd01-4ec2-8d22-32d49abc339f\",\n" +
                                          "    \"attachmentIds\": [\n" +
                                          "        \"b321c682-8aca-4613-a414-c1ba749abe93\"\n" +
                                          "    ],\n" +
                                          "    \"id\": \"f66908c6-ffb7-4b87-87fe-7e84c3bbffe7\",\n" +
                                          "    \"createdAt\": \"2025-07-10T05:38:20.735961900Z\",\n" +
                                          "    \"updatedAt\": \"2025-07-10T05:38:45.506351700Z\"\n" +
                                          "}")}
                  )),
          @ApiResponse(responseCode = "404", description = "Message 찾을 수 없음",
                  content = @Content(
                          mediaType = "application/json",
                          examples = {@ExampleObject(name = "Message 수정 실패 예시",
                                  summary = "존재하지 않는 MessageId 값을 입력 했을 경우 예시",
                                  value = "{ \"message\": \"Message with id {MessageId} not found\" }")}))

  })
  public ResponseEntity<Message> update(@PathVariable("messageId") UUID messageId,
      @RequestBody MessageUpdateRequest request) {
    Message updatedMessage = messageService.update(messageId, request);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(updatedMessage);
  }

  @DeleteMapping("/{messageId}")
  @Operation(summary = "Message 삭제")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Message 삭제 성공",
                  content = @Content(
                          examples = {@ExampleObject(name = "Message 삭제 성공 예시",
                                  summary = "정상적으로 Message 가 삭제 된 경우 예시",
                                  value = "1")}
                  )),

          @ApiResponse(responseCode = "404", description = "Message 찾을 수 없음",
                  content = @Content(
                          mediaType = "application/json",
                          examples = {@ExampleObject(name = "Message 삭제 실패 예시",
                                  summary = "존재하지 않는 MessageId 값을 입력 했을 경우 예시",
                                  value = "{ \"message\": \"Message with id {MessageId} not found\" }")}))
  })
  public ResponseEntity<Void> delete(@PathVariable("messageId")
                                     @Parameter(description = "삭제할 messageId 입력")
                                       UUID messageId) {
    messageService.delete(messageId);
    return ResponseEntity
            .noContent().build();
  }

  @GetMapping()
  @Operation(summary = "특정 채널의 Message 목록 조회")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "특정 채널의 Message 목록 조회 성공",
                  content = @Content(
                          examples = {@ExampleObject(name = "특정 채널 Message 목록 조회 성공 예시",
                                  summary = "정상적으로 특정 채널의 Message 목록 조회가 된 경우 예시",
                                  value = "[\n" +
                                          "    {\n" +
                                          "        \"content\": \"테스트1\",\n" +
                                          "        \"channelId\": \"a05b827e-a1a0-467d-8112-0a85f64948e7\",\n" +
                                          "        \"authorId\": \"ccc5fcbe-fd01-4ec2-8d22-32d49abc339f\",\n" +
                                          "        \"attachmentIds\": [\n" +
                                          "            \"9c043986-e64d-4a1c-9217-c200263e22af\"\n" +
                                          "        ],\n" +
                                          "        \"id\": \"a6903719-8026-490f-9c06-8a3264d63252\",\n" +
                                          "        \"createdAt\": \"2025-07-10T05:28:19.001270300Z\",\n" +
                                          "        \"updatedAt\": null\n" +
                                          "    },\n" +
                                          "    {\n" +
                                          "        \"content\": \"테스트1\",\n" +
                                          "        \"channelId\": \"a05b827e-a1a0-467d-8112-0a85f64948e7\",\n" +
                                          "        \"authorId\": \"ccc5fcbe-fd01-4ec2-8d22-32d49abc339f\",\n" +
                                          "        \"attachmentIds\": [\n" +
                                          "            \"cd28ed7e-cb40-4d0b-bfc3-a7c781ea0e20\"\n" +
                                          "        ],\n" +
                                          "        \"id\": \"ee32240f-bc7a-49fa-a565-68767b72b8c2\",\n" +
                                          "        \"createdAt\": \"2025-07-10T05:28:54.344779900Z\",\n" +
                                          "        \"updatedAt\": null\n" +
                                          "    }\n" +
                                          "]"
                          )})
          )
  })
  public ResponseEntity<List<Message>> findAllByChannelId(
      @RequestParam("channelId")
      @Parameter(description = "조회 할 channelId")
      UUID channelId) {
    List<Message> messages = messageService.findAllByChannelId(channelId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(messages);
  }
}
