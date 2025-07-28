package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.request.LoginRequest;
import com.sprint.mission.discodeit.dto.response.LoginResponseDto;

public interface AuthService {

  LoginResponseDto login(LoginRequest loginRequest);
}
