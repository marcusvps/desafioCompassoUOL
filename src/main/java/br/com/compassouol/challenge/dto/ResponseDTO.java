package br.com.compassouol.challenge.dto;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 *
 */
public class ResponseDTO implements Serializable {

    HttpStatus httpStatus;
    String mensagem;

    public ResponseDTO(HttpStatus httpStatus, String mensagem) {
        this.httpStatus = httpStatus;
        this.mensagem = mensagem;
    }

    public ResponseDTO() {
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
