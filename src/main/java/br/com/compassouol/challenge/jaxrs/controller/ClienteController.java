package br.com.compassouol.challenge.jaxrs.controller;

import br.com.compassouol.challenge.dao.ClienteDAOImpl;
import br.com.compassouol.challenge.dto.ClienteDTO;
import br.com.compassouol.challenge.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller responsavel por receber, processar e retornar
 * as requisicoes rest para o endopoint '/cliente/'
 * @author marcussantos
 */
@RestController
@RequestMapping("/cliente")
public class ClienteController implements IController {

    @Autowired
    private ClienteDAOImpl clienteDAO;

    /**
     * Responsavel por receber a requisição de buscar cliente por parametros(id ou nome).
     * @param params - {@link Map} com os parametros recebidos na requisição.
     * @return ResponseEntity com HttpStatus e Body no formato JSON.
     * @throws NotFoundException - Quando o cliente não for encontrado pelo id ou nome.
     */
    @GetMapping(path = "/get")
    @ResponseBody
    public ResponseEntity<List<ClienteDTO>> getClientByFilter(@RequestParam Map<String,String> params) throws NotFoundException {
        List<ClienteDTO> clientes = null;
        String id = Optional.ofNullable(params.get("id")).orElse(null);
        String nome =  Optional.ofNullable(params.get("nome")).orElse(null);
        if(null != id){
            clientes = Collections.singletonList(Optional
                    .ofNullable(clienteDAO.getById(Long.valueOf(id)))
                    .orElseThrow(() -> new NotFoundException("Nenhum cliente encontrado para o id: " + id)));

        }

        if(null != nome){
            clientes = Optional
                    .ofNullable(clienteDAO.getByName(nome))
                    .orElseThrow(() -> new NotFoundException("Nenhum cliente encontrado para o nome: " + nome));

        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);

    }

    /**
     * Responsavel por receber a requisição de adicionar um novo cliente.
     * @param newCliente - {@link ClienteDTO} que foi enviado na requisicao, com os dados do novo cliente.
     * @return - {@link ClienteDTO} que foi adicionado na base de dados.
     */
    @PostMapping(path = "/add",consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<ClienteDTO> addCliente(@Valid @RequestBody ClienteDTO newCliente) {
        ClienteDTO clienteAdicionado = clienteDAO.addCliente(newCliente);
        return new ResponseEntity<>(clienteAdicionado, HttpStatus.OK);

    }

    /**
     * Responsavel por receber a requisição de atualizar um cliente existente.
     * @param updateCliente - {@link ClienteDTO} que foi enviado na requisicao, com os dados do cliente a ser atualizado.
     * @return - {@link ClienteDTO} que foi atualizado na base de dados.
     */
    @PutMapping(path = "/update",consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<ClienteDTO> updateCliente(@Valid @RequestBody ClienteDTO updateCliente) {
        ClienteDTO clienteAtualizado = clienteDAO.updateCliente(updateCliente);
        return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
    }


    /**
     * Responsavel por receber a requisição de deletar um cliente existente.
     * @param id - identificador unico do cliente.
     * @return - Lista com os {@link ClienteDTO} restantes na base.
     */
    @DeleteMapping(path = "/delete")
    @ResponseBody
    public ResponseEntity<List<ClienteDTO>> deleteCliente(@RequestParam Long id) {
        List<ClienteDTO> clientesRestantes = clienteDAO.deleteCliente(id);
        if(null != clientesRestantes && !clientesRestantes.isEmpty()){
            return new ResponseEntity<>(clientesRestantes, HttpStatus.OK);
        }
        throw new NotFoundException("Nenhum cliente restante na base!");
    }


    public ClienteDAOImpl getClienteDAO() {
        return clienteDAO;
    }
}
