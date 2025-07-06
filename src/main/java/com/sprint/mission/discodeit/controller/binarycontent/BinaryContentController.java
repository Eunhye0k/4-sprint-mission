package com.sprint.mission.discodeit.controller.binarycontent;

import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/binary-contents")
public class BinaryContentController {
    private final BinaryContentService binaryContentService;

    @RequestMapping(method = RequestMethod.GET, value = ("/{binary-content-id}"))
        public ResponseEntity getBinaryContnet(@PathVariable("binary-content-id") UUID binaryContentId){
            return ResponseEntity.ok(binaryContentService.find(binaryContentId));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getBinaryContents(@RequestParam("binary-content-ids") List<UUID> binaryContentIds){
        return ResponseEntity.ok(binaryContentService.findAllByIdIn(binaryContentIds));
    }
}
