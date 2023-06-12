package com.devizones.web.core.token.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
public class MemberAccount {
    private Long id;
    private String email;
    private List<String> authorities;

    private MemberAccount(Long id, String email, List<String> authorities) {
        this.id = id;
        this.email = email;
        this.authorities = authorities;
    }

    public static MemberAccount of(Long id, String email) {
        return new MemberAccount(id, email, Collections.emptyList());
    }

    public static MemberAccount of(Long id, String email, List<String> authorities) {
        return new MemberAccount(id, email, authorities);
    }
}
