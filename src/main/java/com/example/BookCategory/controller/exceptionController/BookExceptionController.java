package com.example.BookCategory.controller.exceptionController;

import com.example.BookCategory.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookExceptionController {
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleNotfoundException(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
