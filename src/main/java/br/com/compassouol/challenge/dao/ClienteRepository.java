package br.com.compassouol.challenge.dao;

import br.com.compassouol.challenge.dao.entity.Cidade;
import br.com.compassouol.challenge.dao.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author marcussantos
 */
public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    @Query("SELECT c FROM Cliente c WHERE c.nomeCompleto like %:nome%")
    List<Optional<Cliente>> findByNome(@Param("nome") String nome);

    @Query("SELECT c FROM Cliente c WHERE c.sexo = :sexo")
    List<Optional<Cliente>> findBySexo(@Param("sexo") String sexo);

    @Query("SELECT c FROM Cliente c WHERE c.id = :id")
    Optional<Cliente> findById(@Param("id") Long id);

}
