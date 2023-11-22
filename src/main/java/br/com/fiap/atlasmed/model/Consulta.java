package br.com.fiap.atlasmed.model;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;

import br.com.fiap.atlasmed.controllers.ConsultaController;
import br.com.fiap.atlasmed.controllers.PacienteController;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
    name = "tb_consulta"
)
public class Consulta {
    
    @Id
    @GeneratedValue(
        generator = "seq_consulta",
        strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
        name = "seq_consulta",
        sequenceName = "seq_consulta",
        allocationSize = 1
    )
    @Column(name = "id_consulta")
    private Long id;

    @NotBlank
    @Column(name = "motivo_consulta")
    private String motivo;

    @NotBlank
    @Column(name = "diagnostico_consulta")
    private String diagnostico;

    @NotBlank
    @Column(name = "data_consulta")
    private String data;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
    @JoinColumn(
        name = "id_paciente",
        referencedColumnName = "id_paciente",
        foreignKey = @ForeignKey(name ="fk_consulta_paciente")
    )
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
    @JoinColumn(
        name = "id_medico",
        referencedColumnName = "id_medico",
        foreignKey = @ForeignKey(name ="fk_consulta_medico")
    )
    private Medico medico;

    public EntityModel<Consulta> toEntityModel(){
        return EntityModel.of(
            this,
            linkTo(methodOn(ConsultaController.class).show(id)).withSelfRel(),
            linkTo(methodOn(ConsultaController.class).destroy(id)).withRel("delete"),
            linkTo(methodOn(ConsultaController.class).index(null, Pageable.unpaged())).withRel("all"),
            linkTo(methodOn(PacienteController.class).show(this.getPaciente().getId())).withRel("paciente")
        );
    }
}
