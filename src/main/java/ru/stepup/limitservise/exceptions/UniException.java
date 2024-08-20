package ru.stepup.limitservise.exceptions;

import org.springframework.http.HttpStatus;

public class UniException extends RuntimeException {

    private final HttpStatus httpStatus;

    public UniException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
