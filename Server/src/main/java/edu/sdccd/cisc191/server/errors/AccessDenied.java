package edu.sdccd.cisc191.server.errors;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AccessDenied extends RuntimeException {
    public AccessDenied(String errorMessage) {
        super(errorMessage);
    }
}
