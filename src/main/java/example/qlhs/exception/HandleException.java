package example.qlhs.exception;

import example.qlhs.dto.response.ResponseError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class HandleException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseError> handleRuntimeExceptions(RuntimeException ex) {
        ResponseError re = ResponseError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(ex.getMessage())
                .build();
        return new ResponseEntity<>(re, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ResponseError> handleValidationExceptions(CustomValidationException ex) {
        ResponseError error = new ResponseError();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(ex.getErrorsData().getErrorCode());
        error.setError(ex.getErrorsData().getMessage());
        error.setErrors(ex.getErrors());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseError> handleNoSuchElementExceptions(NoSuchElementException ex) {
        ResponseError error = new ResponseError();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.NO_CONTENT.value());
        error.setError(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
    }
}
