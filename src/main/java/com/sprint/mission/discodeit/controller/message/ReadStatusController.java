package com.sprint.mission.discodeit.controller.message;

import com.sprint.mission.discodeit.dto.data.ReadStatusDto;
import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/readstatus")
public class ReadStatusController {
    private final ReadStatusService readStatusService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createReadStatus (@RequestBody ReadStatusCreateRequest readStatusCreateRequest) {
        readStatusService.create(readStatusCreateRequest);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = ("/{readStatus-id}"), method = RequestMethod.PATCH)
    public ResponseEntity updateReadStatus (@PathVariable (value = "readStatus-id") UUID readStatusId,
                                            @RequestBody ReadStatusUpdateRequest readStatusUpdateRequest) {

        readStatusService.update(readStatusId, readStatusUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = ("/{user-id}"), method = RequestMethod.GET)
    public ResponseEntity getReadStatus(@PathVariable("user-id") UUID readStatusId) {
        List<ReadStatus> readStatuses = readStatusService.findAllByUserId(readStatusId);

        return ResponseEntity.ok(readStatuses);
    }
}
