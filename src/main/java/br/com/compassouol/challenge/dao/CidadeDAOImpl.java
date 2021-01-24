package br.com.compassouol.challenge.dao;

import br.com.compassouol.challenge.dto.CidadeDTO;
import br.com.compassouol.challenge.exception.InsertException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementação para acessar as informações em memoria relacionadas com a Cidade.
 * @author marcussantos
 */
@Component
public class CidadeDAOImpl {
    static Map<String, CidadeDTO> mapCidades = new HashMap<>();

    /**
     * Ao construir o objeto CidadeDAOImpl, já é preenchido a mapCidades.
     */
    public CidadeDAOImpl() {
        this.preencherMapCidades();
    }

    /**
     *
     * @param nome
     * @return
     */
    public CidadeDTO getByName(String nome) {
        return mapCidades.get(nome.toUpperCase());
    }


    /**
     *
     * @param estado
     * @return
     */
    public List<CidadeDTO> getByEstado(String estado) {
        List<CidadeDTO> estados = mapCidades
                .values()
                .stream()
                .filter(cidade -> cidade.getEstado().toUpperCase().contains(estado.toUpperCase()))
                .collect(Collectors.toList());

        return estados.isEmpty() ? null : estados;
    }

    /**
     *
     * @param newCidade
     * @return
     */
    public CidadeDTO addCidade(CidadeDTO newCidade) {
        if(mapCidades.containsKey(newCidade.getNome().toUpperCase())){
            throw new InsertException("A cidade " + newCidade.getNome() + " já existe.");
        }

        mapCidades.put(newCidade.getNome().toUpperCase(), newCidade);
        return mapCidades.get(newCidade.getNome().toUpperCase());
    }


    /**
     * Popula o mapCidades com as {@link CidadeDTO} e armazena em memoria.
     */
    public void preencherMapCidades() {
        CidadeDTO cidade1 = new CidadeDTO();
        cidade1.setNome("Rio de Janeiro");
        cidade1.setEstado("Rio de Janeiro");

        CidadeDTO cidade2 = new CidadeDTO();
        cidade2.setNome("Taguatinga");
        cidade2.setEstado("Distrito Federal");

        CidadeDTO cidade3 = new CidadeDTO();
        cidade3.setNome("Porto Alegre");
        cidade3.setEstado("Rio Grande do Sul");

        CidadeDTO cidade4 = new CidadeDTO();
        cidade4.setNome("Rio Branco");
        cidade4.setEstado("Acre");

        mapCidades.put(cidade1.getNome().toUpperCase(),cidade1);
        mapCidades.put(cidade2.getNome().toUpperCase(),cidade2);
        mapCidades.put(cidade3.getNome().toUpperCase(),cidade3);
        mapCidades.put(cidade4.getNome().toUpperCase(),cidade4);

    }

    /**
     * Retira todos os objetos dentro do mapCidades.
     */
    public void limparMapCidades(){
        mapCidades.clear();
    }



}
