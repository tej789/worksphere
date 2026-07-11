package com.tej.Worksphere.service;

import com.tej.Worksphere.dto.auth.AuthResponse;
import com.tej.Worksphere.dto.auth.LoginRequest;

public interface AuthenticationService {

    AuthResponse login(LoginRequest request);

}