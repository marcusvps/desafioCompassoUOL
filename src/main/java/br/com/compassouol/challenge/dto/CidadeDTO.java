package br.com.compassouol.challenge.dto;

import javax.validation.constraints.NotNull;

/**
 * Representa o objeto Cidade.
 * @author marcussantos
 */
public class CidadeDTO {

    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
