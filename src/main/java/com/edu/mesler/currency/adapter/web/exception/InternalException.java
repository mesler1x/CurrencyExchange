package com.edu.mesler.currency.adapter.web.exception;

import lombok.Getter;

@Getter
public class InternalException extends RuntimeException {
    String code = "INTERNAL_SERVER_ERROR";
    public InternalException(String message) {
        super(message);
    }
}
