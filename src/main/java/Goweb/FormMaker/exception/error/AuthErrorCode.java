package Goweb.FormMaker.exception.error;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    EXTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "email을 받아올 수 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 Token 입니다."),
    INVALID_LOGIN_METHOD(HttpStatus.CONFLICT, "올바르지 않은 로그인 방식입니다."),
    STUDENT_NUMBER_DUPLICATED(HttpStatus.CONFLICT, "중복된 ID 입니다.");

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