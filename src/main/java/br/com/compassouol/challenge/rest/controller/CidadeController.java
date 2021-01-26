package br.com.compassouol.challenge.rest.controller;

import br.com.compassouol.challenge.dao.impl.CidadeDAOImpl;
import br.com.compassouol.challenge.dto.CidadeDTO;
import br.com.compassouol.challenge.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Controller responsavel por receber, processar e retornar
 * as requisicoes rest para o endopoint '/cidade/'
 * @author marcussantos
 */
@RestController
@RequestMapping("/cidade")
public class CidadeController implements IController {

    @Autowired
    private CidadeDAOImpl cidadeDAO;

    /**
     * Responsavel por receber a requisicao de buscar cidades por parametros(nome ou estado)
     * @param params - {@link Map} com os parametros recebidos na requisição.
     * @return ResponseEntity com HttpStatus e Body no formato JSON.
     * @throws NotFoundException - Quando a cidade não for encontrada pelo nome ou estado.
     */
    @GetMapping(path = "/get")
    @ResponseBody
    public ResponseEntity<List<CidadeDTO>> getCidadeByFilter(@RequestParam Map<String, String> params) {
        List<CidadeDTO> cidades;
        String nome =  Optional.ofNullable(params.get("nome")).orElse(null);
        String estado = Optional.ofNullable(params.get("estado")).orElse(null);
        if(Objects.nonNull(nome)){
            cidades = recuperarCidadesPeloNome(nome);
        }else if(Objects.nonNull(estado)){
            cidades = recuperarCidadesPeloEstado(estado);
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(cidades, HttpStatus.OK);
    }

    /**
     * Busca na base cidades pelo estado
     * @param estado - Estado da cidade
     * @return - Lista com as {@link CidadeDTO} que pertençam ao estado.
     */
    private List<CidadeDTO> recuperarCidadesPeloEstado(String estado) {
        return Optional
                .ofNullable(cidadeDAO.getByEstado(estado))
                .orElseThrow(() -> new NotFoundException("Nenhuma cidade encontrada para o estado: " + estado));
    }

    /**
     * Busca na base a cidade pelo seu nome
     * @param nome - Nome da cidade
     * @return - {@link CidadeDTO} encontrada na base.
     */
    private List<CidadeDTO> recuperarCidadesPeloNome(String nome) {
        return Collections.singletonList(Optional
                .ofNullable(cidadeDAO.getByName(nome))
                .orElseThrow(() -> new NotFoundException("Nenhuma cidade encontrada para o nome: " + nome)));
    }

    /**
     * Responsavel por receber a requisição de adicionar uma nova cidade
     * @param newCidade - {@link CidadeDTO} que foi enviado na requisicao, com os dados da nova Cidade.
     * @return - {@link CidadeDTO} que foi adicionada na base de dados.
     */
    @PostMapping(path = "/add",consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<CidadeDTO> addCidade(@Valid @RequestBody CidadeDTO newCidade) {
        CidadeDTO cidadeAdicionada = cidadeDAO.addCidade(newCidade);
        return new ResponseEntity<>(cidadeAdicionada, HttpStatus.OK);
    }

}