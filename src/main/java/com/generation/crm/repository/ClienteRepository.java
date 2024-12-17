package com.generation.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.crm.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	public List<Cliente> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

	public Cliente findAllByCpf(@Param("cpf") String cpf);
	
	List<Cliente> findByConvenio(boolean convenio);
	
	boolean existsByCpf(@Param("cpf") String cpf);

}
