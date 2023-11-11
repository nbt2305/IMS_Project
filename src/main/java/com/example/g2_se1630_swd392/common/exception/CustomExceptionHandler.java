package com.example.g2_se1630_swd392.common.exception;

import com.example.g2_se1630_swd392.dto.HttpResponse;
import com.example.g2_se1630_swd392.common.G2_SWD392_Error;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Trung Nguyễn Bá
 * @created 9/27/2023
 * @project IMS_G2_SWD392
 */


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ex.printStackTrace();
        logger.error(ex.getMessage());
        return buildResponseEntity(HttpResponse.error(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.OK);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
        ex.printStackTrace();
        logger.error(ex.getMessage());
        String message = ex.getLocalizedMessage();
        return buildResponseEntity(HttpResponse.error(message, HttpStatus.BAD_REQUEST.value()), HttpStatus.OK);
    }

    @ExceptionHandler({ UnauthorizedException.class, AuthenticationException.class })
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public final ResponseEntity<Object> handleAuthenticationException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        logger.error(ex.getMessage());
        String message = ex.getLocalizedMessage();
        if (!message.equals(G2_SWD392_Error.USER_NOT_FOUND_ERROR.getMessage())) {
            message = G2_SWD392_Error.USER_USERNAME_PASSWORD_ERROR.getMessage();
        }
        HttpResponse<Object> baseResponse = HttpResponse.error(message, HttpStatus.UNAUTHORIZED.value());
        baseResponse.setData("USER_USERNAME_PASSWORD_ERROR"); // Đặt giá trị dữ liệu mong muốn
        return buildResponseEntity(baseResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<Object> handleConflictException(ConflictException ex, WebRequest request) {
        ex.printStackTrace();
        logger.error(ex.getMessage());
        String message = ex.getLocalizedMessage();
        return buildResponseEntity(HttpResponse.error(message, HttpStatus.CONFLICT.value()), HttpStatus.OK);
    }


    private ResponseEntity<Object> buildResponseEntity(HttpResponse<Object> baseResponse, HttpStatus status) {
        return new ResponseEntity<>(baseResponse, status);
    }
}
