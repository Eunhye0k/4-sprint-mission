package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.LoginRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "Auth Controller", description = "Auth API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  @Operation(summary = "로그인")
  @PostMapping()
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "로그인 성공",
                  content = @Content(
                          schema = @Schema(implementation = UserCreateRequest.class),
                          examples = @ExampleObject(name = "로그인 성공 예시",
                                  summary = "정상적으로 로그인이 된 경우 예시",
                                  value = "{\n" +
                                          "    \"username\": \"11\",\n" +
                                          "    \"email\": \"11@a.com\",\n" +
                                          "    \"password\": \"11\",\n" +
                                          "    \"profileId\": null,\n" +
                                          "    \"id\": \"d5bd832e-e46b-4510-af53-c4b87bf8e1da\",\n" +
                                          "    \"createdAt\": \"2025-07-10T06:08:52.257774700Z\",\n" +
                                          "    \"updatedAt\": null\n" +
                                          "}"))),
          @ApiResponse(responseCode = "400", description = "로그인 실패",
                  content = @Content(
                          schema = @Schema(implementation = UserCreateRequest.class),
                          examples = @ExampleObject(name = "로그인 실패 예시",
                                  summary = "알맞지않은 password 를 입력 했을 경우",
                                  value = "{\n" +
                                          "    \"message\": \"Wrong password\"\n" +
                                          "}"))),
          @ApiResponse(responseCode = "404", description = "로그인 실패",
                  content = @Content(
                          schema = @Schema(implementation = UserCreateRequest.class),
                          examples = @ExampleObject(name = "로그인 실패 예시",
                                  summary = "존재하지않는 username 을 입력 했을 경우",
                                  value = "{\n" +
                                          "    \"message\": \"User with username {username} not found\"\n" +
                                          "}")))
  })
  public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
    User user = authService.login(loginRequest);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(user);
  }
}
