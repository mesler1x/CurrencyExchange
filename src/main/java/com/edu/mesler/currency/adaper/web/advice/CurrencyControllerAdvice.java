package com.edu.mesler.currency.adaper.web.advice;

import com.edu.mesler.currency.adaper.web.dto.exception.BasicErrorResponse;
import com.edu.mesler.currency.adaper.web.exception.AlreadyExistException;
import com.edu.mesler.currency.adaper.web.exception.ClientException;
import com.edu.mesler.currency.adaper.web.exception.InternalException;
import com.edu.mesler.currency.adaper.web.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CurrencyControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    protected BasicErrorResponse handleInternalException(InternalException ex) {
        return new BasicErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler
    protected BasicErrorResponse handleAlreadyExistException(AlreadyExistException ex) {
        return new BasicErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler
    protected BasicErrorResponse handleNotFoundException(NotFoundException ex) {
        return new BasicErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler
    protected BasicErrorResponse handleClientException(ClientException ex) {
        return new BasicErrorResponse(ex.getCode(), ex.getMessage());
    }
}
