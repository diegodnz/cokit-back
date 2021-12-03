package com.pc.configs.exceptions;


import org.springframework.http.HttpStatus;

public class MensagemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus status;

    public MensagemException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

}