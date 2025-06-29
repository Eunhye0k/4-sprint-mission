package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateDTO;
import com.sprint.mission.discodeit.entity.BinaryContent;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface BinaryContentService {
    UUID create(BinaryContentCreateDTO binaryContentCreateDTO);
    BinaryContent find(UUID id);
    List<BinaryContent> findAll();
    List<BinaryContent> findAllByIdIn(List<UUID> ids);
    UUID delete(UUID id);

}
