package br.com.fiap.atlasmed.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.atlasmed.exceptions.RestNotFoundException;
import br.com.fiap.atlasmed.model.Consulta;
import br.com.fiap.atlasmed.model.Paciente;
import br.com.fiap.atlasmed.repository.ConsultaRepository;
import br.com.fiap.atlasmed.repository.MedicoRepository;
import br.com.fiap.atlasmed.repository.PacienteRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/consulta")
@SecurityRequirement(name = "bearer-key")
@Tag(name="consulta")
public class ConsultaController {

    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    ConsultaRepository repository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    @CrossOrigin
    public List<Consulta> index(){
        return repository.findAll();
    }

    @PostMapping
    @CrossOrigin
    public ResponseEntity<Consulta> create(@RequestBody @Valid Consulta consulta){
        log.info("cadastrando consulta" + consulta);
        repository.save(consulta);
        return ResponseEntity.status(HttpStatus.CREATED).body(consulta);
    }

    @GetMapping("{id}")
    @CrossOrigin
    public ResponseEntity<Consulta> show(@PathVariable Long id){
        log.info("detalhando consulta" + id);
        return ResponseEntity.ok(getConsulta(id));
    }

    @DeleteMapping("{id}")
    @CrossOrigin
    public ResponseEntity<Consulta> destroy(@PathVariable Long id){
        log.info("apagando consulta" + id);

        repository.delete(getConsulta(id));

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @CrossOrigin
    public ResponseEntity<Consulta> update(@PathVariable Long id, @RequestBody @Valid Consulta consulta){
        log.info("alterando consulta" + id);
        getConsulta(id);
        consulta.setId(id);
        repository.save(consulta);
        return ResponseEntity.ok(consulta       );
    }

    private Consulta getConsulta(Long id){
        return repository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("Consulta não encontrada!"));
    }


}
    