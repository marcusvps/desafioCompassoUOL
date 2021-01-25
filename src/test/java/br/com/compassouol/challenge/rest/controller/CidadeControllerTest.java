package br.com.compassouol.challenge.rest.controller;

import br.com.compassouol.challenge.dto.CidadeDTO;
import br.com.compassouol.challenge.exception.InsertException;
import br.com.compassouol.challenge.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author marcussantos
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class CidadeControllerTest {

    @SpyBean
    private CidadeController cidadeController;



    @Test
    void test_deve_lancar_erro_ao_nao_encontrar_cidade_pelo_nome() {
        HashMap<String, String> params = new HashMap<>();
        params.put("nome", "Ceilândia");

        try {
            cidadeController.getCidadeByFilter(params);
            Assertions.fail();
        } catch (NotFoundException e) {
            Assertions.assertEquals("Nenhuma cidade encontrada para o nome: Ceilândia", e.getMessage());
        }

    }

    @Test
    void test_sucesso_ao_encontrar_cidade_pelo_nome() {
        HashMap<String, String> params = new HashMap<>();
        params.put("nome", "Rio de Janeiro");
        ResponseEntity<List<CidadeDTO>> cidadeByFilter = cidadeController.getCidadeByFilter(params);
        Assertions.assertEquals(HttpStatus.OK, cidadeByFilter.getStatusCode());
    }

    @Test
    void test_sucesso_ao_verificar_estado_pelo_nome_da_cidade() {
        HashMap<String, String> params = new HashMap<>();
        params.put("nome", "Taguatinga");
        ResponseEntity<List<CidadeDTO>> cidadeByFilter = cidadeController.getCidadeByFilter(params);
        Assertions.assertEquals("Distrito Federal", Objects.requireNonNull(cidadeByFilter.getBody()).get(0).getEstado());
    }

    @Test
    void test_deve_lancar_erro_ao_nao_encontrar_cidade_pelo_estado() {
        HashMap<String, String> params = new HashMap<>();
        params.put("estado", "Minas Gerais");

        try {
            cidadeController.getCidadeByFilter(params);
            Assertions.fail();
        } catch (NotFoundException e) {
            Assertions.assertEquals("Nenhuma cidade encontrada para o estado: Minas Gerais", e.getMessage());
        }
    }

    @Test
    void test_http_status_200_quando_encontrar_cidade_pelo_estado() {
        HashMap<String, String> params = new HashMap<>();
        params.put("estado", "Rio de Janeiro");
        ResponseEntity<List<CidadeDTO>> cidadeByFilter = cidadeController.getCidadeByFilter(params);
        Assertions.assertEquals(HttpStatus.OK, cidadeByFilter.getStatusCode());
    }

    @Test
    void test_sucesso_ao_verificar_nome_cidade_pelo_estado() {
        HashMap<String, String> params = new HashMap<>();
        params.put("estado", "Distrito Federal");
        ResponseEntity<List<CidadeDTO>> cidadeByFilter = cidadeController.getCidadeByFilter(params);
        Assertions.assertEquals("Taguatinga", Objects.requireNonNull(cidadeByFilter.getBody()).get(0).getNome());
    }

    @Test
    void test_sucesso_ao_incluir_nova_cidade_estado() {
        CidadeDTO newCidade = new CidadeDTO("Brazlândia", "Distrito Federal");
        ResponseEntity<CidadeDTO> cidadeAdicionada = cidadeController.addCidade(newCidade);
        Assertions.assertNotNull(cidadeAdicionada);
    }

    @Test
    void test_sucesso_nome_cidade_que_foi_inserida_na_base() {
        CidadeDTO newCidade = new CidadeDTO("Aracaju", "Sergipe");
        ResponseEntity<CidadeDTO> cidadeAdicionada = cidadeController.addCidade(newCidade);
        Assertions.assertEquals("Sergipe", Objects.requireNonNull(cidadeAdicionada.getBody()).getEstado());
    }

    @Test
    void test_deve_lancar_erro_ao_inserir_cidade_duplicada() {
        CidadeDTO newCidade = new CidadeDTO("Taguatinga", "Distrito Federal");
        try {
            cidadeController.addCidade(newCidade);
            Assertions.fail("Deveria ter lançado uma InsertException, pois Aracaju já existe.");
        } catch (InsertException e) {
            Assertions.assertEquals("A cidade Taguatinga já existe.",e.getMessage());
        }
    }


}