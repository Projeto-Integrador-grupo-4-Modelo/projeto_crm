package com.generation.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.generation.crm.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    //Alteração de 0 e 1 para FALSE e TRUE para o postgree reconhecer um valor boolean
    @Query(value = "SELECT * FROM tb_clientes WHERE nome LIKE %:nome% AND deleted = FALSE", nativeQuery = true)
    public List<Paciente> findAllByNome(@Param("nome") String nome);

    @Query(value = "SELECT * FROM tb_clientes WHERE cpf = :cpf AND deleted = FALSE", nativeQuery = true)
    public Paciente findAllByCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM tb_clientes WHERE convenio = :convenio AND deleted = FALSE", nativeQuery = true)
    List<Paciente> findByConvenio(boolean convenio);

    boolean existsByCpf(@Param("cpf") String cpf);

    @Query(value = "SELECT * FROM tb_clientes WHERE id=:id AND convenio = TRUE AND deleted = FALSE", nativeQuery = true)
    Optional<Paciente> verificarConvenio(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_clientes SET deleted = TRUE WHERE id=:id", nativeQuery = true)
    void deleteLogic(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_clientes SET deleted = FALSE WHERE id=:id", nativeQuery = true)
    void restore(Long id);

    @Query(value = "SELECT * FROM tb_clientes WHERE deleted = FALSE AND id = :id", nativeQuery = true)
    Optional<Paciente> findByIdLogic(Long id);

    @Query(value = "SELECT * FROM tb_clientes WHERE deleted = FALSE", nativeQuery = true)
    List<Paciente> findAllLogic();

}
