package br.com.compassouol.challenge.exception.mapper;

import br.com.compassouol.challenge.exception.InsertException;
import br.com.compassouol.challenge.exception.NotFoundException;
import br.com.compassouol.challenge.exception.UpdateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;


class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = Mockito.spy(new GlobalExceptionHandler());

    @Test
    void test_handler_notFoundException() {
        ResponseEntity<ErrorResponse> response =
                handler.notFoundExceptionHandler(new NotFoundException("Informação nao encontrada!"));

        Assertions.assertEquals("Informação nao encontrada!", Objects.requireNonNull(response.getBody()).getErrorMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void test_handler_insertException() {
        ResponseEntity<ErrorResponse> response =
                handler.dmlExceptionHandler(new InsertException("Erro ao incluir Informacao!"));

        Assertions.assertEquals("Erro ao incluir Informacao!", Objects.requireNonNull(response.getBody()).getErrorMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void test_handler_updateException() {
        ResponseEntity<ErrorResponse> response =
                handler.dmlExceptionHandler(new UpdateException("Erro ao alterar Informacao!"));

        Assertions.assertEquals("Erro ao alterar Informacao!", Objects.requireNonNull(response.getBody()).getErrorMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void test_handler_deleteException() {
        ResponseEntity<ErrorResponse> response =
                handler.dmlExceptionHandler(new UpdateException("Erro ao deletar informacao!"));

        Assertions.assertEquals("Erro ao deletar informacao!", Objects.requireNonNull(response.getBody()).getErrorMessage());
        Assertions.assertEquals("400",response.getBody().getErrorCode());

    }
}