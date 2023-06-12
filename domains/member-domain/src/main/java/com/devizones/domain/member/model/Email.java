package com.devizones.domain.member.model;

import com.devizones.domain.member.exception.MemberErrorCode;
import com.devizones.domain.member.exception.MemberException;
import lombok.Builder;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.devizones.commons.StringUtils.hasText;

@Getter
public class Email {
    private String value;

    @Builder
    private Email(String email) {
        setEmail(email);
    }

    private void setEmail(String email) {
        if (!hasText(email)) {
            throw new MemberException(MemberErrorCode.EMAIL_EMPTY);
        }

        if (!isValidEmail(email)) {
            throw new MemberException(MemberErrorCode.EMAIL_FORMAT_ERROR);
        }

        this.value = email;
    }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Email create(String email) {
        return new Email(email);
    }
}
