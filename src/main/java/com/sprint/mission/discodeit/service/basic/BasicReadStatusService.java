package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusCreateDTO;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusUpdateDTO;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import com.sprint.mission.discodeit.validate.ReadStatusValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final ReadStatusValidator readStatusValidator;

    @Override
    public UUID create(ReadStatusCreateDTO dto) {
        readStatusValidator.validateReadStatus(dto.userId(), dto.channelId());
        ReadStatus readStatus = new ReadStatus(dto.userId(), dto.channelId());
        return readStatus.getId();
    }

    @Override
    public ReadStatus find(UUID id) {
        ReadStatus findReadStatus = readStatusRepository.findId(id);
        Optional.ofNullable(findReadStatus)
                .orElseThrow(() -> new NoSuchElementException("해당하는 findReadStatus가 없습니다." + id));
        return findReadStatus;
    }

    @Override
    public List<ReadStatus> findAll() {
        return readStatusRepository.findAll();
    }

    @Override
    public List<ReadStatus> findAllByUserId(UUID userId) {
        return readStatusRepository.findAllByUserId(userId);
    }

    @Override
    public ReadStatus update(ReadStatusUpdateDTO readStatusUpdateDTO) {
        ReadStatus findReadStatus = readStatusRepository.findId(readStatusUpdateDTO.id());
        findReadStatus.updateReadStatus(readStatusUpdateDTO.time());
        readStatusRepository.update(findReadStatus);
        return findReadStatus;
    }

    @Override
    public UUID delete(UUID id) {
        return readStatusRepository.delete(id);
    }
}
