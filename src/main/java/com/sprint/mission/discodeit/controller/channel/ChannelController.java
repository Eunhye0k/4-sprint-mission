package com.sprint.mission.discodeit.controller.channel;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.*;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/channels")
public class ChannelController {
    private final BasicChannelService basicChannelService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createChannel (@RequestBody ChannelCreateRequest channelCreateRequest) throws IOException {

        if(channelCreateRequest.type().equals("public")){
            PublicChannelCreateRequest publicChannelCreateRequest = new PublicChannelCreateRequest(channelCreateRequest.name(), channelCreateRequest.description());
            basicChannelService.createPublic(publicChannelCreateRequest);
        }else if(channelCreateRequest.type().equals("private")){
            PrivateChannelCreateRequest privateChannelCreateRequest = new PrivateChannelCreateRequest(channelCreateRequest.participantIds());
            basicChannelService.createPrivate(privateChannelCreateRequest);
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{channel-id}", method = RequestMethod.PATCH)
    public ResponseEntity updatePublicChannel (@PathVariable("channel-id") UUID channelId
                                                ,@RequestBody PublicChannelUpdateRequest publicChannelUpdateRequest) throws IOException {

        basicChannelService.update(channelId, publicChannelUpdateRequest);

        return ResponseEntity.ok(publicChannelUpdateRequest.newName());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteChannel (@RequestParam("channel-id") UUID channelId) throws IOException {
            basicChannelService.delete(channelId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getChannels (@RequestParam("user-id") UUID userId) throws IOException {

        List<ChannelDto> channels = basicChannelService.findAllByUserId(userId);

        return ResponseEntity.ok(channels);
    }
}
