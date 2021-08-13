package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;
import com.infercidium.safetynet.repository.AllergiesRepository;
import com.infercidium.safetynet.repository.MedicationsRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SecondaryTableService implements SecondaryTableI {

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
            if (allergiesR.findByAllergie(allergie.getAllergie()) != null) {
                allergie.setId(allergiesR
                        .findByAllergie(allergie.getAllergie()).getId());
            } else {
                allergiesR.save(allergie);
            }
            allergies1.add(allergie);
        }
        return allergies1;
    }
    @Override
    public Set<Allergies> checkAllergieMedicalRecords(Set<Allergies> allergiesSet) {
        Set<Allergies> allergies = new HashSet<>();
        for(Allergies allergie : allergiesSet) {
            if(allergiesR.findByAllergie(allergie.getAllergie()) != null) {
                allergie.setId(allergiesR.findByAllergie(allergie.getAllergie()).getId());
            } else {
                allergiesR.save(allergie);
            }
            allergies.add(allergie);
        }
        return allergies;
    }

    @Override
    public List<Medications> checkMedicationMedicalRecords(
            final List<String> medications) {
        List<Medications> medications1 = new ArrayList<>();
        for (String medication1 : medications) {
            Medications medication = new Medications();
            medication.setMedication(medication1);
            if (medicationsR
                    .findByMedication(medication.getMedication()) != null) {
                medication.setId(medicationsR
                        .findByMedication(medication.getMedication()).getId());
            } else {
                medicationsR.save(medication);
            }
            medications1.add(medication);
        }
        return medications1;
    }



    @Override
    public Set<Medications> checkMedicationMedicalRecords(Set<Medications> medicationsSet) {
        Set<Medications> medications = new HashSet<>();
        for(Medications medication : medicationsSet) {
            if(medicationsR.findByMedication(medication.getMedication()) != null) {
                medication.setId(medicationsR.findByMedication(medication.getMedication()).getId());
            } else {
                medicationsR.save(medication);
            }
            medications.add(medication);
        }
        return medications;
    }
}
