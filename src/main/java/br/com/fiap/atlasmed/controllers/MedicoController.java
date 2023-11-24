package br.com.fiap.atlasmed.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.atlasmed.exceptions.RestNotFoundException;
import br.com.fiap.atlasmed.model.Credencial;
import br.com.fiap.atlasmed.model.Medico;
import br.com.fiap.atlasmed.model.Paciente;
import br.com.fiap.atlasmed.repository.MedicoRepository;
import br.com.fiap.atlasmed.service.TokenJwtService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
// @SecurityRequirement(name = "bearer-key")
@Tag(name="medico")
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
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Médico cadastrada com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Um erro ocorreu! Verifique se os campos são válidos"),
        @ApiResponse(responseCode = "403", description = "Erro de autenticão!"),      
        @ApiResponse(responseCode = "401", description = "Erro de autenticão!")
    })
    public ResponseEntity<Medico> registrar(@RequestBody @Valid Medico medico){
        medico.setSenha(encoder.encode(medico.getSenha()));
        repository.save(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(medico);
    }

    @PostMapping("/api/login")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Um erro ocorreu!"),
        @ApiResponse(responseCode = "403", description = "Erro de autenticão!"),      
        @ApiResponse(responseCode = "401", description = "Erro de autenticão!")
    })
    public ResponseEntity<Object> login(@RequestBody @Valid Credencial credencial){
        manager.authenticate(credencial.toAuthentication());
        var token = tokenJwtService.generateToken(credencial);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/api/medicos/{id}")
    public ResponseEntity<Medico> show(@PathVariable Long id){
        return ResponseEntity.ok(getMedico(id));
    }

    private Medico getMedico(Long id){
        return repository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("Medico não encontrada!"));
    }
}
