package br.com.compassouol.challenge.dto;

import br.com.compassouol.challenge.exception.NotFoundException;
import org.dozer.Mapping;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/**
 * Representa o objeto Cliente.
 * @author marcussantos
 */
public class ClienteDTO {



    private Long id;

    @NotNull(message = "Nome do cliente deve ser informado.")
    private String nomeCompleto;

    @NotNull(message = "Sexo do cliente deve ser informado.")
    private EnumSexo sexo;

    @NotNull(message = "Data de Nascimento do cliente deve ser informada.")
    private LocalDate dataNascimento;

    private Integer idade;

    @Mapping("cidade")
    private CidadeDTO cidadeDTO;

    private String nomeCidade;

    public ClienteDTO(Long id, String nomeCompleto, EnumSexo sexo, LocalDate dataNascimento, CidadeDTO cidade) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        calcularIdade();
        this.cidadeDTO = cidade;
        this.nomeCidade = cidade.getNome();
    }

    public ClienteDTO(Long id, String nomeCompleto, EnumSexo sexo, LocalDate dataNascimento, String nomeCidade) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        calcularIdade();
        this.nomeCidade = nomeCidade;
    }

    public ClienteDTO() {
    }

    public EnumSexo getSexo() {
        return sexo;
    }


    /**
     * Enumerado representando os sexos disponiveis para Cliente
     */
    public enum EnumSexo {
        FEMININO("F"),
        MASCULINO("M"),
        OUTROS("O");

        private final String sigla;

        EnumSexo(String sigla) {
            this.sigla = sigla;
        }



        /**
         * Recupera o sexo baseado na sigla recebida.
         * @param sigla - sigla dos sexos(F,M,O).
         * @return EnumSexo corresponte a sigla.
         */
        public static EnumSexo getBySigla(String sigla) throws NotFoundException {

            return Arrays.stream(EnumSexo.values())
                    .filter(enumSexo -> enumSexo.sigla.equalsIgnoreCase(sigla))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Sigla inválida para o sexo do cliente."));


        }
    }

    /**
     * Calcula a idade e define no campo idade do {@link ClienteDTO}
     * Calculo é feito com Ano Atual - Ano de Nascimento do Cliente.
     */
    public void calcularIdade() {
        Integer anoNascimento = getDataNascimento().getYear();
        Integer anoAtual = LocalDate.now().getYear();
        this.setIdade(anoAtual - anoNascimento);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setSexo(EnumSexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getIdade() {
        return idade;
    }



    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public CidadeDTO getCidadeDTO() {
        return cidadeDTO;
    }

    public void setCidadeDTO(CidadeDTO cidadeDTO) {
        this.cidadeDTO = cidadeDTO;
    }
    public String getNomeCidade() {
        if(Objects.nonNull(getCidadeDTO())){
            nomeCidade = getCidadeDTO().getNome();
        }

        return nomeCidade;
    }

}
