package br.com.compassouol.challenge.exception.mapper;

import br.com.compassouol.challenge.exception.DeleteException;
import br.com.compassouol.challenge.exception.InsertException;
import br.com.compassouol.challenge.exception.NotFoundException;
import br.com.compassouol.challenge.exception.UpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Responsavel por interceptar as exceptions negocias e retornar uma forma
 * mais amigavel de leitura para a requisição.
 * @author marcussantos
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(NotFoundException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        response.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InsertException.class,UpdateException.class, DeleteException.class})
    public ResponseEntity<ErrorResponse> dmlExceptionHandler(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
