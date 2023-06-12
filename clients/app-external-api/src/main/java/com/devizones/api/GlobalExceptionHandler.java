package com.devizones.api;

import com.devizones.api.dto.BaseResponse;
import com.devizones.domain.member.exception.MemberException;
import com.devizones.domain.post.exception.PostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<BaseResponse<?>> memberExceptionHandler(MemberException e) {
        log.error("MemberExceptionHandler: ", e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(BaseResponse.error(e.getMessage()));
    }

    @ExceptionHandler(PostException.class)
    public ResponseEntity<BaseResponse<?>> postExceptionHandler(PostException e) {
        log.error("PostExceptionHandler: ", e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(BaseResponse.error(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<MultiValueMap<String, String>>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidExceptionHandler: ", e);
        MultiValueMap<String, String> errors = new LinkedMultiValueMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            errors.add(fieldError.getField(), fieldError.getDefaultMessage());
        }

        BaseResponse<MultiValueMap<String, String>> messageBody = BaseResponse.error("필드 유효성 검사에 실패하였습니다.", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(messageBody);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse<?>> accessDeniedExceptionHandler(AccessDeniedException e) {
        log.error("accessDeniedExceptionHandler: ", e);

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                             .body(BaseResponse.error(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> exceptionHandler(Exception e) {
        log.error("ExceptionHandler: ", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(BaseResponse.error(e.getMessage()));
    }
}
