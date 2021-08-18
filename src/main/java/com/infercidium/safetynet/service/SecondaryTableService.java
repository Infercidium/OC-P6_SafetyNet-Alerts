package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;
import com.infercidium.safetynet.repository.AllergiesRepository;
import com.infercidium.safetynet.repository.MedicationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SecondaryTableService implements SecondaryTableI {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(SecondaryTableService.class);

    private final MedicationsRepository medicationsR;
    private final AllergiesRepository allergiesR;

    public SecondaryTableService(
            final MedicationsRepository medicationsRe,
            final AllergiesRepository allergiesRe) {
        this.medicationsR = medicationsRe;
        this.allergiesR = allergiesRe;
    }

    @Override
    public List<Allergies> checkAllergieMedicalRecords(
            final List<String> allergies) {
        List<Allergies> allergies1 = new ArrayList<>();
        for (String allergie1 : allergies) {
            Allergies allergie = new Allergies();
            allergie.setAllergie(allergie1);
            if (this.allergiesR
                    .findByAllergie(allergie.getAllergie()) != null) {
               /* allergie.setId(this.allergiesR
                        .findByAllergie(allergie.getAllergie()).getId());*/
            } else {
                this.allergiesR.save(allergie);
                LOGGER.debug("Save " + allergie + " in the Allergies table");
            }
            allergies1.add(allergie);
        }
        LOGGER.debug("Allergie check completed");
        return allergies1;
    }
    @Override
    public Set<Allergies> checkAllergieMedicalRecords(
            final Set<Allergies> allergiesSet) {
        Set<Allergies> allergies = new HashSet<>();
        for (Allergies allergie : allergiesSet) {
            if (this.allergiesR
                    .findByAllergie(allergie.getAllergie()) != null) {
               /* allergie.setId(this.allergiesR
                        .findByAllergie(allergie.getAllergie()).getId());*/
            } else {
                this.allergiesR.save(allergie);
                LOGGER.debug("Save " + allergie + " in the Allergies table");
            }
            allergies.add(allergie);
        }
        LOGGER.debug("Allergie check completed");
        return allergies;
    }

    @Override
    public List<Medications> checkMedicationMedicalRecords(
            final List<String> medications) {
        List<Medications> medications1 = new ArrayList<>();
        for (String medication1 : medications) {
            Medications medication = new Medications();
            medication.setMedication(medication1);
            if (this.medicationsR
                    .findByMedication(medication.getMedication()) != null) {
               /* medication.setId(this.medicationsR
                        .findByMedication(medication.getMedication()).getId());*/
            } else {
                this.medicationsR.save(medication);
                LOGGER.debug("Save " + medication
                        + " in the Medications table");
            }
            medications1.add(medication);
        }
        LOGGER.debug("Medication check completed");
        return medications1;
    }



    @Override
    public Set<Medications> checkMedicationMedicalRecords(
            final Set<Medications> medicationsSet) {
        Set<Medications> medications = new HashSet<>();
        for (Medications medication : medicationsSet) {
            if (this.medicationsR
                    .findByMedication(medication.getMedication()) != null) {
               /* medication.setId(this.medicationsR
                        .findByMedication(medication.getMedication()).getId());*/
            } else {
                this.medicationsR.save(medication);
                LOGGER.debug("Save " + medication
                        + " in the Medications table");
            }
            medications.add(medication);
        }
        LOGGER.debug("Medication check completed");
        return medications;
    }
}
