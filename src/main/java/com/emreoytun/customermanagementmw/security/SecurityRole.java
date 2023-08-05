package com.emreoytun.customermanagementmw.security;

import com.emreoytun.customermanagementdata.dto.role.RoleDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class SecurityRole implements GrantedAuthority {

    private final RoleDto role;

    @Override
    public String getAuthority() {
        return role.getName();
    }
}
