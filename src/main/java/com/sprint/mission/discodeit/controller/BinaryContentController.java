package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
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

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Tag(name = "BinaryContent Controller", description = "BinaryContent API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/binaryContents")
public class BinaryContentController {

  private final BinaryContentService binaryContentService;

  @Operation(summary = "첨부 파일 조회")
  @GetMapping(path = "/{binaryContentId}")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "binaryContent 조회 성공",
                  content = @Content(
                          examples = @ExampleObject(name = "binaryContent 조회 성공 예시",
                                  summary = "정상적으로 binaryContent 조회 성공한 경우 예시",
                                  value = "{\n" +
                                          "  \"fileName\": \"KakaoTalk_20250628_134250733_03.jpg\" ,\n" +
                                          "  \"size\": \" 315431\",\n" +
                                          "  \"contentType\": \"image/jpeg\",\n"+
                                          "  \"bytes\": \"Base64 encoded image data\",\n" +
                                          "  \"id\": \"0a1f04d6-06e5-4a8c-8eb2-09d8a633865b\",\n" +
                                          "  \"createdAt\": \"2025-07-10T06:22:25.198639600Z\",\n" +
                                          "}")
                  )),
          @ApiResponse(responseCode = "404", description = "binaryContent를 찾을 수 없음",
                  content = @Content(
                          mediaType = "application/json",
                          examples = {@ExampleObject(name = "binaryContent 조회 실패 예시",
                                  summary = "존재하지 않는 binaryContentId 값을 입력 했을 경우 예시",
                                  value = "{ \"message\": \"BinaryContent with id {BinaryContentId} not found\" }")}))
  })
  public ResponseEntity<BinaryContent> find(@PathVariable("binaryContentId")
                                            @Parameter(description = "조회할 binaryContentId")
                                              UUID binaryContentId) {
    BinaryContent binaryContent = binaryContentService.find(binaryContentId);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(binaryContent);
  }

  @Operation(summary = "첨부 파일 다중 조회")
  @GetMapping()
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "binaryContent 다중 조회 성공",
                  content = @Content(
                          examples = @ExampleObject(name = "binaryContent 다중 조회 성공 예시",
                                  summary = "정상적으로 binaryContent 다중 조회 성공한 경우 예시",
                                  value = "{\n" +
                                          "  \"fileName\": \"KakaoTalk_20250628_134250733_03.jpg\" ,\n" +
                                          "  \"size\": \" 315431\",\n" +
                                          "  \"contentType\": \"image/jpeg\",\n"+
                                          "  \"bytes\": \"Base64 encoded image data\",\n" +
                                          "  \"id\": \"0a1f04d6-06e5-4a8c-8eb2-09d8a633865b\",\n" +
                                          "  \"createdAt\": \"2025-07-10T06:22:25.198639600Z\",\n" +
                                          "}"+
                                          "{\n" +
                                          "  \"fileName\": \"KakaoTalk_20250628_134250733_01.jpg\" ,\n" +
                                          "  \"size\": \" 392070\",\n" +
                                          "  \"contentType\": \"image/jpeg\",\n"+
                                          "  \"bytes\": \"Base64 encoded image data\",\n" +
                                          "  \"id\": \"454d8a17-cfbd-4884-895e-bf60b44bb95c\",\n" +
                                          "  \"createdAt\": \"2025-07-10T06:35:54.335722300Z\",\n" +
                                          "}")
                  )),
          @ApiResponse(responseCode = "404", description = "binaryContent를 찾을 수 없음",
                  content = @Content(
                          mediaType = "application/json",
                          examples = {@ExampleObject(name = "binaryContent 조회 실패 예시",
                                  summary = "존재하지 않는 binaryContentId 값을 입력 했을 경우 예시",
                                  value = "{ \"message\": \"BinaryContent with id {BinaryContentId} not found\" }")}))
  })
  public ResponseEntity<List<BinaryContent>> findAllByIdIn(
      @RequestParam("binaryContentIds")
      @Parameter(description = "조회할 binaryContentIds") List<UUID> binaryContentIds) {
    List<BinaryContent> binaryContents = binaryContentService.findAllByIdIn(binaryContentIds);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(binaryContents);
  }
}
