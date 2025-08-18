package com.sprint.mission.discodeit.exception;

import com.sprint.mission.discodeit.exception.user.UserException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DiscodeitException.class)
  public ResponseEntity<ErrorResponse> handleDiscodeitException(DiscodeitException e) {
    ErrorCode errorCode = e.getErrorCode();

    ErrorResponse errorResponse = ErrorResponse.builder()
        .status(errorCode.getStatus())
        .message(errorCode.getMessage())
        .details(e.getDetails())
        .exceptionType(e.getClass().getName())
        .status(errorCode.getStatus())
        .build();

    return ResponseEntity
        .status(e.getErrorCode().getStatus())
        .body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    Map<String, Object> details = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      details.put(error.getField(), error.getDefaultMessage());
    }

    ErrorResponse errorResponse = ErrorResponse.builder()
        .timestamp(Instant.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .exceptionType(ex.getClass().getSimpleName())
        .code("INVALID_REQUEST")
        .message("유효성 검증 실패")
        .details(details)
        .build();

    return ResponseEntity.badRequest().body(errorResponse);
  }

}
