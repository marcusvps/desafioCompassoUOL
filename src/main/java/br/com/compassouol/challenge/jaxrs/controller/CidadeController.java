package br.com.compassouol.challenge.jaxrs.controller;

import br.com.compassouol.challenge.dao.CidadeDAOImpl;
import br.com.compassouol.challenge.dto.CidadeDTO;
import br.com.compassouol.challenge.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
     *
     * @param params
     * @return
     */
    public ResponseEntity<List<CidadeDTO>> getCidadeByFilter(Map<String, String> params) {
        List<CidadeDTO> cidades = null;
        String nome =  Optional.ofNullable(params.get("nome")).orElse(null);
        String estado = Optional.ofNullable(params.get("estado")).orElse(null);
        if(null != nome){
            cidades = Collections.singletonList(Optional
                    .ofNullable(cidadeDAO.getByName(nome))
                    .orElseThrow(() -> new NotFoundException("Nenhuma cidade encontrado para o nome: " + nome)));
        }

        if(null != estado){
            cidades = Optional
                    .ofNullable(cidadeDAO.getByEstado(estado))
                    .orElseThrow(() -> new NotFoundException("Nenhuma cidade encontrada para o estado: " + estado));
        }

        return new ResponseEntity<>(cidades, HttpStatus.OK);
    }

    /**
     *
     * @param newCidade
     * @return
     */
    public ResponseEntity<CidadeDTO> addCidade(CidadeDTO newCidade) {
        CidadeDTO cidadeAdicionada = cidadeDAO.addCidade(newCidade);
        return new ResponseEntity<>(cidadeAdicionada, HttpStatus.OK);
    }
}