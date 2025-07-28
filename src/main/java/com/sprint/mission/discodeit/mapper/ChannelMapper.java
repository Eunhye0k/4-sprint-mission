package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.response.ChannelResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, MessageMapper.class, ReadStatusMapper.class})
public interface ChannelMapper {
    //ChannelDto toDto(Channel channel);
    ChannelResponseDto toResponseDto(ChannelDto channelDto);
}
