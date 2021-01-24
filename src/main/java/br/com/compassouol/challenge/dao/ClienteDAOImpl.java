package br.com.compassouol.challenge.dao;

import br.com.compassouol.challenge.dto.CidadeDTO;
import br.com.compassouol.challenge.dto.ClienteDTO;
import br.com.compassouol.challenge.exception.UpdateException;
import br.com.compassouol.challenge.exception.DeleteException;
import br.com.compassouol.challenge.exception.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    private final Map<Long, ClienteDTO> mapClientes = new HashMap<>();

    @Autowired
    private CidadeDAOImpl cidadeDAO;

    /**
     * Ao construir o objeto ClienteDAOImpl, já é preenchido a mapClientes.
     */
    public ClienteDAOImpl() {
        preencherMapClientes();
    }

    /**
     * Responsavel por identificar um {@link ClienteDTO} utilizando o id.
     * @param id - Identificador unico do cliente
     * @return - ClienteDTO com os dados do cliente ou null
     */
    public ClienteDTO getById(Long id){
        return mapClientes.get(id);
    }

    /**
     * Responsavel por identificar um {@link ClienteDTO} utilizando o nome.
     * OBS: este metodo busca por todos os clientes que possuam esta string no seu nome completo.
     * EX: nome: Maria, retorna Maria do Carmo e Maria Aparecida
     * @param nome - Nome Do cliente
     * @return - Uma lista com os clientes que possuirem o nome do parametro.
     */
    public List<ClienteDTO> getByName(String nome) {
        List<ClienteDTO> clientes = mapClientes
                .values()
                .stream()
                .filter(cliente -> cliente.getNomeCompleto().toUpperCase().contains(nome.toUpperCase()))
                .collect(Collectors.toList());
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
        mapClientes.put(newCliente.getId(),newCliente);

        return mapClientes.get(newCliente.getId());
    }

    /**
     * Responsavel por validar se o cliente já existe na base de dados.
     * @param newCliente - {@link ClienteDTO} com os dados completos do cliente a ser adicionado.
     * @exception InsertException - Quando o cliente já existir na base, baseado em seu id.
     */
    private void validarClienteJaExistente(ClienteDTO newCliente) {
        if(mapClientes.containsKey(newCliente.getId())){
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
     * @exception - {@link InsertException} - Quando a cidade informado for inexistente.
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

    /**
     * Responsavel por atualizar os dados de um cliente existente na base de dados.
     * @param updateCliente - {@link ClienteDTO} com todas as informações obrigatorias.
     * @return - O {@link ClienteDTO} que foi atualizado na base ou lança {@link UpdateException} quando o cliente não existir.
     */
    public ClienteDTO updateCliente(ClienteDTO updateCliente) {
        if(mapClientes.containsKey(updateCliente.getId())){
            validarCidade(updateCliente);

            calcularIdadeSeNecessario(updateCliente);
            mapClientes.replace(updateCliente.getId(), updateCliente);

            return mapClientes.get(updateCliente.getId());
        }else{
            throw new UpdateException("O Cliente de id: " + updateCliente.getId() + " não existe.");
        }
    }

    /**
     * Responsavel por apagar os dados de um cliente existente na base de dados.
     * @param id
     * @return
     */
    public List<ClienteDTO> deleteCliente(Long id) {
        if(mapClientes.containsKey(id)){
            mapClientes.remove(id);
            return new ArrayList<>(mapClientes.values());
        }else{
            throw new DeleteException("O Cliente de id: " + id + " não existe.");
        }
    }

    /**
     * Popula o mapClientes com as {@link ClienteDTO} e armazena em memoria.
     */
    public void preencherMapClientes(){
        ClienteDTO cliente1 =
                new ClienteDTO(1L,
                        "José de Assis",
                        ClienteDTO.EnumSexo.MASCULINO,
                        LocalDate.of(1995,9,21),
                        new CidadeDTO("Rio de Janeiro","Rio de Janeiro"));

        ClienteDTO cliente2 =
                new ClienteDTO(2L,
                        "Maria do Carmo",
                        ClienteDTO.EnumSexo.FEMININO,
                        LocalDate.of(1956,2,9),
                        new CidadeDTO("Rio de Janeiro","Tocantins"));

        ClienteDTO cliente3 =
                new ClienteDTO(3L,
                        "Ricardo de Almeida",
                        ClienteDTO.EnumSexo.OUTROS,
                        LocalDate.of(2011,7,14),
                        new CidadeDTO("Taguatinga","Distrito Federal"));

        ClienteDTO cliente4 =
                new ClienteDTO(4L,
                        "Maria de Assunção",
                        ClienteDTO.EnumSexo.FEMININO,
                        LocalDate.of(1956,6,4),
                        new CidadeDTO("São Paulo","São Paulo"));

        mapClientes.put(cliente1.getId(),cliente1);
        mapClientes.put(cliente2.getId(),cliente2);
        mapClientes.put(cliente3.getId(),cliente3);
        mapClientes.put(cliente4.getId(),cliente4);
    }

    /**
     * Retira todos os objetos dentro do mapClientes.
     */
    public void limparMapClientes(){
        mapClientes.clear();
    }


    public CidadeDAOImpl getCidadeDAO() {
        return cidadeDAO;
    }
}
