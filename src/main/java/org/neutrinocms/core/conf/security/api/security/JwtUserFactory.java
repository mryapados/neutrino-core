package org.neutrinocms.core.conf.security.api.security;

import java.util.List;
import java.util.stream.Collectors;

import org.neutrinocms.core.model.Authority;
import org.neutrinocms.core.model.independant.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
            user.getId(),
            user.getLogin(),
            user.getEncryptedPassword(),
            mapToGrantedAuthorities(user.getAuthorities()),
            user.getEnabled(),
            user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
            .collect(Collectors.toList());
    }
}
