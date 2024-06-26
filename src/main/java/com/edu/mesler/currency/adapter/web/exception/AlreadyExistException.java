package com.edu.mesler.currency.adapter.web.exception;

import lombok.Getter;

@Getter
public class AlreadyExistException extends RuntimeException{
    String code = "ALREADY_EXIST";
    public AlreadyExistException(String entity) {
        super(entity + " already exist");
    }
}
