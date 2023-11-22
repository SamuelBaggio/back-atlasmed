package br.com.fiap.atlasmed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.atlasmed.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
}
