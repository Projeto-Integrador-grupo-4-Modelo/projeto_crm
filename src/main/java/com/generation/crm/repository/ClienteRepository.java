package com.generation.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.generation.crm.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT * FROM tb_clientes WHERE nome LIKE %:nome% AND deleted = 0", nativeQuery = true)
    public List<Cliente> findAllByNome(@Param("nome") String nome);

    @Query(value = "SELECT * FROM tb_clientes WHERE cpf = :cpf AND deleted = 0", nativeQuery = true)
    public Cliente findAllByCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM tb_clientes WHERE convenio = :convenio AND deleted = 0", nativeQuery = true)
    List<Cliente> findByConvenio(boolean convenio);

    boolean existsByCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM tb_clientes WHERE id=:id AND convenio = 1 AND deleted = 0", nativeQuery = true)
    Optional<Cliente> verificarConvenio(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_clientes SET deleted = 1 WHERE id=:id", nativeQuery = true)
    void deleteLogic(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_clientes SET deleted = 0 WHERE id=:id", nativeQuery = true)
    void restore(Long id);

    @Query(value = "SELECT * FROM tb_clientes WHERE deleted = 0 AND id = :id", nativeQuery = true)
    Optional<Cliente> findByIdLogic(Long id);

    @Query(value = "SELECT * FROM tb_clientes WHERE deleted = 0", nativeQuery = true)
    List<Cliente> findAllLogic();

}
