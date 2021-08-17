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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class DataBaseInit {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(DataBaseInit.class);

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
            LOGGER.debug("JSON file in string");
            // Deserialize du string en MAP
            Map<String, Object> passageList = dbis.deserializeStringToMap(data);
            LOGGER.debug(
                    "Deserialization of the String in a Map<String, Object>");
            // Mise en liste instanciée des Objets
            List<Map<String, String>> persons
                    = dbis.convertMaptoList(passageList, "persons");
            LOGGER.debug("List of Map<String, String> containing Persons");
            List<Map<String, String>> firestations
                    = dbis.convertMaptoList(passageList, "firestations");
            LOGGER.debug("List of Map<String, String> containing Firestations");
            List<Map<String, Object>> medicalRecords
                    = dbis.convertMedicalRecordsMaptoList(passageList);
            LOGGER.debug(
                    "List of Map<String, String> containing MedicalRecords");

            //Instantiation des Lists
            List<Persons> personsList = dbis.instantiateListPersons(persons);
            LOGGER.debug(
                    "Instantiation of List from Map Persons to List<Persons>");
            List<Firestations> firestationsList
                    = dbis.instantiateListFirestations(firestations);
            LOGGER.debug("Instantiation of List from Map Firestations"
                    + " to List<Firestations>");
            List<MedicalRecords> medicalRecordsList
                    = dbis.instantiateListMedicalRecords(medicalRecords);
            LOGGER.debug("Instantiation of List from Map MedicalRecords"
                    + " to List<MedicalRecords>");
            // Envoie dans H2
            dbis.saveAllList(personsList, firestationsList, medicalRecordsList);
            LOGGER.debug("Saving List Persons, Firestations "
                    + "and MedicalRecords in H2");
            //Fin
            LOGGER.info("Database initialized");
        };
    }
}
