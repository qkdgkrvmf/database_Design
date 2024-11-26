package com.database_Design.Database_Design.security.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 응답 상태 설정
public class ResourceNotFoundException extends RuntimeException { // DB에 없는 데이터를 찾으려고 할 때 발생하는 예외 처리
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
