package br.com.compassouol.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class DeleteException extends RuntimeException {

    public DeleteException(String message) {
        super(message);
    }

}
