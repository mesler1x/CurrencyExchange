package com.edu.mesler.currency.adaper.web.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private String code ="NOT_FOUND";

    public NotFoundException(String entity) {
        super(entity + " not found");
    }
}
