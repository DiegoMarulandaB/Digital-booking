package com.digital.DigitaBooking.controllers;

import com.digital.DigitaBooking.models.dtos.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;

@CrossOrigin(origins = "*")
@ControllerAdvice
@Slf4j
public class ErrorHandlerController {

    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    ProblemDetail handleUnauthorized() {
        return ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ex.getBody();
        if (!CollectionUtils.isEmpty(ex.getFieldErrors())) {
            problemDetail.setProperty("fieldErrors",
                    ex.getFieldErrors().stream().map(fieldError ->
                            new ValidationError(fieldError.getField(), fieldError.getDefaultMessage())));
        }
        return problemDetail;
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ProblemDetail handleErrorResponseException(ErrorResponseException ex) {
        return ex.getBody();
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
