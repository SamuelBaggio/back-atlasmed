package br.com.fiap.atlasmed.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.atlasmed.model.Consulta;
import br.com.fiap.atlasmed.model.Medico;
import br.com.fiap.atlasmed.model.Paciente;
import br.com.fiap.atlasmed.repository.ConsultaRepository;
import br.com.fiap.atlasmed.repository.MedicoRepository;
import br.com.fiap.atlasmed.repository.PacienteRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {
    
    @Autowired
    ConsultaRepository consultaRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        Medico m1 = new Medico(1l,
             "Samuel Baggio", 
             "samuelsbaggio@gmail.com", 
             encoder.encode("123456"),
             "000111222", 
             "222.222.222-22");

        Paciente p1 = new Paciente(1l, "333.333.333-33", "Letícia", "leticia@gmail.com");
        Paciente p2 = new Paciente(2l, "444.444.444-44", "Sofia", "sofia@gmail.com");
        Paciente p3 = new Paciente(3l, "555.555.555-55", "Olívia", "olivia@gmail.com");
             
        medicoRepository.save(m1);
        pacienteRepository.saveAll(List.of(p1, p2, p3));

        consultaRepository.saveAll(List.of(
            Consulta.builder().paciente(p1).medico(m1).motivo("Cólica").diagnostico("Dor pré-menstrual").data("22/11/23").build(),            
            Consulta.builder().paciente(p1).medico(m1).motivo("Enxaqueca").diagnostico("Dor crônica").data("10/11/23").build(),            
            Consulta.builder().paciente(p2).medico(m1).motivo("Tosse").diagnostico("Pneumonia").data("01/11/23").build(),
            Consulta.builder().paciente(p3).medico(m1).motivo("Dor de estômago").diagnostico("Emocional").data("21/11/23").build()
        ));
    }
}
