package com.sprint.mission.discodeit.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "User 생성 정보")
public class UserCreateRequest {

    @Schema(description = "사용자 이름", example = "hyeok_dev")
    private String username;

    @Schema(description = "이메일 주소", example = "hyeok@example.com")
    private String email;

    @Schema(description = "비밀번호", example = "123456")
    private String password;


    public UserCreateRequest() {}

}

