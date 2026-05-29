package com.javaeo.usuario.controller;

import com.javaeo.usuario.infrastructure.exeptions.ConflictException;
import com.javaeo.usuario.infrastructure.exeptions.RescoucerNotFoundException;
import com.javaeo.usuario.infrastructure.exeptions.UnauthorizedException;
import com.javaeo.usuario.infrastructure.exeptions.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RescoucerNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(RescoucerNotFoundException ex,
                                                                            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND.value(),
        ex.getMessage(),
        request.getRequestURI(),
                "Not Found"
        ));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflictException(ConflictException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildError(HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                request.getRequestURI(),
                "Not Found"
        ));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorizedException(UnauthorizedException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildError(HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                request.getRequestURI(),
                "Not Found"
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError(HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI(),
                "Not Found"
        ));

    }

    private ErrorResponseDTO buildError(int status, String mensagem, String path, String error) {
         return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(mensagem)
                .error(error)
                .status(status)
                .path(path)
                .build();
    }
}


