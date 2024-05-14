package com.edu.mesler.currency.adapter.web.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private String code ="NOT_FOUND";

    public NotFoundException(String entity) {
        super(entity + " not found");
    }
}
