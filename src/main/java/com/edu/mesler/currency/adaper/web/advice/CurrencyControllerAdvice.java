package com.edu.mesler.currency.adaper.web.advice;

import com.edu.mesler.currency.adaper.web.dto.response.exception.BasicErrorResponse;
import com.edu.mesler.currency.adaper.web.exception.AlreadyExistException;
import com.edu.mesler.currency.adaper.web.exception.ClientException;
import com.edu.mesler.currency.adaper.web.exception.InternalException;
import com.edu.mesler.currency.adaper.web.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CurrencyControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    protected ResponseEntity<BasicErrorResponse> handleInternalException(InternalException ex) {
        return new ResponseEntity<>(new BasicErrorResponse(ex.getCode(), ex.getMessage()), HttpStatusCode.valueOf(500));
    }

    @ExceptionHandler
    protected ResponseEntity<BasicErrorResponse> handleAlreadyExistException(AlreadyExistException ex) {
        return new ResponseEntity<>(new BasicErrorResponse(ex.getCode(), ex.getMessage()), HttpStatusCode.valueOf(409));
    }

    @ExceptionHandler
    protected ResponseEntity<BasicErrorResponse> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(new BasicErrorResponse(ex.getCode(), ex.getMessage()), HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler
    protected ResponseEntity<BasicErrorResponse> handleClientException(ClientException ex) {
        return new ResponseEntity<>(new BasicErrorResponse(ex.getCode(), ex.getMessage()), HttpStatusCode.valueOf(400));
    }
}
