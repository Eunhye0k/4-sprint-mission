package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateDTO;
import com.sprint.mission.discodeit.entity.BinaryContent;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface BinaryContentRepository {
    UUID create(BinaryContentCreateDTO binaryContentCreateDTO);
    UUID save(BinaryContent binaryContent);
    BinaryContent find(UUID id);
    List<BinaryContent> findAllByIdIn(List<UUID> ids);
    List<BinaryContent> findAll();
    UUID delete(UUID id);
}
