package br.com.fiap.atlasmed.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.atlasmed.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    
    Optional<Medico> findByEmail(String email);

}
