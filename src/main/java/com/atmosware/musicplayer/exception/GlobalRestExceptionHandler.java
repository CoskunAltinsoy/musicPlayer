package com.atmosware.musicplayer.exception;

import com.atmosware.musicplayer.util.constant.ExceptionTypes;
import com.atmosware.musicplayer.util.result.ExceptionResult;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalRestExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionResult<Object> handleBusinessException(BusinessException exception) {
        return new ExceptionResult<Object>(
                exception.getMessage(),
                ExceptionTypes.Exception.BUSINESS,
                HttpStatus.UNPROCESSABLE_ENTITY.value()
        );
    }
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    private ExceptionResult<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> validationErrors = new HashMap<String, String>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ExceptionResult<Object>(
                validationErrors,
                ExceptionTypes.Exception.VALIDATION,
                HttpStatus.BAD_REQUEST.value()
        );
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    public ExceptionResult<Object> handleValidationException(ValidationException exception) {
        return new ExceptionResult<>(
                exception.getMessage(),
                ExceptionTypes.Exception.VALIDATION,
                HttpStatus.UNPROCESSABLE_ENTITY.value()
        );
    }
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.CONFLICT)
    private ExceptionResult<Object> handleDataIntegrityViolationExceptionException(DataIntegrityViolationException exception) {
        return new ExceptionResult<Object>(
                exception.getMessage(),
                ExceptionTypes.Exception.DATA_INTEGRITY_VIOLATION,
                HttpStatus.CONFLICT.value()
        );
    }
}