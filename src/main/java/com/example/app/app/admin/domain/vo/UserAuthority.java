package com.example.app.app.admin.domain.vo;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum UserAuthority implements GrantedAuthority {
    ROLE_USER,
    ROLE_STAFF,
    ROLE_ADMIN;

    public String getAuthority() {
        return this.name();
    }
}
