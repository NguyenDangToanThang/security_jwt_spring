package com.socialnetwork.socialbackend.service;

import com.socialnetwork.socialbackend.dto.request.RefreshTokenRequest;
import com.socialnetwork.socialbackend.dto.request.SignInRequest;
import com.socialnetwork.socialbackend.dto.request.SignUpRequest;
import com.socialnetwork.socialbackend.dto.response.JwtAuthenticationResponse;
import com.socialnetwork.socialbackend.entity.User;

public interface AuthenticationService {
    User signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SignInRequest request);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest request);
}
