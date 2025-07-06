package com.sprint.mission.discodeit.controller.message;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final BasicMessageService basicMessageService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createMessage(@ModelAttribute MessageCreateRequest messageCreateRequest,
                                        @RequestParam(value = "images", required = false) MultipartFile[] files) throws IOException {

        List<BinaryContentCreateRequest> fileRequests = Arrays.stream(files != null ? files : new MultipartFile[0])
                .map(file -> {
                    try {
                        return BinaryContentCreateRequest.from(file).orElse(null);
                    } catch (IOException e) {
                        throw new RuntimeException("파일 처리 중 오류 발생", e);
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        basicMessageService.create(messageCreateRequest, fileRequests);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = {"/{message-id}"}, method = RequestMethod.PATCH)
    public ResponseEntity updateMessage(@PathVariable(value = "message-id") UUID messageId,
            @RequestBody MessageUpdateRequest messageUpdateRequest) {
        basicMessageService.update(messageId, messageUpdateRequest);
        return ResponseEntity.ok(messageUpdateRequest);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteMessage(@RequestParam(value = "message-id") UUID messageId) {
        basicMessageService.delete(messageId);
        return ResponseEntity.ok(messageId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getMessage(@RequestParam(value = "channel-id") UUID channelId) {
        basicMessageService.findAllByChannelId(channelId);

        return ResponseEntity.ok(basicMessageService.findAllByChannelId(channelId));
    }
}
