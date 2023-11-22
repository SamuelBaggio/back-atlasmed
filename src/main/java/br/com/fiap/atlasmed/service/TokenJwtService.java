package br.com.fiap.atlasmed.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.fiap.atlasmed.model.Credencial;
import br.com.fiap.atlasmed.model.JwtToken;
import br.com.fiap.atlasmed.model.Medico;
import br.com.fiap.atlasmed.repository.MedicoRepository;

@Service
public class TokenJwtService {
    
    @Autowired
    MedicoRepository repository;

    public JwtToken generateToken(Credencial credencial){
        Algorithm alg = Algorithm.HMAC256("secret");

        var token = JWT.create()
            .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS))
            .withSubject(credencial.email())
            .withIssuer("AtlasMed")
            .sign(alg);

        return new JwtToken(token);
    }

    
    public Medico validate(String token){
        Algorithm alg = Algorithm.HMAC256("secret");
        var email = JWT.require(alg)
            .withIssuer("AtlasMed")
            .build()
            .verify(token)
            .getSubject();

        return repository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Token Inválido"));
    }

}
