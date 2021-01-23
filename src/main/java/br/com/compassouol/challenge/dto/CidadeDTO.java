package br.com.compassouol.challenge.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Representa o objeto Cidade.
 * @author marcussantos
 */
public class CidadeDTO {

    @NotNull(message = "Nome da cidade deve ser informado.")
    private String nome;
    @NotNull(message = "Estado da cidade deve ser informado.")
    private String estado;


    public CidadeDTO() {
    }

    public CidadeDTO(String nome, String estado) {
        this.nome = nome;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "CidadeDTO{" +
                "nome='" + nome + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CidadeDTO cidadeDTO = (CidadeDTO) o;
        return Objects.equals(nome, cidadeDTO.nome) && Objects.equals(estado, cidadeDTO.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, estado);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


}
