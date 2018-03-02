package edu.eci.cosw.samples;


import edu.eci.cosw.jpa.sample.model.Consulta;
import edu.eci.cosw.jpa.sample.model.Paciente;
import edu.eci.cosw.jpa.sample.model.PacienteId;
import edu.eci.cosw.samples.persistence.PatientsRepository;
import edu.eci.cosw.samples.services.PatientServices;
import edu.eci.cosw.samples.services.ServicesException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static jdk.nashorn.internal.runtime.Debug.id;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataRestApiApplication.class)
@WebAppConfiguration
@ActiveProfiles("application-test.properties")
@TestPropertySource("/application-test.properties")
public class SpringDataRestApiApplicationTests {

    @Autowired
    PatientServices services;

    @Autowired
    PatientsRepository repo;


    //| Método | Clase de equivalencia        | Prueba           |
//|-----| ------------- |:-------------:|
//| getPatient(id,tipoid)| Consulta a paciente que existe      | Registrar un paciente, consultarlo a través de los servcios, y rectificar que sea el mismo |
//| getPatient(id,tipoid)| Consulta a paciente que no existe      | Consultar a través de los servicios un paciente no registrado, y esperar que se produzca el error |
//| topPatients(N)| No existen pacientes con N o más consultas      | Registrar un paciente con sólo 1 consulta. Probar usando N=2 como parámetro y esperar una lista vacía.     |
//| topPatients(N)| Registrar 3 pacientes. Uno sin consultas, otro con una, y el último con dos consultas. Probar usando N=1  y esperar una lista con los dos pacientes correspondientes.| centered      |

    @Test
    public void contextLoads() {
    }

    @Test
    public void patientLoadTest1(){
        Paciente p = new Paciente(new PacienteId(1015470987, "cc"), "Edwin Cardona", new Date(2018));
        repo.save(p);
        try {
            assertEquals("Es el mismo usuario", p.getId().getId(), services.getPatient(1015470987, "cc").getId().getId());
        } catch (ServicesException ex) {
            Logger.getLogger(SpringDataRestApiApplicationTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void patientLoadTest2(){
        try {
            services.getPatient(-23, "cc");
        } catch (ServicesException ex) {
            Logger.getLogger(SpringDataRestApiApplicationTests.class.getName()).log(Level.SEVERE, null, ex);
            assertEquals("Error, paciente no registrado",ServicesException.class,ex);
        }
    }

    @Test
    public void topPatientsLoadTest1(){
        Consulta query =new Consulta(new Date(2018), "estoy en excelente forma");
        Set<Consulta> set=new HashSet<>();
        set.add(query);
        Paciente p = new Paciente(new PacienteId(1015470987, "cc"), "Roger Federer", new Date(2018),set);
        repo.save(p);
        try {
            assertEquals("Ningun paciente tiene mas de una consulta", 0, services.topPatients(2).size());
        } catch (ServicesException ex) {
            Logger.getLogger(SpringDataRestApiApplicationTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void topPatientsLoadTest2(){
        Paciente paciente = new Paciente(new PacienteId(109076453, "cc"), "Franco Armani", new Date(2018));
        repo.save(paciente);

        Consulta query =new Consulta(new Date(2018), "Hoy es un bonito dia");
        Set<Consulta> set=new HashSet<>();set.add(query);
        Paciente paciente2 = new Paciente(new PacienteId(1015470987, "cc"), "Fredy Guarin", new Date(2018),set);
        repo.save(paciente2);

        Consulta query1 =new Consulta(new Date(2018), "Me gusta ver peliculas");
        Consulta query2 =new Consulta(new Date(2018), "Soy una persona alegre");
        Set<Consulta> set1=new HashSet<>();
        set1.add(query1);
        set1.add(query2);

        Paciente paciente3 = new Paciente(new PacienteId(1026585645, "cc"), "Dayro Moreno", new Date(2018),set1);
        repo.save(paciente3);

        try {
            assertEquals("Solo hay dos pacientes con mas de una consulta", 2, services.topPatients(1).size());
        } catch (ServicesException ex) {
            Logger.getLogger(SpringDataRestApiApplicationTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}
