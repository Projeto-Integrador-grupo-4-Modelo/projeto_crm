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

    @Operation(summary = "Buscar todas consultas", tags = {"Consultas"}, description = "Buscar todas as consultas")
    @GetMapping
    public ResponseEntity<List<Consulta>> getAll() {
        return ResponseEntity.ok(consultaRepository.findAllLogic());
    }

    @Operation(summary = "Buscar consultas pelo id", tags = {"Consultas"}, description = "Busca uma consulta cadastrada pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<Consulta> getById(@PathVariable Long id) {
        return consultaRepository.findByIdLogic(id).map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Buscar consulta pela especialidade", tags = {"Consultas"}, description = "Busca uma consulta cadastrada pela sua especialidade")
    @GetMapping("/especialidade/{especialidade}")
    public ResponseEntity<List<Consulta>> getByEspecialidade(@PathVariable String especialidade) {
        return ResponseEntity.ok(consultaRepository.findAllByEspecialidade(especialidade));
    }

    @Operation(summary = "Cadastra consultas", tags = {"Consultas"}, description = "Cadastra uma consulta com seus dados")
    @PostMapping
    public ResponseEntity<Consulta> post(@Valid @RequestBody Consulta consulta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaRepository.save(consulta));
    }

    @Operation(summary = "Atualiza uma consulta", tags = {"Consultas"}, description = "Atualiza uma consulta cadastrada e seus dados ")
    @PutMapping
    public ResponseEntity<Consulta> put(@Valid @RequestBody Consulta consulta) {
        return consultaRepository.findByIdLogic(consulta.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(consultaRepository.save(consulta)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Deleta uma consulta", tags = {"Consultas"}, description = "Deleta uma consulta cadastrada logicamente, sem apagar do banco alterando apenas seu status para false")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Consulta> consulta = consultaRepository.findByIdLogic(id);

        if (consulta.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        consultaRepository.deleteLogic(id);
    }

    @Operation(summary = "Restaura status da consulta", tags = {"Consultas"}, description = "Restaura o status de uma consulta deletada anteriormente para true")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("restaurar/{id}")
    public void restore(@PathVariable Long id) {
        Optional<Consulta> consulta = consultaRepository.findById(id);

        if (consulta.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);


        consultaRepository.restore(id);
    }

}