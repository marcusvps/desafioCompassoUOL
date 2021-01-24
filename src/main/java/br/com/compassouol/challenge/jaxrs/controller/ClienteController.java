package br.com.compassouol.challenge.jaxrs.controller;

import br.com.compassouol.challenge.dao.ClienteDAOImpl;
import br.com.compassouol.challenge.dto.ClienteDTO;
import br.com.compassouol.challenge.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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
     *
     * @param params
     * @return
     * @throws NotFoundException
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
     *
     * @param newCliente
     * @return
     */
    @PostMapping(path = "/add",consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<ClienteDTO> addCliente(@Valid @RequestBody ClienteDTO newCliente) {
        ClienteDTO clienteAdicionado = clienteDAO.addCliente(newCliente);
        return new ResponseEntity<>(clienteAdicionado, HttpStatus.OK);

    }

    /**
     *
     * @param updateCliente
     * @return
     */
    public ResponseEntity<ClienteDTO> updateCliente(ClienteDTO updateCliente) {
        ClienteDTO clienteAtualizado = clienteDAO.updateCliente(updateCliente);
        return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
    }


    @DeleteMapping(path = "/delete")
    public ResponseEntity<List<ClienteDTO>> deleteCliente(@RequestParam Long id) {
        List<ClienteDTO> clientesRestantes = clienteDAO.deleteCliente(id);
        if(null != clientesRestantes && !clientesRestantes.isEmpty()){
            return new ResponseEntity<>(clientesRestantes, HttpStatus.OK);
        }
        throw new NotFoundException("Nenhum cliente restante na base!");
    }
}
