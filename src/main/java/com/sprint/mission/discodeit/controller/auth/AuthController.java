package com.sprint.mission.discodeit.controller.auth;

import com.sprint.mission.discodeit.dto.request.LoginRequest;
import com.sprint.mission.discodeit.service.basic.BasicAuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auths")
public class AuthController {
    private final BasicAuthService basicAuthService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        basicAuthService.login(loginRequest);

        return ResponseEntity.ok(loginRequest);
    }

    @GetMapping
    public String get(){
        return "Hello world";
    }
}
