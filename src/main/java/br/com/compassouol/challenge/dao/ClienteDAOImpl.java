package br.com.compassouol.challenge.dao;

import br.com.compassouol.challenge.dto.CidadeDTO;
import br.com.compassouol.challenge.dto.ClienteDTO;
import br.com.compassouol.challenge.exception.AtualizarClienteException;
import br.com.compassouol.challenge.exception.DeletarClienteException;
import br.com.compassouol.challenge.exception.NovoClienteException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementação para acessar as informações em memoria relacionadas com o Cliente.
 * @author marcussantos
 */
@Component
public class ClienteDAOImpl {

    static Map<Long, ClienteDTO> mapClientes = new HashMap<>();

    public ClienteDAOImpl() {
        createMapClientes();
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
        return mapClientes
                .values()
                .stream()
                .filter(cliente -> cliente.getNomeCompleto().toUpperCase().contains(nome.toUpperCase()))
                .collect(Collectors.toList());

    }

    /**
     * Responsavel por adicionar um cliente na base de dados.
     * @param newCliente - {@link ClienteDTO} com todas as informações obrigatorias.
     * @return - O {@link ClienteDTO} que foi incluso na base de dados ou lança {@link NovoClienteException} quando o cliente já existir, baseado no id.
     */
    public ClienteDTO addCliente(ClienteDTO newCliente) {
        if(mapClientes.containsKey(newCliente.getId())){
            throw new NovoClienteException("O cliente de id " + newCliente.getId() + " já existe.");
        }

        //Garantindo que a idade vai ser preenchida.
        if(null == newCliente.getIdade()) newCliente.calcularIdade();

        mapClientes.put(newCliente.getId(),newCliente);
        return mapClientes.get(newCliente.getId());
    }

    /**
     * Responsavel por atualizar os dados de um cliente existente na base de dados.
     * @param updateCliente - {@link ClienteDTO} com todas as informações obrigatorias.
     * @return - O {@link ClienteDTO} que foi atualizado na base ou lança {@link AtualizarClienteException} quando o cliente não existir.
     */
    public ClienteDTO updateCliente(ClienteDTO updateCliente) {
        if(mapClientes.containsKey(updateCliente.getId())){
            mapClientes.replace(updateCliente.getId(), updateCliente);
            if(null == updateCliente.getIdade()) updateCliente.calcularIdade();
            return mapClientes.get(updateCliente.getId());
        }else{
            throw new AtualizarClienteException("O Cliente de id: " + updateCliente.getId() + " não existe.");
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
            return mapClientes.values().stream().collect(Collectors.toList());
        }else{
            throw new DeletarClienteException("O Cliente de id: " + id + " não existe.");
        }
    }

    /**
     *
     * @return
     */
    public void createMapClientes(){
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



}
