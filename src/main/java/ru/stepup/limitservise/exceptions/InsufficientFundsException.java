package ru.stepup.limitservise.exceptions;

import org.springframework.http.HttpStatus;

public class InsufficientFundsException extends RuntimeException {

    private final HttpStatus httpStatus;

    public InsufficientFundsException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
