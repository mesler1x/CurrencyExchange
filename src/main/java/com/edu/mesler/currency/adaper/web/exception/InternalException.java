package com.edu.mesler.currency.adaper.web.exception;

import lombok.Getter;

@Getter
public class InternalException extends RuntimeException {
    String code = "INTERNAL_SERVER_ERROR";
    public InternalException(String entity) {
        super(entity + " error happened");
    }
}
