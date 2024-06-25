package com.kidzcubicle.uaps.web.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestAlertException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(BadRequestAlertException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", ex.getMessage());
        response.put("entityName", ex.getEntityName());
        response.put("errorKey", ex.getErrorKey());
        return ResponseEntity.badRequest().body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<Map<String, String>> handleInvalidIdException(InvalidIdException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", ex.getMessage());
        response.put("entityName", ex.getEntityName());
        response.put("errorKey", ex.getErrorKey());
        return ResponseEntity.badRequest().body(response);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyUsedExceptionException(EmailAlreadyUsedException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", ex.getMessage());
        response.put("entityName", ex.getEntityName());
        response.put("errorKey", ex.getErrorKey());
        return ResponseEntity.badRequest().body(response);
    }
}
