package br.com.fiap.atlasmed.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.atlasmed.model.Credencial;
import br.com.fiap.atlasmed.model.Medico;
import br.com.fiap.atlasmed.repository.MedicoRepository;
import br.com.fiap.atlasmed.service.TokenJwtService;
import jakarta.validation.Valid;

@RestController
public class MedicoController {
    @Autowired
    MedicoRepository repository;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenJwtService tokenJwtService;

    @PostMapping("/api/registrar")
    public ResponseEntity<Medico> registrar(@RequestBody @Valid Medico medico){
        medico.setSenha(encoder.encode(medico.getSenha()));
        repository.save(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(medico);
    }

    @PostMapping("/api/login")
    public ResponseEntity<Object> login(@RequestBody @Valid Credencial credencial){
        manager.authenticate(credencial.toAuthentication());
        var token = tokenJwtService.generateToken(credencial);
        return ResponseEntity.ok(token);
    }
}
