package br.com.compassouol.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class AtualizarClienteException extends RuntimeException {

    public AtualizarClienteException(String message) {
        super(message);
    }

}
