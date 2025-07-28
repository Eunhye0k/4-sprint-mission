package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.controller.api.BinaryContentApi;
import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import com.sprint.mission.discodeit.dto.response.BinaryContentResponseDto;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/binaryContents")
public class BinaryContentController implements BinaryContentApi {

  private final BinaryContentService binaryContentService;
  private final BinaryContentMapper binaryContentMapper;

  @GetMapping(path = "{binaryContentId}")
  public ResponseEntity<BinaryContentResponseDto> find(@PathVariable("binaryContentId") UUID binaryContentId) {
    BinaryContentDto binaryContent = binaryContentService.find(binaryContentId);
    BinaryContentResponseDto binaryContentResponseDto = binaryContentMapper.toResponseDto(binaryContent);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(binaryContentResponseDto);
  }

  @GetMapping
  public ResponseEntity<List<BinaryContentResponseDto>> findAllByIdIn(
      @RequestParam("binaryContentIds") List<UUID> binaryContentIds) {
    List<BinaryContentDto> binaryContents = binaryContentService.findAllByIdIn(binaryContentIds);
    List<BinaryContentResponseDto> responseDtoList = binaryContents.stream().map(binaryContentMapper::toResponseDto).toList();
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(responseDtoList);
  }
}
