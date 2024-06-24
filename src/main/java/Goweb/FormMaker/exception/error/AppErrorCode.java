package Goweb.FormMaker.exception.error;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AppErrorCode implements ErrorCode {

    INVALID_USER(HttpStatus.UNAUTHORIZED, "존재하지 않는 회원입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}