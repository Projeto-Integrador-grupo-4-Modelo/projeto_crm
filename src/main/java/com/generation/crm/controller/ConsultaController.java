package com.generation.crm.controller;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.crm.model.Consulta;
import com.generation.crm.repository.ConsultaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ConsultaController {

	@Autowired
	private ConsultaRepository consultaRepository;

	@GetMapping
	public ResponseEntity<List<Consulta>> getAll() {
		return ResponseEntity.ok(consultaRepository.findAll());
	}

	@Operation(summary = "Encontra a consulta", tags = {"Consulta"}, description = "Encontra uma consulta pelo id")
	@GetMapping("/{id}")
	public ResponseEntity<Consulta> getById(@PathVariable Long id) {
		return consultaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@Operation(summary = "Encontra a consulta por especialidade", tags = {"Consulta"}, description = "Encontra uma consulta pela sua especialidade")
	@GetMapping("/especialidade/{especialidade}")
	public ResponseEntity<List<Consulta>> getByEspecialidade(@PathVariable String especialidade) {
		return ResponseEntity.ok(consultaRepository.findAllByEspecialidadeContainingIgnoreCase(especialidade));
	}

	@Operation(summary = "Cadastra uma consulta", tags = {"Consulta"}, description = "Cadastra uma consulta com seus atributos")
	@PostMapping
	public ResponseEntity<Consulta> post(@Valid @RequestBody Consulta consulta) {
		return ResponseEntity.status(HttpStatus.CREATED).body(consultaRepository.save(consulta));
	}

	@Operation(summary = "Atualiza uma consulta", tags = {"Consulta"}, description = "Atualiza uma consulta e seus dados pelo id")
	@PutMapping
	public ResponseEntity<Consulta> put(@Valid @RequestBody Consulta consulta) {
		return consultaRepository.findById(consulta.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(consultaRepository.save(consulta)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@Operation(summary = "Deleta uma consulta", tags = {"Consulta"}, description = "Deleta uma consulta pelo id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Consulta> consulta = consultaRepository.findById(id);

		if (consulta.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		consultaRepository.deleteById(id);
	}

}