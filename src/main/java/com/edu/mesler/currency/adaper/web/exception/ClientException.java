package com.edu.mesler.currency.adaper.web.exception;

import lombok.Getter;

@Getter
public class ClientException extends RuntimeException{
    String code = "Bad Request";
    public ClientException(String entity) {
        super(entity + " have bad credentials");
    }
}
