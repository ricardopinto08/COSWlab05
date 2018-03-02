package edu.eci.cosw.samples.services;

import edu.eci.cosw.jpa.sample.model.Paciente;
import edu.eci.cosw.jpa.sample.model.PacienteId;
import edu.eci.cosw.samples.persistence.PatientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.List;

@Service
public class PatientServicesImpl implements PatientServices {

    @Autowired
    private PatientsRepository repo;

    @Override
    public Paciente getPatient(int id, String tipoid) throws ServicesException {
        return repo.findOne(new PacienteId(id,tipoid));
    }

    @Override
    public List<Paciente> topPatients(int n) throws ServicesException {
        List<Paciente> lp=null;
        lp=repo.findPacienteByNConsults(n);
        return lp!=null? lp:new LinkedList<>();
    }
}
