package io.fahmikudo.wallet.exception;

import org.springframework.http.HttpStatus;

public class UserNotFound extends Exception {

    private final HttpStatus httpStatus;

    public UserNotFound(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
