package Goweb.FormMaker.exception.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getMessage();

    HttpStatus getHttpStatus();
}