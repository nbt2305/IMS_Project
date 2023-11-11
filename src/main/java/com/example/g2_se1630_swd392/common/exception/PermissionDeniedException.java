package com.example.g2_se1630_swd392.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class PermissionDeniedException extends RuntimeException{
    public PermissionDeniedException() {
        super("Permission denied!");
    }
}
