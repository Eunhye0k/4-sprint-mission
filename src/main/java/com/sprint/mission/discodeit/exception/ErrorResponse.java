package com.sprint.mission.discodeit.exception;

import java.time.Instant;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
  private Instant timestamp;
  private String code;
  private String message;
  private Map<String, Object> details;
  private String exceptionType;
  private int status;
}
