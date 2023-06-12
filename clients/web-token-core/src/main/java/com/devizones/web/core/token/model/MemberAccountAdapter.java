package com.devizones.web.core.token.model;

import lombok.Getter;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.stream.Collectors;

@Getter
public class MemberAccountAdapter extends User {

    private MemberAccount memberAccount;

    public MemberAccountAdapter(MemberAccount memberAccount) {
        super(memberAccount.getEmail(),  "", memberAccount.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        this.memberAccount = memberAccount;
    }
}
