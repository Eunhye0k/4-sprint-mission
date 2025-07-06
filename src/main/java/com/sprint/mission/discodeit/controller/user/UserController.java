package com.sprint.mission.discodeit.controller.user;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.*;
import com.sprint.mission.discodeit.service.basic.BasicAuthService;
import com.sprint.mission.discodeit.service.basic.BasicBinaryContentService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.service.basic.BasicUserStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final BasicUserService basicUserService;
    private final BasicUserStatusService basicUserStatusService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@ModelAttribute UserCreateRequest userCreateRequest ,
                                     @RequestParam(value = "image", required = false)MultipartFile file) throws IOException{

        basicUserService.create(userCreateRequest, BinaryContentCreateRequest.from(file));
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/{user-id}", method = RequestMethod.PATCH)
    public ResponseEntity patchUser(@PathVariable("user-id") UUID memberId,
                                    @ModelAttribute UserUpdateRequest userUpdateRequest,
                                    @RequestParam(value = "image", required = false)MultipartFile file) throws IOException{
        basicUserService.update(memberId, userUpdateRequest, BinaryContentCreateRequest.from(file));

        return ResponseEntity.ok(userUpdateRequest);
    }


    @RequestMapping(value = "/{user-id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("user-id") UUID memberId){
        basicUserService.delete(memberId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getUsers(){
        List<UserDto> findUsers = basicUserService.findAll();
        return ResponseEntity.ok(findUsers);
    }

    @RequestMapping(value = "/{user-id}/connect-status", method = RequestMethod.PATCH)
    public ResponseEntity isOnline(@PathVariable("user-id")UUID userId){

        basicUserStatusService.markOnline(userId);

        return ResponseEntity.ok().build();
    }
}
