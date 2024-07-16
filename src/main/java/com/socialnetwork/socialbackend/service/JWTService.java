package com.socialnetwork.socialbackend.service;

import com.socialnetwork.socialbackend.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface JWTService {
    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateRefreshToken(HashMap<String, Object> extraClaim, UserDetails userDetails);
}
