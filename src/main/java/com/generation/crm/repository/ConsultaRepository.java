package com.generation.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.crm.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	public List<Consulta> findAllByEspecialidadeContainingIgnoreCase(@Param("especialidade") String especialidade);

}
