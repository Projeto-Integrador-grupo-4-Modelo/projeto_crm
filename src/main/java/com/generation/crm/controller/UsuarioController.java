package com.generation.crm.controller;

import java.util.List;
import java.util.Optional;

import com.generation.crm.model.Consulta;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.generation.crm.model.Usuario;
import com.generation.crm.model.UsuarioLogin;
import com.generation.crm.repository.UsuarioRepository;
import com.generation.crm.service.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Operation(summary = "Encontra todos os usuarios ", tags = {"Usuario"}, description = "Está função irá buscar no banco todos os usuarios cadastrados")
    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> getAll() {

        return ResponseEntity.ok(usuarioRepository.findAll());

    }

    @GetMapping("/{id}")
    @Operation(summary = "Encontra usuario pelo seu id", tags = {"Usuario"}, description = "inserindo o id do usuario vai buscar no banco um usuario com um id que corresponde")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Autentica o usuário", tags = {"Usuario"}, description = "Verifica o acesso do usuário a aplicação e gera o token")
    @PostMapping("/logar")
    public ResponseEntity<UsuarioLogin> autenticarUsuario(@RequestBody Optional<UsuarioLogin> usuarioLogin) {

        return usuarioService.autenticarUsuario(usuarioLogin)
                .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @Operation(summary = "Cadastra o usuario ", tags = {"Usuario"}, description = "Cadastra informações do usuario")
    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> postUsuario(@RequestBody @Valid Usuario usuario) {

        return usuarioService.cadastrarUsuario(usuario)
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

    }

    @Operation(summary = "Atualizar dados do usuario ", tags = {"Usuario"}, description = "Atualiza os dados do usuario pelo id")
    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario) {

        return usuarioService.atualizarUsuario(usuario)
                .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @Operation(summary = "Deleta usuario ", tags = {"Usuario"}, description = "Deleta os dados do usuario pelo id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        usuarioRepository.deleteById(id);
    }

}