package br.com.compassouol.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class NovoClienteException extends RuntimeException {

    public NovoClienteException(String message) {
        super(message);
    }

}
