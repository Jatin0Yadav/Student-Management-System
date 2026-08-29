package org.example.student_service.exception;



import org.example.student_service.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice       // makes the ExceptionHandler global across controllers
public class GlobalExceptionHandler {


    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFoundException(
            StudentNotFoundException ex
    ) {

        ErrorResponse e = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())             // this Http is just JSON Value
                .message(ex.getMessage())                         // ex.msg is what we give in service
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)                   // this Http is for Postman/Browser.
                .body(e);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDepartmentNotFoundException (
            DepartmentNotFoundException ex
    ) {
        ErrorResponse e = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e);
    }

}
