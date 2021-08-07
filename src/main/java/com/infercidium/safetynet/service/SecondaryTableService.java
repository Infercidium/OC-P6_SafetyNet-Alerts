package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;
import com.infercidium.safetynet.repository.AllergiesRepository;
import com.infercidium.safetynet.repository.MedicationsRepository;

import java.util.ArrayList;
import java.util.List;

public class SecondaryTableService implements SecondaryTableI {

    private final MedicationsRepository medicationsR;
    private final AllergiesRepository allergiesR;

    public SecondaryTableService(MedicationsRepository medicationsR, AllergiesRepository allergiesR) {
        this.medicationsR = medicationsR;
        this.allergiesR = allergiesR;
    }

    @Override
    public List<Allergies> checkAllergieMedicalRecords(List<String> allergies) {
        List<Allergies> allergies1 = new ArrayList<>();
        for (String allergie1 : allergies) {
            Allergies allergie = new Allergies();
            allergie.setAllergie(allergie1);
            if(allergiesR.findByAllergie(allergie.getAllergie()) != null)
            {allergie.setId(allergiesR.findByAllergie(allergie.getAllergie()).getId());
            }else{allergiesR.save(allergie);}
            allergies1.add(allergie);
        }
        return allergies1;
    }

    @Override
    public List<Medications> checkMedicationMedicalRecords(List<String> medications) {
        List<Medications> medications1 = new ArrayList<>();
        for(String medication1 : medications) {
            Medications medication = new Medications();
            medication.setMedication(medication1);
            if(medicationsR.findByMedication(medication.getMedication()) != null)
            {medication.setId(medicationsR.findByMedication(medication.getMedication()).getId());
            }else{medicationsR.save(medication);}
            medications1.add(medication);
        }
        return medications1;
    }
}
