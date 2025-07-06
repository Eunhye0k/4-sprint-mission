package com.sprint.mission.discodeit.dto.request;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public record BinaryContentCreateRequest(
        String fileName,
        String contentType,
        byte[] bytes
) {
    public static Optional<BinaryContentCreateRequest> from(MultipartFile file) throws IOException {
        if(file == null || file.isEmpty()) return Optional.empty();

        try{
            return Optional.of(new BinaryContentCreateRequest(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            ));
        }catch (IOException e){
            throw new IOException("Error reading file");
        }
    }
}
