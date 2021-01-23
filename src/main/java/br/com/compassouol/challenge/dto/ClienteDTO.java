package br.com.compassouol.challenge.dto;



import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa o objeto Cliente.
 * @author marcussantos
 */
public class ClienteDTO {


    @NotNull(message = "id do cliente deve ser informado.")
    private Long id;

    @NotNull(message = "Nome do cliente deve ser informado.")
    private String nomeCompleto;

    @NotNull(message = "Sexo do cliente deve ser informado.")
    private EnumSexo sexo;

    @NotNull(message = "Data de Nascimento do cliente deve ser informada.")
    private LocalDate dataNascimento;

    private Integer idade;

    @NotNull(message = "A cidade  do cliente deve ser informada.")
    private CidadeDTO cidade;

    public ClienteDTO(Long id, String nomeCompleto, EnumSexo sexo, LocalDate dataNascimento, CidadeDTO cidade) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        calcularIdade();
        this.cidade = cidade;
    }

    public ClienteDTO() {
    }

    public enum EnumSexo {
        FEMININO(1,"Feminino",'F'),
        MASCULINO(2,"Masculino",'M'),
        OUTROS(3,"Outros",'O');

        private Integer id;
        private String descricao;
        private char sigla;

        EnumSexo(Integer id, String descricao, char sigla) {
            this.id = id;
            this.descricao = descricao;
            this.sigla = sigla;
        }

        /**
         * Recupera o sexo baseado no id recebido.
         * @param id - identificador unico no EnumSexo.
         * @return EnumSexo corresponte ao ID.
         * @throws Exception - Quando o id não corresponder a nenhum dos EnumSexo.
         */
        public static EnumSexo getById(Integer id) throws Exception {
            EnumSexo[] values = EnumSexo.values();
            for (EnumSexo value : values) {
                if(value.id.equals(id)) return value;
            }
            throw new Exception("Identificador inválido para o sexo do cliente.");
        }

        /**
         * Recupera o sexo baseado na sigla recebida.
         * @param sigla - sigla dos sexos(F,M,O).
         * @return EnumSexo corresponte a sigla.
         * @throws Exception - Quando a sigla não corresponder a nenhum dos EnumSexo.
         */
        public static EnumSexo getBySigla(String sigla) throws Exception {
            EnumSexo[] values = EnumSexo.values();
            for (EnumSexo value : values) {
                if(value.sigla == sigla.charAt(0)) return value;
            }
            throw new Exception("Sigla inválida para o sexo do cliente.");
        }
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "id=" + id +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", sexo=" + sexo +
                ", dataNascimento=" + dataNascimento +
                ", idade=" + idade +
                ", cidade='" + cidade + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteDTO that = (ClienteDTO) o;
        return Objects.equals(id, that.id)
                && Objects.equals(nomeCompleto, that.nomeCompleto)
                && Objects.equals(dataNascimento, that.dataNascimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeCompleto, dataNascimento);
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

    public EnumSexo getSexo() {
        return sexo;
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

    public void calcularIdade() {
        Integer anoNascimento = dataNascimento.getYear();
        Integer anoAtual = LocalDate.now().getYear();
        this.setIdade(anoAtual - anoNascimento);
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public CidadeDTO getCidade() {
        return cidade;
    }

    public void setCidade(CidadeDTO cidade) {
        this.cidade = cidade;
    }
}
