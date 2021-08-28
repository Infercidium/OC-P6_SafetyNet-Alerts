package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Address;
import com.infercidium.safetynet.model.Allergies;
import com.infercidium.safetynet.model.Medications;
import com.infercidium.safetynet.repository.AddressRepository;
import com.infercidium.safetynet.repository.AllergiesRepository;
import com.infercidium.safetynet.repository.MedicationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SecondaryTableService implements SecondaryTableI {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondaryTableService.class);

    private final MedicationsRepository medicationsR;
    private final AllergiesRepository allergiesR;
    private final AddressRepository addressR;

    public SecondaryTableService(
            final MedicationsRepository medicationsRe,
            final AllergiesRepository allergiesRe,
            final AddressRepository addressRe) {
        this.medicationsR = medicationsRe;
        this.allergiesR = allergiesRe;
        this.addressR = addressRe;
    }

    @Override
    public Address checkAddress(final Address address) {
        Address addressCheck;
        if (addressR.findByAddressIgnoreCase(address.getAddress()) == null) {
           addressCheck = addressR.save(address);
            LOGGER.debug("Save " + address + " in the Address table");
        } else {
            addressCheck = addressR.findByAddressIgnoreCase(address.getAddress());
        }
        LOGGER.debug("Address checked");
        return addressCheck;
    }

    @Override
    public Set<Allergies> checkAllergies(final Set<Allergies> allergiesSet) {
        Set<Allergies> allergies = new HashSet<>();
        for (Allergies allergie : allergiesSet) {
            if (allergiesR.findByAllergyIgnoreCase(allergie.getAllergy()) == null) {
                allergiesR.save(allergie);
                LOGGER.debug("Save " + allergie + " in the Allergies table");
            } else {
                allergie.setId(allergiesR.findByAllergyIgnoreCase(allergie.getAllergy()).getId());
            }
            allergies.add(allergie);
        }
        LOGGER.debug("Allergie check completed");
        return allergies;
    }

    @Override
    public Set<Medications> checkMedications(final Set<Medications> medicationsSet) {
        Set<Medications> medications = new HashSet<>();
        for (Medications medication : medicationsSet) {
            if (medicationsR.findByMedicationIgnoreCase(medication.getMedication()) == null) {
                        medicationsR.save(medication);
                        LOGGER.debug("Save " + medication + " in the Medications table");
                    } else {
                medication.setId(medicationsR.findByMedicationIgnoreCase(medication.getMedication()).getId());
            }
            medications.add(medication);
        }
        LOGGER.debug("Medication check completed");
        return medications;
    }
}
