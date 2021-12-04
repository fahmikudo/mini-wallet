package io.fahmikudo.wallet.exception;

import org.springframework.http.HttpStatus;

public class HttpException extends Exception {

    private HttpStatus httpStatus;

    public HttpException() {
    }

    public HttpException(HttpStatus httpStatus) {
        super(httpStatus.name());
        this.httpStatus = httpStatus;
    }

    public HttpException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
