package com.generation.crm.repository;

import com.generation.crm.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    @Query(value = "SELECT * FROM tb_medicos WHERE deleted = FALSE", nativeQuery = true)
    List<Medico> findAllLogic();

    @Query(value = "SELECT * FROM tb_medicos WHERE deleted = FALSE AND id = :id", nativeQuery = true)
    Optional<Medico> findByIdLogic(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_medicos SET deleted = TRUE WHERE id=:id", nativeQuery = true)
    void deleteLogic(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_medicos SET deleted = FALSE WHERE id=:id", nativeQuery = true)
    void restore(Long id);
}
