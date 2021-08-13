package com.infercidium.safetynet.config;

import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.AllergiesRepository;
import com.infercidium.safetynet.repository.FirestationsRepository;
import com.infercidium.safetynet.repository.MedicalrecordsRepository;
import com.infercidium.safetynet.repository.MedicationsRepository;
import com.infercidium.safetynet.repository.PersonsRepository;
import com.infercidium.safetynet.service.DataBaseInitService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class DataBaseInit {

    private final PersonsRepository personR;
    private final FirestationsRepository firestationsR;
    private final MedicalrecordsRepository medicalrecordsR;
    private final MedicationsRepository medicationsR;
    private final AllergiesRepository allergiesR;

    public DataBaseInit(final PersonsRepository personRe,
                        final FirestationsRepository firestationsRe,
                        final MedicalrecordsRepository medicalrecordsRe,
                        final MedicationsRepository medicationsRe,
                        final AllergiesRepository allergiesRe) {
        this.personR = personRe;
        this.firestationsR = firestationsRe;
        this.medicalrecordsR = medicalrecordsRe;
        this.medicationsR = medicationsRe;
        this.allergiesR = allergiesRe;
    }

    @Bean
    public CommandLineRunner beginInit() {
        return args -> {

            //Initialisation
            DataBaseInitService dbis
                    = new DataBaseInitService(personR, firestationsR,
                    medicalrecordsR, medicationsR, allergiesR);

            // Ouverture et mise en String du fichier
            String data
                    = dbis.convertFileToString("src/main/resources/data.json");

            // Deserialize du string en MAP
            Map<String, Object> passageList = dbis.deserializeStringToMap(data);

            // Mise en liste instanciée des Objets
            List<Map<String, String>> persons
                    = dbis.convertMaptoList(passageList, "persons");
            List<Map<String, String>> firestations
                    = dbis.convertMaptoList(passageList, "firestations");
            List<Map<String, Object>> medicalRecords
                    = dbis.convertMedicalRecordsMaptoList(passageList);

            //Instantiation des Lists
            List<Persons> personsList = dbis.instantiateListPersons(persons);
            List<Firestations> firestationsList
                    = dbis.instantiateListFirestations(firestations);
            List<MedicalRecords> medicalRecordsList
                    = dbis.instantiateListMedicalRecords(medicalRecords);

            // Envoie dans H2
            dbis.saveAllList(personsList, firestationsList, medicalRecordsList);

            //Fin
            System.out.println("Initialisation");
        };
    }
}
