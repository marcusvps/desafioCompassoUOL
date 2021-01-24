package br.com.compassouol.challenge.rest.controller;

import br.com.compassouol.challenge.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface IController{

    /**
     * Ping para verificar se a aplicação já está online e disponivel.
     * @return ResponseEntity quando a aplicação estiver online.
     */
    @GetMapping
    static ResponseEntity<ResponseDTO> pingController(){
        String name = "Serviço está";
        ResponseDTO response = new ResponseDTO(HttpStatus.OK,name + " online!" );
        return new ResponseEntity<>(response, response.getHttpStatus());
    };
}
