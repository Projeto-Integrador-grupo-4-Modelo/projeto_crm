package com.generation.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.generation.crm.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	public List<Cliente> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);

	public Cliente findAllByCpf(@Param("cpf") String cpf);
	
	List<Cliente> findByConvenio(boolean convenio);
	
	boolean existsByCpf(@Param("cpf") String cpf);

	@Query(value = "SELECT * FROM db_crm.tb_clientes WHERE id=:id AND convenio=1",nativeQuery = true)
	Optional <Cliente> verificarConvenio(Long id);

}
