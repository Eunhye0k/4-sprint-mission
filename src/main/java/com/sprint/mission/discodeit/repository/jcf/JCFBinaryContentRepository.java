package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentCreateDTO;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JCFBinaryContentRepository implements BinaryContentRepository {
    private final Map<UUID, BinaryContent> data;

    public JCFBinaryContentRepository() {
       data = new HashMap<>();
    }



    @Override
    public UUID create(BinaryContentCreateDTO binaryContentCreateDTO) {
        return null;
    }

    @Override
    public UUID save(BinaryContent binaryContent) {
        data.put(binaryContent.id(), binaryContent);
        return binaryContent.id();
    }

    @Override
    public BinaryContent find(UUID id) {
        return data.get(id);
    }


    @Override
    public List<BinaryContent> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
        return ids.stream()
                .map(data::get)
                .filter(Objects::nonNull)
                .toList();
    }
    @Override
    public UUID delete(UUID id) {
        data.remove(id);
        return id;
    }
}
