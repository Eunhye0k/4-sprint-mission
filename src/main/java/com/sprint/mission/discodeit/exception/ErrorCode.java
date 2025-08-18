package com.sprint.mission.discodeit.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "유저를 찾을 수 없습니다.", "해당 유저를 찾을 수 없습니다."),
  DUPLICATE_USER(HttpStatus.CONFLICT.value(), "잘못된 요청 입니다.","유저이름이 이미 존재합니다." ),
  DUPLICATE_EMAIL(HttpStatus.CONFLICT.value(), "잘못된 요청 입니다.", "이메일이 이미 존재합니다"),
  CHANNEL_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "채널을 찾을 수 없습니다.", "해당 채널을 찾을 수 없습니다."),
  PRIVATE_CHANNEL_NOT_UPDATE(HttpStatus.BAD_REQUEST.value(), "채널을 수정할 수 없습니다.", "비공개 채널은 수정할 수 없습니다."),
  MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND.value(),  "메세지를 찾을 수 없습니다", "해당 메세지를 찾을 수 없습니다.");

  private final int status;
  private final String message;
  private final String detail;
}
