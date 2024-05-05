package com.edu.mesler.currency.adaper.web.advice;

import com.edu.mesler.currency.adaper.web.dto.exception.DBExceptionResponse;
import com.edu.mesler.currency.adaper.web.dto.exception.NotFoundExceptionResponse;
import com.edu.mesler.currency.adaper.web.dto.exception.NotUniqueExceptionResponse;
import com.edu.mesler.currency.adaper.web.exception.CurrencyNotFoundException;
import com.edu.mesler.currency.adaper.web.exception.DBException;
import com.edu.mesler.currency.adaper.web.exception.NotUniqueException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CurrencyControllerAdviceAdvice extends ResponseEntityExceptionHandler {
    String DB_EXCEPTION_MESSAGE = "Something went wrong with DB";
    String NOT_UNIQUE_EXCEPTION_MESSAGE = "Not unique value in DB";
    String CURRENCY_NOT_FOUND_MESSAGE = "Currency not found";

    @ExceptionHandler(DBException.class)
    protected ResponseEntity<DBExceptionResponse> handleDBException () {
        ResponseEntity<DBExceptionResponse> dbExceptionResponseResponseEntity =
                new ResponseEntity<>(new DBExceptionResponse(DB_EXCEPTION_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        return dbExceptionResponseResponseEntity;
    }

    @ExceptionHandler(NotUniqueException.class)
    protected ResponseEntity<NotUniqueExceptionResponse> handleNotUniqueException() {
        return new ResponseEntity<>(new NotUniqueExceptionResponse(NOT_UNIQUE_EXCEPTION_MESSAGE),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CurrencyNotFoundException.class)
    protected ResponseEntity<NotFoundExceptionResponse> handleNotFoundException() {
        return new ResponseEntity<>(new NotFoundExceptionResponse(CURRENCY_NOT_FOUND_MESSAGE), HttpStatus.NOT_FOUND);
    }
}
