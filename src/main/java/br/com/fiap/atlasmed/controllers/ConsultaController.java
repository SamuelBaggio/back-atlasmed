package br.com.fiap.atlasmed.controllers;

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
import br.com.fiap.atlasmed.repository.ConsultaRepository;
import br.com.fiap.atlasmed.repository.MedicoRepository;
import br.com.fiap.atlasmed.repository.PacienteRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import oracle.net.aso.c;

@RestController
@RequestMapping("/api/consulta")
@Slf4j
@SecurityRequirement(name = "bearer-key")
@Tag(name="consulta")
public class ConsultaController {
    
    @Autowired
    ConsultaRepository consultaRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    PagedResourcesAssembler<Object> assembler;

    @GetMapping
    public PagedModel<EntityModel<Object>> index(@RequestParam(required = false) String busca,
        @ParameterObject @PageableDefault(size=3) Pageable pageable){

            Page<Consulta> consultas = (busca == null) ?
                consultaRepository.findAll(pageable):
                consultaRepository.findByPaciente(busca, pageable);

            return assembler.toModel(consultas.map(Consulta::toEntityModel));

        }

    @GetMapping("{id}")
    public EntityModel<Consulta> show(@PathVariable Long id){
        log.info("detalhando consulta" + id);

        var consulta = consultaRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("consulta não encontrado!"));

        return consulta.toEntityModel();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid Consulta consulta) {
        log.info("cadastrando consulta" + consulta);
        consultaRepository.save(consulta);
        // consulta.setPaciente(pacienteRepository.findById(alimento.getEmpresa().getId()).get());        consulta.setEmpresa(empresaRepository.findById(alimento.getEmpresa().getId()).get());
        // consulta.setMedico(medicoRepository.findById(alimento.getEmpresa().getId()).get());        consulta.setEmpresa(empresaRepository.findById(alimento.getEmpresa().getId()).get());
        return ResponseEntity.status(HttpStatus.CREATED).body(consulta.toEntityModel());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Consulta> destroy(@PathVariable Long id){
        log.info("apagando consulta" + id);
        var consulta = consultaRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("consulta não encontrado!"));

        consultaRepository.delete(consulta);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public EntityModel<Consulta> update(@PathVariable Long id, @RequestBody @Valid Consulta consulta){
        log.info("alterando consulta" + id);

        consultaRepository.findById(id)
            .orElseThrow(() -> new RestNotFoundException("consulta não encontrado!"));

        consulta.setId(id);
        consultaRepository.save(consulta);

        return consulta.toEntityModel();
    }

}
