package br.com.compassouol.challenge.jaxrs.controller;

import br.com.compassouol.challenge.dto.CidadeDTO;
import br.com.compassouol.challenge.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.List;

/**
 * @author marcussantos
 */
@SpringBootTest
@ExtendWith(SpringExtension.class) //novo RunWith
class CidadeControllerTest {

    @SpyBean
    private CidadeController cidadeController;

    @Test
    void test_404_not_found_cidade_by_nome_response() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("nome","Ceilândia");
            cidadeController.getCidadeByFilter(params);
        });
    }

    @Test
    void test_http_200_when_found_cidade() {
        HashMap<String, String> params = new HashMap<>();
        params.put("nome","Rio de Janeiro");
        ResponseEntity<List<CidadeDTO>> cidadeByFilter = cidadeController.getCidadeByFilter(params);
        Assertions.assertEquals(HttpStatus.OK,cidadeByFilter.getStatusCode());
    }

    @Test
    void test_http_correct_estado_when_found_cidade() {
        HashMap<String, String> params = new HashMap<>();
        params.put("nome","Taguatinga");
        ResponseEntity<List<CidadeDTO>> cidadeByFilter = cidadeController.getCidadeByFilter(params);
        Assertions.assertEquals("Distrito Federal",cidadeByFilter.getBody().get(0).getEstado());
    }

    @Test
    void test_404_not_found_cidade_by_estado() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("estado","Minas Gerais");
            cidadeController.getCidadeByFilter(params);
        });
    }

    @Test
    void test_http_200_when_found_cidade_by_estado() {
        HashMap<String, String> params = new HashMap<>();
        params.put("estado","Rio de Janeiro");
        ResponseEntity<List<CidadeDTO>> cidadeByFilter = cidadeController.getCidadeByFilter(params);
        Assertions.assertEquals(HttpStatus.OK,cidadeByFilter.getStatusCode());
    }

    @Test
    void test_http_correct_cidade_when_found_estado() {
        HashMap<String, String> params = new HashMap<>();
        params.put("estado","Distrito Federal");
        ResponseEntity<List<CidadeDTO>> cidadeByFilter = cidadeController.getCidadeByFilter(params);
        Assertions.assertEquals("Taguatinga",cidadeByFilter.getBody().get(0).getNome());
    }

    @Test
    void test_insert_cidade_succes() {
        CidadeDTO newCidade = new CidadeDTO("Brazlândia", "Distrito Federal");
        ResponseEntity<CidadeDTO> cidadeAdicionada =  cidadeController.addCidade(newCidade);
        Assertions.assertNotNull(cidadeAdicionada);
    }

    @Test
    void test_insert_cidade_correct_estado() {
        CidadeDTO newCidade = new CidadeDTO("Aracaju", "Sergipe");
        ResponseEntity<CidadeDTO> cidadeAdicionada =  cidadeController.addCidade(newCidade);
        Assertions.assertEquals("Sergipe",newCidade.getEstado());
    }
}