package br.com.compassouol.challenge.jaxrs.controller;

import br.com.compassouol.challenge.dto.CidadeDTO;
import br.com.compassouol.challenge.dto.ClienteDTO;
import br.com.compassouol.challenge.exception.InsertException;
import br.com.compassouol.challenge.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
@ExtendWith(SpringExtension.class)
class ClienteControllerTest {

    @SpyBean
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        clienteController.getClienteDAO().limparMapClientes();
        clienteController.getClienteDAO().preencherMapClientes();
    }

    @Test()
    void test_deve_retornar_erro_ao_buscar_cliente_nao_cadastrado()  {
            HashMap<String, String> params = new HashMap<>();
            params.put("id","123123123");

        try {
            clienteController.getClientByFilter(params);
            Assertions.fail("Deveria ter sido lançado uma NotFoundException");
        } catch (NotFoundException e) {
           Assertions.assertEquals("Nenhum cliente encontrado para o id: 123123123",e.getMessage());
        }


    }

    @Test
    void test_http_status_200_ao_buscar_cliente_existente() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id","2");

        ResponseEntity<List<ClienteDTO>> clientByFilter = clienteController.getClientByFilter(params);
        Assertions.assertEquals(HttpStatus.OK.value(), clientByFilter.getStatusCodeValue());
    }

    @Test
    void test_http_status_200_ao_buscar_cliente_existente_pelo_nome() {
        HashMap<String, String> params = new HashMap<>();
        params.put("nome","maria");
        ResponseEntity<List<ClienteDTO>> clientByFilter = clienteController.getClientByFilter(params);
        Assertions.assertEquals(HttpStatus.OK.value(), clientByFilter.getStatusCodeValue());
    }

    @Test
    void test_inserir_novo_cliente_com_sucesso() {
        ClienteDTO newCliente = new ClienteDTO(5L,
                "Antonieta dos Santos",
                ClienteDTO.EnumSexo.FEMININO,
                LocalDate.of(1996,5,25),
                new CidadeDTO("Taguatinga","Distrito Federal"));

        ResponseEntity<ClienteDTO> clienteAdiconado = clienteController.addCliente(newCliente);
        Assertions.assertNotNull(clienteAdiconado);
    }

    @Test
    void test_inserir_novo_cliente_sem_informar_idade() {
        ClienteDTO newCliente = new ClienteDTO();
        newCliente.setNomeCompleto("Maria dos Reis");
        newCliente.setSexo(ClienteDTO.EnumSexo.OUTROS);
        newCliente.setId(99L);
        newCliente.setDataNascimento(LocalDate.of(1990,3,2));
        newCliente.setCidadeDTO(new CidadeDTO("Rio de Janeiro","Rio de Janeiro"));

        ResponseEntity<ClienteDTO> clienteAdiconado = clienteController.addCliente(newCliente);
        Assertions.assertEquals(Integer.valueOf(31), Objects.requireNonNull(clienteAdiconado.getBody()).getIdade());

    }

    @Test
    void test_deve_lancar_exception_ao_incluir_cliente_ja_existente() {
            ClienteDTO newCliente = new ClienteDTO(1L,
                    "Antonieta dos Santos",
                    ClienteDTO.EnumSexo.FEMININO,
                    LocalDate.of(1996,5,25),
                    new CidadeDTO("Taguatinga","Distrito Federal"));

        try {
            clienteController.addCliente(newCliente);
            Assertions.fail();
        } catch (InsertException e) {
            Assertions.assertEquals("O cliente de id 1 já existe.",e.getMessage());
        }

    }


    @Test
    void test_deve_atualizar_cliente_com_sucesso() {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setId(1L);
        cliente.setCidadeDTO(new CidadeDTO("Taguatinga","Dis"));
        cliente.setSexo(ClienteDTO.EnumSexo.MASCULINO);
        cliente.setDataNascimento(LocalDate.of(1900,4,9));
        cliente.setNomeCompleto("NOME ATUALIZADO - UPDATE");

        ResponseEntity<ClienteDTO> clienteAtualizado = clienteController.updateCliente(cliente);

        Assertions.assertEquals("NOME ATUALIZADO - UPDATE", Objects.requireNonNull(clienteAtualizado.getBody()).getNomeCompleto());
    }

    @Test
    void teste_deve_deletar_cliente_com_sucesso() {
        Long id = 2L;
        ResponseEntity<List<ClienteDTO>> clientesRestantes = clienteController.deleteCliente(id);

        boolean present = Objects.requireNonNull(clientesRestantes
                .getBody())
                .stream()
                .anyMatch(cliente -> cliente.getId().equals(id));

        Assertions.assertFalse(present);
    }

    @Test
    void test_deve_lancar_exception_ao_nao_sobrar_clientes_na_base() {
        clienteController.deleteCliente(1L);
        clienteController.deleteCliente(2L);
        clienteController.deleteCliente(3L);
        try {
            clienteController.deleteCliente(4L);
            Assertions.fail("Deveria ter sido lançado uma NotFoundException, pois não existe mais clientes na base.");
        } catch (NotFoundException e) {
           Assertions.assertEquals("Nenhum cliente restante na base!",e.getMessage());
        }
    }

    @Test
    void test_deve_retornar_erro_ao_incluir_cliente_para_cidade_invalida() {
            ClienteDTO newCliente = new ClienteDTO(99L,
                    "Antonieta dos Santos",
                    ClienteDTO.EnumSexo.FEMININO,
                    LocalDate.of(1996,5,25),
                    new CidadeDTO("Taboquinha","Goias"));

        try {

            clienteController.addCliente(newCliente);
        } catch (InsertException e) {
            Assertions.assertEquals("A cidade Taboquinha informada para o(a) cliente Antonieta dos Santos não existe!",e.getMessage());
        }

    }

    @Test
    void test_deve_recuperar_cidadeDTO_apenas_pelo_nome_cidade() {
        ClienteDTO newCliente = new ClienteDTO(99L,
                "Antonieta dos Santos",
                ClienteDTO.EnumSexo.FEMININO,
                LocalDate.of(1996,5,25),
                "Taguatinga");

        ResponseEntity<ClienteDTO> resposta = clienteController.addCliente(newCliente);

        Assertions.assertEquals("Distrito Federal",resposta.getBody().getCidadeDTO().getEstado());
    }

    @Test
    void test_deve_lancar_erro_ao_nao_ter_informacoes_de_cidade() {
        ClienteDTO newCliente = new ClienteDTO();
        newCliente.setId(199L);
        newCliente.setNomeCompleto("Rosangela dos Santos");
        newCliente.setSexo(ClienteDTO.EnumSexo.OUTROS);

        try {
            clienteController.addCliente(newCliente);
            Assertions.fail();
        } catch (InsertException e) {
             Assertions.assertEquals("Não foi informado nenhuma cidade para o cliente: Rosangela dos Santos",e.getMessage());
        }
    }
}