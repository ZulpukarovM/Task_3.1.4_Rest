package ru.kata.spring.boot_security.demo.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kata.spring.boot_security.demo.util.ErrorMessage;
import ru.kata.spring.boot_security.demo.util.ValidationErrorResponse;
import ru.kata.spring.boot_security.demo.util.Violation;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> onEntityNotFoundException(EntityNotFoundException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintViolationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream().map(violation -> new Violation(violation.getPropertyPath().toString(), violation.getMessage())).collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream().map(error -> new Violation(error.getField(), error.getDefaultMessage())).collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }
}
