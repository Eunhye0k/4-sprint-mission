package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.mapper.UserDtoMapper;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
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
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "User Controller", description = "User API")
@OpenAPIDefinition(
        info = @Info(title = "Discodeit API 문서", version = "v1",
                description = "Discodeit 프로젝트의 Swagger API 문서입니다.")
)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final UserStatusService userStatusService;


  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "User 생성")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "User 생성 성공",
                  content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                          schema = @Schema(implementation = UserDto.class),
                          examples = @ExampleObject(name = "User 생성 성공 예시",
                                  summary = "정상적으로 User가 생성된 경우 예시",
                                  value = "{\n" +
                                          "  \"username\": \"hyeok_dev\",\n" +
                                          "  \"email\": \"hyeok@example.com\",\n" +
                                          "  \"password\" : \"123\",\n" +
                                          "  \"profileId\": \"9d98c5b3-7a7e-4a96-bb42-5f3e1149e1c4\",\n" +
                                          "  \"id\": \"a3f88e34-67bd-4c7f-a5f4-02f97881c1a1\",\n" +
                                          "  \"createdAt\": \"2025-07-09T10:15:00Z\",\n" +
                                          "  \"updatedAt\": \"2025-07-09T11:45:00Z\"\n" +
                                          "}"))),

          @ApiResponse(responseCode = "400", description = "같은 email 또는 username을 사용하는 User가 이미 존재함",
                  content = @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          examples = @ExampleObject(name = "User 생성 실패 예시",
                                  summary = "중복 값을 입력 했을 경우 예시",
                                  value = "{ \"message\": \"User with email {email} already exists\" }")
                  ))
  })
  public ResponseEntity<UserDto> create(
          @RequestPart @Parameter(description = "사용자 이름") String username,
          @RequestPart @Parameter(description = "이메일") String email,
          @RequestPart @Parameter(description = "비밀번호") String password,
          @RequestPart(value = "profile", required = false)
          @Parameter(description = "User 프로필 이미지 (선택)") MultipartFile profile
  ) {
    // DTO 수동 생성
    UserCreateRequest userCreateRequest = new UserCreateRequest();
    userCreateRequest.setUsername(username);
    userCreateRequest.setEmail(email);
    userCreateRequest.setPassword(password);

    Optional<BinaryContentCreateRequest> profileRequest = Optional.ofNullable(profile)
            .flatMap(this::resolveProfileRequest);

    User createdUser = userService.create(userCreateRequest, profileRequest);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(UserDtoMapper.toDto(createdUser));
  }

  @PatchMapping(
      path = "/{userId}",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
  )
  @Operation(summary = "User 수정")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User 수정 성공",
          content =  @Content(
                  schema = @Schema(implementation = UserUpdateRequest.class),
                  examples = {@ExampleObject(name = "User 수정 성공 예시",
                                            summary = "정상적으로 User가 수정 된 예시",
                                            value = "{\n" +
                                                    "  \"username\": \"eunhyeok_dev\",\n" +
                                                    "  \"email\": \"eunhyeok@example.com\",\n" +
                                                    "  \"password\" : \"112233\",\n" +
                                                    "  \"profileId\": \"9d98c5b3-7a7e-4a96-bb42-5f3e1149e1c4\",\n" +
                                                    "  \"id\": \"a3f88e34-67bd-4c7f-a5f4-02f97881c1a1\",\n" +
                                                    "  \"createdAt\": \"2025-07-09T10:15:00Z\",\n" +
                                                    "  \"updatedAt\": \"2025-07-09T11:45:00Z\"\n" +
                                                    "}")}
          )),
          @ApiResponse(responseCode = "400", description = "같은 email 또는 username을 사용하는 User가 이미 존재함",
                  content = @Content(
                          mediaType = "application/json",
                          examples = {@ExampleObject(name = "User 수정 실패 예시",
                                  summary = "중복 값을 입력 했을 경우 예시",
                                  value = "{ \"message\": \"User with email {email} already exists\" }")})),
          @ApiResponse(responseCode = "404", description = "User를 찾을 수 없음",
                  content = @Content(
                          mediaType = "application/json",
                          examples = {@ExampleObject(name = "User 수정 실패 예시",
                                  summary = "존재하지 않는 userId 값을 입력 했을 경우 예시",
                                  value = "{ \"message\": \"User with id {userId} not found\" }")}))

  })
  public ResponseEntity<User> update(
      @PathVariable("userId")
      @Parameter(description = "수정할 User ID") UUID userId,
      @RequestPart @Parameter(description = "수정할 사용자 이름") String username,
      @RequestPart @Parameter(description = "수정할 이메일") String email,
      @RequestPart @Parameter(description = "수정할 비밀번호") String password,
      @RequestPart(value = "profile", required = false)
      @Parameter(description = "수정할 User 프로필 이미지 (선택)")
      MultipartFile profile
  ) {
    UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
    userUpdateRequest.setNewUsername(username);
    userUpdateRequest.setNewEmail(email);
    userUpdateRequest.setNewPassword(password);

    Optional<BinaryContentCreateRequest> profileRequest = Optional.ofNullable(profile)
        .flatMap(this::resolveProfileRequest);
    User updatedUser = userService.update(userId, userUpdateRequest, profileRequest);
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/{userId}")
  @Operation(summary = "User 삭제")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "User 삭제 성공",
          content = @Content(
                  examples = {@ExampleObject(name = "User 삭제 성공 예시",
                  summary = "정상적으로 User 가 삭제 된 경우 예시",
                  value = "1")}
          )),

          @ApiResponse(responseCode = "404", description = "User를 찾을 수 없음",
                  content = @Content(
                          mediaType = "application/json",
                          examples = {@ExampleObject(name = "User 삭제 실패 예시",
                                  summary = "존재하지 않는 userId를 입력 했을 경우 예시",
                                  value = "{ \"message\": \"User with {userId} not found\" }")}))
  })
  public ResponseEntity<Void> delete(@PathVariable("userId")
                                       @Parameter(description = "삭제할 User ID") UUID userId) {
    userService.delete(userId);
    return ResponseEntity
            .noContent().build();
  }

  @GetMapping
  @Operation(summary = "User 전체 조회")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User 전체 조회 성공",
          content = @Content(
                  examples = {@ExampleObject(name = "User 전체 조회 성공 예시",
                  summary = "정상적으로 User 전체 조회가 된 경우 예시",
                  value = "{\n" +
                          "  \"id\": \"a3f88e34-67bd-4c7f-a5f4-02f97881c1a1\",\n" +
                          "  \"createdAt\": \"2025-07-09T10:15:00Z\",\n" +
                          "  \"updatedAt\": \"2025-07-09T11:45:00Z\",\n" +
                          "  \"username\": \"eunhyeok_dev\",\n" +
                          "  \"email\": \"eunhyeok@example.com\",\n" +
                          "  \"profileId\": \"9d98c5b3-7a7e-4a96-bb42-5f3e1149e1c4\",\n" +
                          "  \"online\" : \"ture\" or \"false\",\n" +
                          "}"
+
                          "{\n" +
                          "  \"id\": \"0ea99af1-4eba-4e74-a5fb-92923d97c567\",\n" +
                          "  \"createdAt\": \"2025-07-09T09:32:58.620275400Z\",\n" +
                          "  \"updatedAt\": \"null\",\n" +
                          "  \"username\": \"abcd\",\n" +
                          "  \"email\": \"1a1@a.com\",\n" +
                          "  \"profileId\": \"null\",\n" +
                          "  \"online\" : \"ture\" or \"false\",\n" +
                          "}"
                  )})
          )
  })
  public ResponseEntity<List<UserDto>> findAll() {
    List<UserDto> users = userService.findAll();
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(users);
  }

  @PatchMapping(path = "/{userId}/userStatus")
  @Operation(summary = "User 온라인 상태 업데이트")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "User 온라인 업데이트 성공",
          content = @Content(
                  schema = @Schema(implementation = UserStatusUpdateRequest.class),
                  examples = {@ExampleObject(name = "User 온라인 상태 업데이트 성공 예시",
                  summary = "정상적으로 User 온라인 상태가 업데이트 됐을 경우 예시")
                  }
          )),
          @ApiResponse(responseCode = "404", description = "User를 찾을 수 없음",
          content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                          implementation = UserStatusUpdateRequest.class,
                  type = "object"),
                  examples = {@ExampleObject(name = "User 온라인 상태 업데이트 실패 예시",
                  summary = "존재하지 않는 userId를 입력 했을 경우 예시",
                  value = "{ \"message\": \"UserStatus with {userId} not found\" }")}
          )),
  })
  public ResponseEntity<UserStatus> updateUserStatusByUserId(@PathVariable("userId")
                                                             @Parameter(description = "업데이트 할 User ID") UUID userId,
                                                             @RequestBody UserStatusUpdateRequest request) {
    UserStatus updatedUserStatus = userStatusService.updateByUserId(userId, request);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(updatedUserStatus);
  }


  //추후 @GlobalExceptionHandler 에서 예외처리 진행cd 
  private Optional<BinaryContentCreateRequest> resolveProfileRequest(MultipartFile profileFile) {
    if (profileFile.isEmpty()) {
      return Optional.empty();
    } else {
      try {
        BinaryContentCreateRequest binaryContentCreateRequest = new BinaryContentCreateRequest(
            profileFile.getOriginalFilename(),
            profileFile.getContentType(),
            profileFile.getBytes()
        );
        return Optional.of(binaryContentCreateRequest);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
