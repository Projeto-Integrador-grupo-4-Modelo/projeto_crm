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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.crm.model.Cliente;
import com.generation.crm.repository.ClienteRepository;
import com.generation.crm.service.ClienteService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ConsultaRepository consultaRepository;

    @GetMapping
    public ResponseEntity<List<Cliente>> getAll() {
        return ResponseEntity.ok(clienteRepository.findAllLogic());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        return clienteRepository.findByIdLogic(id).map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Cliente>> getByNome(@PathVariable String nome) {
        return ResponseEntity.ok(clienteRepository.findAllByNome(nome));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Cliente> getByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(clienteRepository.findAllByCpf(cpf));
    }

    @GetMapping("/{id}/verificarConvenio")
    public ResponseEntity<Cliente> verificarConvenio(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.verificarConvenio(id));
    }

    @PostMapping
    public ResponseEntity<Cliente> post(@Valid @RequestBody Cliente cliente) {
        if (!clienteRepository.existsByCpf(cliente.getCpf())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(cliente));
        }
        throw new ConstraintViolationException("Este CPF já existe!", null);
    }

    @PutMapping
    public ResponseEntity<Cliente> put(@Valid @RequestBody Cliente cliente) {
        return clienteRepository.findByIdLogic(cliente.getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(cliente)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        consultaRepository.deleteLogicFromUser(id);
        clienteRepository.deleteLogic(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("restaurar/{id}")
    public void restore(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);


        consultaRepository.restoreFromUser(id);
        clienteRepository.restore(id);
    }

}
