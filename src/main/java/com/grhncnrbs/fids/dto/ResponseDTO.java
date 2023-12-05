package com.grhncnrbs.fids.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseDTO {
    public static ResponseEntity<ExceptionDTO> errorResponse(String message) {
        return new ResponseEntity<>(new ExceptionDTO(HttpStatus.OK.value(), message), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    public static ResponseEntity<ExceptionDTO> successResponse(String message, Object data) {
        return new ResponseEntity<>(new ExceptionDTO(HttpStatus.OK.value(), message, data), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    public static ResponseEntity<ExceptionDTO> successResponse(String message) {
        return new ResponseEntity<>(new ExceptionDTO(HttpStatus.OK.value(), message), HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }
}
