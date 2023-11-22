package br.com.fiap.atlasmed.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.atlasmed.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    
    Page<Consulta> findByPaciente(String busca, Pageable pageable);

}
