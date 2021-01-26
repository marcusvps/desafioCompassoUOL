package br.com.compassouol.challenge.dao.repository;

import br.com.compassouol.challenge.dao.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author marcussantos
 */
public interface CidadeRepository extends JpaRepository<Cidade,Long> {

    @Query("SELECT c FROM Cidade c WHERE c.nome = :nome")
    Cidade findByName(@Param("nome") String nome);

    @Query("SELECT c FROM Cidade c WHERE c.estado like %:estado%")
    List<Cidade> findByEstado(@Param("estado") String estado);
}
