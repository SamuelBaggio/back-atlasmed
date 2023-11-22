package br.com.fiap.atlasmed.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "tb_paciente",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_cpf_aciente",
            columnNames = "cpf_paciente"
        )
    }
)
public class Paciente {
    @Id
    @GeneratedValue(
        generator = "seq_paciente",
        strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
        name = "seq_paciente",
        sequenceName = "seq_paciente",
        allocationSize = 1
    )
    @Column(name = "id_paciente")
    private Long id;

    @NotBlank
    @Column(name = "cpf_paciente")
    private String cpf;

    @NotBlank
    @Column(name = "nome_paciente")
    private String nome;

    @NotBlank
    @Column(name = "email_paciente")
    private String email;
}
