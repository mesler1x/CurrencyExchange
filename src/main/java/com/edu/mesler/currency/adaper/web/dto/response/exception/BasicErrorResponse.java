package com.edu.mesler.currency.adaper.web.dto.response.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BasicErrorResponse(String code, String field, String message) {
    public BasicErrorResponse(String code, String message) {
        this(code, null, message);
    }
}
