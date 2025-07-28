package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.data.UserStatusDto;
import com.sprint.mission.discodeit.dto.response.UserStatusResponseDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserStatusMapper {
    UserStatusDto toDto(UserStatus userStatus);
    UserStatusResponseDto toResponseDto(UserStatusDto userStatusDto);

}