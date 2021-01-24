package br.com.compassouol.challenge.dto;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 *
 */
public class ResponseDTO implements Serializable {

    final HttpStatus httpStatus;
    final String mensagem;

    public ResponseDTO(HttpStatus httpStatus, String mensagem) {
        this.httpStatus = httpStatus;
        this.mensagem = mensagem;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMensagem() {
        return mensagem;
    }

}
