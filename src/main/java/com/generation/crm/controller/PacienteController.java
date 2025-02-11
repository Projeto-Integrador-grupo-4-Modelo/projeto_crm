package com.generation.crm.controller;

import java.util.List;
import java.util.Optional;

import com.generation.crm.repository.ConsultaRepository;
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

import com.generation.crm.model.Paciente;
import com.generation.crm.repository.PacienteRepository;
import com.generation.crm.service.PacienteService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Operation(summary = "Buscar todos Pacientes", tags = {"Pacientes"}, description = "Buscar todos os Pacientes cadastrados")
    @GetMapping
    public ResponseEntity<List<Paciente>> getAll() {
        return ResponseEntity.ok(pacienteRepository.findAllLogic());
    }

    @Operation(summary = "Buscar paciente pelo id", tags = {"Pacientes"}, description = "Buscar um Paciente cadastrado pelo seu id")
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> getById(@PathVariable Long id) {
        return pacienteRepository.findByIdLogic(id).map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Busca um Paciente pelo nome", tags = {"Pacientes"}, description = "Busca um Paciente cadastrado pelo seu nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Paciente>> getByNome(@PathVariable String nome) {
        return ResponseEntity.ok(pacienteRepository.findAllByNome(nome));
    }

    @Operation(summary = "Busca um Paciente pelo seu cpf", tags = {"Pacientes"}, description = "Busca um Paciente cadastrado pelo seu cpf")
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Paciente> getByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(pacienteRepository.findAllByCpf(cpf));
    }

    @Operation(summary = "Verifica convenio do paciente", tags = {"Pacientes"}, description = "Verifica status do convenio do paciente true ou false")
    @GetMapping("/{id}/verificarConvenio")
    public ResponseEntity<Paciente> verificarConvenio(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.verificarConvenio(id));
    }

    @Operation(summary = "Cadastra Paciente", tags = {"Pacientes"}, description = "Cadastra um paciente com seus dados")
    @PostMapping
    public ResponseEntity<Paciente> post(@Valid @RequestBody Paciente cliente) {
        if (!pacienteRepository.existsByCpf(cliente.getCpf())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteRepository.save(cliente));
        }
        throw new ConstraintViolationException("Este CPF já existe!", null);
    }

    @Operation(summary = "Atualiza Paciente", tags = {"Pacientes"}, description = "Atualiza um paciente e seus dados")
    @PutMapping
    public ResponseEntity<Paciente> put(@Valid @RequestBody Paciente cliente) {
        return pacienteRepository.findByIdLogic(cliente.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(pacienteRepository.save(cliente)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Deleta Paciente", tags = {"Pacientes"}, description = "Deleta um paciente cadastrado logicamente alterando apenas seus status de true para false, sem apagar do banco de dados")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);

        if (paciente.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        consultaRepository.deleteLogicFromUser(id);
        pacienteRepository.deleteLogic(id);
    }

    @Operation(summary = "Restaura Paciente", tags = {"Pacientes"}, description = "Restaura um paciente cadastrado logicamente alterando apenas seus status de false para true")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/restaurar/{id}")
    public void restore(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);

        if (paciente.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);


        consultaRepository.restoreFromUser(id);
        pacienteRepository.restore(id);
    }

}
