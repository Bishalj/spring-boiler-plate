package com.application.jwt.v1.mappers.request;

import com.application.jwt.v1.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class User implements UserDetails {

        private String username;
        private String password;

        private Boolean enabled;

        private List<Role> roles;

        public User(String username) {
            this.username = username;
        }

        @Override
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }
        @Override
        public boolean isAccountNonLocked() {
            return false;
        }
        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }
        @Override
        public boolean isEnabled() {
            return this.enabled;
        }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList());
        }

        @JsonIgnore
        @Override
        public String getPassword() {
            return password;
        }
        @JsonProperty
        public void setPassword(String password) {
            this.password = password;
        }
}
