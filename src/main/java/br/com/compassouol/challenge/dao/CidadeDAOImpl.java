package br.com.compassouol.challenge.dao;

import br.com.compassouol.challenge.dao.entity.Cidade;
import br.com.compassouol.challenge.dto.CidadeDTO;
import br.com.compassouol.challenge.exception.InsertException;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Implementação para acessar as informações em memoria relacionadas com a Cidade.
 * @author marcussantos
 */
@Component
public class CidadeDAOImpl {

    @Resource
    private CidadeRepository cidadeRepository;

    final DozerBeanMapper mapper = new DozerBeanMapper();

    public CidadeDAOImpl() {}

    /**
     * Responsavel por identificar uma {@link CidadeDTO} pelo nome;
     * @param nome - Nome da cidade a ser pesquisada.
     * @return - {@link CidadeDTO} com os dados da cidade ou null
     */
    public CidadeDTO getByName(String nome) {
        Cidade entidade = cidadeRepository.findByName(nome);
        if(Objects.nonNull(entidade)) return mapper.map(entidade,CidadeDTO.class);
        return null;
    }


    /**
     * Responsavel por identificar Cidades pelo estado;
     * OBS: este metodo busca por todos os estados que possuam esta string no seu nome.
     * EX: nome: Rio, retorna Rio de Janeiro e Rio Grande do Sul.
     * @param estado - Nome do estado a ser pesquisado.
     * @return - Uma lista com os estados que possuirem o parametro informado.
     */
    public List<CidadeDTO> getByEstado(String estado) {
        List<CidadeDTO> retorno = new ArrayList<>();
        List<Cidade> entidades = cidadeRepository.findByEstado(estado);
        if(Objects.nonNull(entidades) && !entidades.isEmpty()){
            for (Cidade entidade : entidades) {
                retorno.add(mapper.map(entidade, CidadeDTO.class));
            }
            return retorno;
        }
      return null;

    }

    /**
     * Responsavel por adiconar uma cidade na base de dados
     * @param newCidade - {@link CidadeDTO} com os dados da nova cidade
     * @return - Retorna a cidade que foi inserida na base.
     * @throws InsertException - Quando a cidade já existir na base.
     */
    public CidadeDTO addCidade(CidadeDTO newCidade) {
        Cidade entidade = mapper.map(newCidade,Cidade.class);
        if (null != this.getByName(newCidade.getNome())) {
            throw new InsertException("A cidade " + newCidade.getNome() + " já existe.");
        }

        Cidade save = cidadeRepository.save(entidade);
        return this.getByName(save.getNome());
    }






}
