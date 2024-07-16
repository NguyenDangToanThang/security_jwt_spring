package com.socialnetwork.socialbackend.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class JwtAuthenticationResponse {
    String token;
    String refreshToken;
}
