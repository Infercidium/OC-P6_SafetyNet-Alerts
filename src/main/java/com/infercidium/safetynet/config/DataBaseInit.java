package com.infercidium.safetynet.config;

import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Medications;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.FirestationsRepository;
import com.infercidium.safetynet.repository.MedicalrecordsRepository;
import com.infercidium.safetynet.repository.MedicationsRepository;
import com.infercidium.safetynet.repository.PersonsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataBaseInit {


    private final PersonsRepository personR;
    private final FirestationsRepository FirestationR;
    private final MedicalrecordsRepository MedicalrecordsR;
    private final MedicationsRepository MedicationsR;
    private final static Logger LOGGER = LoggerFactory.getLogger(DataBaseInit.class);
    private final Set<Medications> set =  new HashSet<>() ;
    private final String anniv = "12/02/1985";
    private final LocalDate date;

    public DataBaseInit(PersonsRepository personR, FirestationsRepository firestationR, MedicalrecordsRepository medicalrecordsR, MedicationsRepository medicationsR) {
        this.personR = personR;
        this.FirestationR = firestationR;
        this.MedicalrecordsR = medicalrecordsR;
        this.MedicationsR = medicationsR;
        set.add(new Medications("test1"));
        set.add(new Medications("test2"));
        date = LocalDate.parse(anniv, DateTimeFormatter.ofPattern("dd/MM/uuuu"));

    }


    @Bean
    public CommandLineRunner beginInit() {
        return args -> {
            Persons test = new Persons("Jean", "Jean", "34 rue table", "Bayeux", 14400, "456-854-854", "Jeanjean@email.com");
            Firestations test2 = new Firestations("34 rue table", 4);
            this.personR.save(test);
            this.FirestationR.save(test2);
            this.MedicationsR.saveAll(set);
            this.MedicalrecordsR.save(new MedicalRecords("Jean", "Jean", date, set, null));
            this.MedicalrecordsR.save(new MedicalRecords("Marc", "Tronc", LocalDate.of(1994, 2, 21), set, null));
        };
    }
}
