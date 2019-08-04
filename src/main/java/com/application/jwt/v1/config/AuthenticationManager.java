package com.application.jwt.v1.config;

import com.application.jwt.v1.enums.Role;
import com.application.jwt.v1.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        return getValidationTokenResponse(authToken);
    }

    private Mono<Authentication> getValidationTokenResponse(String authToken) {
        String username = getUserNameFromToken(authToken);
        if (username != null && jwtUtil.validateToken(authToken)) {
            Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
            List<String> rolesMap = claims.get("role", List.class);
            List<Role> roles = new ArrayList<>();
            roles = rolesMap.parallelStream().map(roleMap -> Role.valueOf(roleMap)).collect(Collectors.toList());
//            for (String roleMap : rolesMap) {
//                roles.add(Role.valueOf(roleMap));
//            }
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList())
            );
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }

    private String getUserNameFromToken(String authToken) {
        try {
             return jwtUtil.getUsernameFromToken(authToken);
        } catch (Exception e) {
            return null;
        }
    }
}
