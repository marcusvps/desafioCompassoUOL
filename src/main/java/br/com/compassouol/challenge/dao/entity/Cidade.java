package br.com.compassouol.challenge.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "CIDADE")
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    @Column
    private String nome;
    @Column
    private String estado;






    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
