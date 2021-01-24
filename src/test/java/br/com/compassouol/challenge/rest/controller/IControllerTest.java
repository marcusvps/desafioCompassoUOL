package br.com.compassouol.challenge.rest.controller;

import br.com.compassouol.challenge.dto.ResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

class IControllerTest {

    @Test
    void test_ping_controller() {
        ResponseEntity<ResponseDTO> response = IController.pingController();
        Assertions.assertEquals("Serviço está online!", Objects.requireNonNull(response.getBody()).getMensagem());
    }
}