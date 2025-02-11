package com.generation.crm.controller;

import com.generation.crm.model.Medico;
import com.generation.crm.repository.MedicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/medicos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    public ResponseEntity<Medico> create(@RequestBody @Valid Medico medico) {
        repository.save(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(medico);
    }

    @GetMapping
    public ResponseEntity<List<Medico>> getAll() {
        repository.findAllLogic();
        return ResponseEntity.ok(repository.findAllLogic());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> getById(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findByIdLogic(id).orElseThrow(() -> new NoSuchElementException("Médico não encontrado")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> put(@PathVariable Long id, @RequestBody @Valid Medico medico) {
        Medico referenceById = repository.getReferenceById(id);
        referenceById.update(medico);
        repository.save(referenceById);
        return ResponseEntity.ok(referenceById);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Medico> delete(@PathVariable Long id) {
        boolean exists = repository.existsById(id);

        if (!exists) throw new NoSuchElementException("Médico não encontrado");

        repository.deleteLogic(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/restaurar/{id}")
    public ResponseEntity<Void> restore(@PathVariable Long id) {
        repository.restore(id);

        return ResponseEntity.noContent().build();
    }
}
