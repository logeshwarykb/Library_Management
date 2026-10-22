
package com.springboot.library_management.config;

import com.springboot.library_management.dto.response.ErrorRespDto;
import com.springboot.library_management.exception.BookNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorRespDto> handleBookNotFoundException(
            BookNotFoundException e) {

        return ResponseEntity
                .badRequest()
                .body(
                        new ErrorRespDto(
                                e.getMessage(),
                                "Book not found in DB",
                                Instant.now())
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {

        Map<String, String> map = new HashMap<>();

        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> list = bindingResult.getFieldErrors();

        list.forEach(fieldError ->
                map.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity
                .badRequest()
                .body(map);
    }
}
