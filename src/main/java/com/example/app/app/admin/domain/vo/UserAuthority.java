package com.example.app.app.admin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
public enum UserAuthority implements GrantedAuthority {
    USER,
    STAFF,
    ADMIN;

    public String getAuthority() {
        return this.name();
    }
}
