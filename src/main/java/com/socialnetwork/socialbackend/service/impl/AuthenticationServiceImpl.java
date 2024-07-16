package com.socialnetwork.socialbackend.service.impl;

import com.socialnetwork.socialbackend.dto.request.RefreshTokenRequest;
import com.socialnetwork.socialbackend.dto.request.SignInRequest;
import com.socialnetwork.socialbackend.dto.request.SignUpRequest;
import com.socialnetwork.socialbackend.dto.response.JwtAuthenticationResponse;
import com.socialnetwork.socialbackend.entity.Role;
import com.socialnetwork.socialbackend.entity.User;
import com.socialnetwork.socialbackend.repository.UserRepository;
import com.socialnetwork.socialbackend.service.AuthenticationService;
import com.socialnetwork.socialbackend.service.JWTService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    JWTService jwtService;
    ModelMapper modelMapper;

    @Override
    public User signup(SignUpRequest request) {
        User user = modelMapper.map(request, User.class);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("Invalid email or password")
        );

        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String userEmail = jwtService.extractUsername(request.getToken());
        User user = userRepository.findByUsername(userEmail).orElseThrow();
        if(jwtService.isTokenValid(request.getToken(), user)) {

            String jwt = jwtService.generateToken(user);

            return JwtAuthenticationResponse.builder()
                    .token(jwt)
                    .refreshToken(request.getToken())
                    .build();
        }
        return null;
    }
}
