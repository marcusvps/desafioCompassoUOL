package br.com.compassouol.challenge.dao;

import br.com.compassouol.challenge.dao.entity.Cliente;
import br.com.compassouol.challenge.dto.CidadeDTO;
import br.com.compassouol.challenge.dto.ClienteDTO;
import br.com.compassouol.challenge.exception.UpdateException;
import br.com.compassouol.challenge.exception.DeleteException;
import br.com.compassouol.challenge.exception.InsertException;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * Implementação para acessar as informações em memoria relacionadas com o Cliente.
 * @author marcussantos
 */
@Component
public class ClienteDAOImpl {

    @Resource
    ClienteRepository clienteRepository;

    @Autowired
    private CidadeDAOImpl cidadeDAO;

    DozerBeanMapper mapper;

    /**
     * Ao construir o objeto ClienteDAOImpl, já é preenchido a mapClientes.
     */
    public ClienteDAOImpl() {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml"));
    }

    /**
     * Responsavel por identificar um {@link ClienteDTO} utilizando o id.
     * @param id - Identificador unico do cliente
     * @return - ClienteDTO com os dados do cliente ou null
     */
    public ClienteDTO getById(Long id){
        Optional<Cliente> entidade = clienteRepository.findById(id);
        if(entidade.isPresent()) return mapper.map(entidade.get(), ClienteDTO.class);
        return null;
    }

    /**
     * Responsavel por identificar uma lista de {@link ClienteDTO} utilizando o nome.
     * OBS: este metodo busca por todos os clientes que possuam esta string no seu nome completo.
     * EX: nome: Maria, retorna Maria do Carmo e Maria Aparecida
     * @param nome - Nome Do cliente
     * @return - Uma lista com os clientes que possuirem o nome do parametro.
     */
    public List<ClienteDTO> getByName(String nome) {
        List<ClienteDTO> clientes = new ArrayList<>();
        List<Optional<Cliente>> entidades = clienteRepository.findByNome(nome);
        for (Optional<Cliente> entidade : entidades) {
            if(entidade.isPresent()){
                clientes.add(mapper.map(entidade.get(), ClienteDTO.class));
            }
        }

        return clientes.isEmpty() ? null : clientes;

    }

    /**
     *  Responsavel por identificar uma lista de {@link ClienteDTO} utilizando o sexo.
     * @param siglaSexo - Sigla do Sexo do Cliente (F,M,O)
     * @return - Uma lista com os clientes que possuirem o sexo do parametro.
     */
    public List<ClienteDTO> getBySexo(String siglaSexo) {
        ClienteDTO.EnumSexo enumSexo = ClienteDTO.EnumSexo.getBySigla(siglaSexo);
        List<ClienteDTO> clientes = new ArrayList<>();
        List<Optional<Cliente>> entidades = clienteRepository.findBySexo(enumSexo.name());
        for (Optional<Cliente> entidade : entidades) {
            if(entidade.isPresent()){
                clientes.add(mapper.map(entidade.get(), ClienteDTO.class));
            }
        }

        return clientes.isEmpty() ? null : clientes;
    }

    /**
     * Responsavel por adicionar um cliente na base de dados.
     * @param newCliente - {@link ClienteDTO} com todas as informações obrigatorias.
     * @return - O {@link ClienteDTO} que foi incluso na base de dados ou lança {@link InsertException} quando o cliente já existir, baseado no id.
     */
    public ClienteDTO addCliente(ClienteDTO newCliente) {
        validarClienteJaExistente(newCliente);
        validarCidade(newCliente);
        calcularIdadeSeNecessario(newCliente);

        Cliente entidade = mapper.map(newCliente, Cliente.class);
        Cliente entidadeSalva =
                clienteRepository.save(entidade);

        return this.getById(entidadeSalva.getId());
    }



    /**
     * Responsavel por atualizar os dados de um cliente existente na base de dados.
     * @param updateCliente - {@link ClienteDTO} com todas as informações obrigatorias.
     * @return - O {@link ClienteDTO} que foi atualizado na base ou lança {@link UpdateException} quando o cliente não existir.
     */
    public ClienteDTO updateCliente(ClienteDTO updateCliente) {
        if(null != this.getById(updateCliente.getId())){
            validarCidade(updateCliente);

            calcularIdadeSeNecessario(updateCliente);

            Cliente entidade = mapper.map(updateCliente, Cliente.class);
            Cliente entidadeSalva =
                    clienteRepository.save(entidade);
            return this.getById(entidadeSalva.getId());

        }else{
            throw new UpdateException("O Cliente de id: " + updateCliente.getId() + " não existe.");
        }
    }

    /**
     * Responsavel por apagar os dados de um cliente existente na base de dados.
     * @param id - Identificador unico do cliente.
     * @return - Lista de Clientes restante na base de dados.
     * @throws DeleteException quando o cliente não existir na base.
     */
    public List<ClienteDTO> deleteCliente(Long id) {
        List<ClienteDTO> retorno = new ArrayList<>();
        if(null != this.getById(id)){
            clienteRepository.deleteById(id);
            List<Cliente> clientesRestantes = clienteRepository.findAll();
            for (Cliente cliente : clientesRestantes) {
                retorno.add(mapper.map(cliente, ClienteDTO.class));
            }
            return retorno;
        }else{
            throw new DeleteException("O Cliente de id: " + id + " não existe.");
        }
    }

    /**
     * Responsavel por validar se o cliente já existe na base de dados.
     * @param newCliente - {@link ClienteDTO} com os dados completos do cliente a ser adicionado.
     * @exception InsertException - Quando o cliente já existir na base, baseado em seu id.
     */
    private void validarClienteJaExistente(ClienteDTO newCliente) {
        if(null != this.getById(newCliente.getId())){
            throw new InsertException("O cliente de id " + newCliente.getId() + " já existe.");
        }
    }

    /**
     * Responsavel por calcular a idade do Cliente quando a mesma não for informada.
     * @param cliente - {@link ClienteDTO} que estiver sendo inserido ou atualizado.
     */
    private void calcularIdadeSeNecessario(ClienteDTO cliente) {
        if (isNull(cliente.getIdade())) cliente.calcularIdade();
    }

    /**
     * Responsavel por validar se a cidade do {@link ClienteDTO} é um cidade que existe na base de dados.
     * @param cliente -  {@link ClienteDTO} que estiver sendo inserido ou atualizado.
     * @throws InsertException - Quando a cidade informado for inexistente.
     */
    private void validarCidade(ClienteDTO cliente) {
        CidadeDTO cidade;
        if(isNull(cliente.getCidadeDTO())){
            if(isNull(cliente.getNomeCidade())){
                throw new InsertException("Não foi informado nenhuma cidade para o cliente: " + cliente.getNomeCompleto());
            }
            cidade = getCidadeDAO().getByName(cliente.getNomeCidade());
        }else{
            cidade = getCidadeDAO().getByName(cliente.getCidadeDTO().getNome());
        }


        if(isNull(cidade)){
            throw new InsertException("A cidade " + cliente.getCidadeDTO().getNome() +
                    " informada para o(a) cliente " + cliente.getNomeCompleto() +
                    " não existe!");
        }else{
            cliente.setCidadeDTO(cidade);
        }
    }


    public CidadeDAOImpl getCidadeDAO() {
        return cidadeDAO;
    }


}
