package com.edu.mesler.currency.adaper.web.exception;

import lombok.Getter;

@Getter
public class AlreadyExistException extends RuntimeException{
    String code = "ALREADY_EXIST";
    public AlreadyExistException(String entity) {
        super(entity + " already exist");
    }
}
