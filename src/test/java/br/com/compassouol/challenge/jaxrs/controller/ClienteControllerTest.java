package br.com.compassouol.challenge.jaxrs.controller;

import br.com.compassouol.challenge.dto.CidadeDTO;
import br.com.compassouol.challenge.dto.ClienteDTO;
import br.com.compassouol.challenge.exception.NotFoundException;
import br.com.compassouol.challenge.exception.InsertException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author marcussantos
 */
@SpringBootTest
@ExtendWith(SpringExtension.class) //novo RunWith
class ClienteControllerTest {

    @SpyBean
    private ClienteController clienteController;

    @Test()
    void test_404_not_found_response()  {
        Assertions.assertThrows(NotFoundException.class, () -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("id","123123123");
            clienteController.getClientByFilter(params);
        });
    }

    @Test
    void test_200_when_search_cliente_id_2() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id","2");

        ResponseEntity<List<ClienteDTO>> clientByFilter = clienteController.getClientByFilter(params);
        Assertions.assertEquals(HttpStatus.OK.value(), clientByFilter.getStatusCodeValue());
    }

    @Test
    void test_200_when_search_cliente_by_name() {
        HashMap<String, String> params = new HashMap<>();
        params.put("nome","maria");
        ResponseEntity<List<ClienteDTO>> clientByFilter = clienteController.getClientByFilter(params);
        Assertions.assertEquals(HttpStatus.OK.value(), clientByFilter.getStatusCodeValue());
    }

    @Test
    void test_insert_cliente_sucess() {
        ClienteDTO newCliente = new ClienteDTO(4L,
                "Antonieta dos Santos",
                ClienteDTO.EnumSexo.FEMININO,
                LocalDate.of(1996,5,25),
                new CidadeDTO("Taguatinga","Distrito Federal"));

        ResponseEntity<ClienteDTO> clienteAdiconado = clienteController.addCliente(newCliente);
        Assertions.assertNotNull(clienteAdiconado);
    }

    @Test
    void test_insert_cliente_without_idade() {
        ClienteDTO newCliente = new ClienteDTO();
        newCliente.setNomeCompleto("Maria dos Reis");
        newCliente.setSexo(ClienteDTO.EnumSexo.OUTROS);
        newCliente.setId(99L);
        newCliente.setDataNascimento(LocalDate.of(1990,3,2));
        newCliente.setCidade(new CidadeDTO("Rio de Janeiro","Rio de Janeiro"));

        ResponseEntity<ClienteDTO> clienteAdiconado = clienteController.addCliente(newCliente);
        Assertions.assertEquals(Integer.valueOf(31), Objects.requireNonNull(clienteAdiconado.getBody()).getIdade());

    }

    @Test
    void test_insert_duplicate_cliente() {
        Assertions.assertThrows(InsertException.class, () -> {
            ClienteDTO newCliente = new ClienteDTO(1L,
                    "Antonieta dos Santos",
                    ClienteDTO.EnumSexo.FEMININO,
                    LocalDate.of(1996,5,25),
                    new CidadeDTO("Taguatinga","Distrito Federal"));
            clienteController.addCliente(newCliente);
        });
    }


    @Test
    void test_update_cliente_sucess() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setId(1L);
        cliente.setCidade(new CidadeDTO("Caxias do Sul","Rio Grande do Sul"));
        cliente.setSexo(ClienteDTO.EnumSexo.MASCULINO);
        cliente.setDataNascimento(LocalDate.of(1900,4,9));
        cliente.setNomeCompleto("NOME ATUALIZADO - UPDATE");
        ResponseEntity<ClienteDTO> clienteAtualizado = clienteController.updateCliente(cliente);
        Assertions.assertEquals("NOME ATUALIZADO - UPDATE", Objects.requireNonNull(clienteAtualizado.getBody()).getNomeCompleto());
    }

    @Test
    void test_delete_cliente_sucess() {
        Long id = 2L;
        ResponseEntity<List<ClienteDTO>> clientesRestantes = clienteController.deleteCliente(id);
        boolean present = Objects.requireNonNull(clientesRestantes
                .getBody())
                .stream()
                .anyMatch(cliente -> cliente.getId().equals(id));

        Assertions.assertFalse(present);


    }
}