package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.Medications;
import com.infercidium.safetynet.repository.MedicationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MedicationsService implements MedicationsI {

    /**
     * Instantiation of LOGGER in order to inform in console.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(MedicationsService.class);

    /**
     * Instantiation of MedicationsRepository.
     */
    private final MedicationsRepository medicationsR;

    /**
     * Class Constructor.
     * @param medicationsRe this is MedicationsRepository.
     */
    public MedicationsService(final MedicationsRepository medicationsRe) {
        this.medicationsR = medicationsRe;
    }

    /**
     * Check the medication of medicationsSet,
     * if it exists add the id, if not create it.
     * @param medicationsSet to be checked.
     * @return  medicationsSet complete.
     */
    @Override
    public Set<Medications> checkMedications(
            final Set<Medications> medicationsSet) {
        Set<Medications> medications = new HashSet<>();
        for (Medications medication : medicationsSet) {
            if (medicationsR.findByMedicationIgnoreCase(
                    medication.getMedication()) == null) {
                medicationsR.save(medication);
                LOGGER.debug("Save " + medication
                        + " in the Medications table");
            } else {
                medication.setId(medicationsR.findByMedicationIgnoreCase(
                        medication.getMedication()).getId());
            }
            medications.add(medication);
        }
        LOGGER.debug("Medication check completed");
        return medications;
    }
}
