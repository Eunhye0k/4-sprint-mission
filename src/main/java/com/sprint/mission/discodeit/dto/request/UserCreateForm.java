package com.sprint.mission.discodeit.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@Schema(name = "UserCreateForm", description = "Swagger 전용 사용자 생성 요청 폼")
public class UserCreateForm {

    @Schema(description = "사용자 정보(JSON 형식)", implementation = UserCreateRequest.class)
    private String userCreateRequest;

    @Schema(description = "프로필 이미지", type = "string", format = "binary")
    private MultipartFile profile;
}
