package edu.eci.cosw.samples.persistence;

import edu.eci.cosw.jpa.sample.model.Paciente;
import edu.eci.cosw.jpa.sample.model.PacienteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PatientsRepository extends JpaRepository<Paciente, PacienteId> {



    @Query("SELECT p FROM Paciente AS p WHERE p.consultas.size>=?1")
    public List<Paciente> findPacienteByNConsults(int N);


}
