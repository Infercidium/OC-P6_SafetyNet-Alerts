package com.infercidium.safetynet.config;

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

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(DataBaseInit.class);

    /**
     * Instantiation of the Service class of DataBaseInit.
     */
    private final DataBaseInitService dbis;

    /**
     * DataBaseInit constructor.
     * @param dbisC the Service class of DataBaseInit.
     */
    public DataBaseInit(final DataBaseInitService dbisC) {
        this.dbis = dbisC;
    }

    /**
     * Method for initializing the H2 database.
     * @return arg, containing the method
     * and starting it automatically when the API starts.
     */
    @Bean
    public CommandLineRunner beginInit() {
        return args -> {
            // Ouverture et mise en String du fichier
            String data
                    = dbis.convertFileToString("src/main/resources/data.json");
            LOGGER.debug("JSON file in string");
            // Deserialize du string en MAP
            Map<String, Object> passageList = dbis.deserializeStringToMap(data);
            LOGGER.debug("Deserialization of the String "
                    + "in a Map<String, Object>");
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
            dbis.instanciateListPersons(persons);
            LOGGER.debug("Instantiating and saving "
                    + "the list of persons in H2 table");
            /*dbis.instanciateListFirestations(firestations);
            LOGGER.debug("Instantiating and saving "
                    + "the list of firestations in H2 table");*/
            dbis.instanciateListMedicalRecords(medicalRecords);
            LOGGER.debug("Instantiating and saving "
                    + "the list of medicalRecords in H2 table");
            //Fin
            LOGGER.info("Database initialized");
        };
    }
}
