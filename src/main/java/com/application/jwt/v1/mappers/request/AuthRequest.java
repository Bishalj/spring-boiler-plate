package com.application.jwt.v1.mappers.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthRequest {
    private String username;
    private String password;
    private String role;
    private String encryptedPassword;
}
