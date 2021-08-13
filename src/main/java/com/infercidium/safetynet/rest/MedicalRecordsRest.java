package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.model.*;
import com.infercidium.safetynet.repository.AllergiesRepository;
import com.infercidium.safetynet.repository.MedicalrecordsRepository;
import com.infercidium.safetynet.repository.MedicationsRepository;
import com.infercidium.safetynet.service.SecondaryTableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class MedicalRecordsRest {
    private final MedicalrecordsRepository medicalrecordsR;
    private final MedicationsRepository medicationsR;
    private final AllergiesRepository allergiesR;

    public MedicalRecordsRest(final MedicalrecordsRepository medicalrecordsRe, final MedicationsRepository medicationsRe, final AllergiesRepository allergiesRe) {
        this.medicalrecordsR = medicalrecordsRe;
        this.medicationsR = medicationsRe;
        this.allergiesR = allergiesRe;
    }

    @PostMapping(value = "/medicalRecord")
    public ResponseEntity<Void> createMedicalRecords(@Valid @RequestBody final MedicalRecords medicalRecords) {
        MedicalRecords verify = medicalRecords;
        SecondaryTableService sts = new SecondaryTableService(medicationsR, allergiesR);
        if(!this.medicalrecordsR.existsById(medicalRecords.getName())) {
            Set<Medications> medicationsSet = sts.checkMedicationMedicalRecords(medicalRecords.getMedications());
            verify.setMedications(medicationsSet);
            Set<Allergies> allergiesSet = sts.checkAllergieMedicalRecords(medicalRecords.getAllergies());
            verify.setAllergies(allergiesSet);
            MedicalRecords result = this.medicalrecordsR.save(verify);

            URI locate = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{firstName}/{lastName}")
                    .buildAndExpand(verify.getFirstName(), verify.getLastName())
                    .toUri();

            return ResponseEntity.created(locate).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<Void> editMedicalRecords(@PathVariable final String firstName,
                                                   @PathVariable final String lastName,
                                                   @Valid @RequestBody final MedicalRecords medicalRecords) {
        Name name = new Name(firstName, lastName);
        MedicalRecords verify = medicalRecords;
        verify.setName(name);
        SecondaryTableService sts = new SecondaryTableService(medicationsR, allergiesR);
        if(this.medicalrecordsR.existsById(medicalRecords.getName())) {
            Set<Medications> medicationsSet = sts.checkMedicationMedicalRecords(medicalRecords.getMedications());
            verify.setMedications(medicationsSet);
            Set<Allergies> allergiesSet = sts.checkAllergieMedicalRecords(medicalRecords.getAllergies());
            verify.setAllergies(allergiesSet);
            this.medicalrecordsR.save(verify);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<Void> removeMedicalRecords(@PathVariable final String firstName,
                                                     @PathVariable final String lastName) {
        Name name = new Name(firstName, lastName);
        Optional<MedicalRecords> medicalRecord = getMedicalRecord(firstName, lastName);
        this.medicalrecordsR.delete(medicalRecord.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public Optional<MedicalRecords> getMedicalRecord(@PathVariable final String firstName,
                                                     @PathVariable final String lastName) {
        Name name = new Name(firstName, lastName);
        Optional<MedicalRecords> medicalRecord = this.medicalrecordsR.findById(name);
        return medicalRecord;
    }

    @GetMapping(value = "/medicalRecords")
    public List<MedicalRecords> getMedicalRecords() {
        List<MedicalRecords> medicalRecords = this.medicalrecordsR.findAll();
        return medicalRecords;
    }

    public int getChildNumber(List<Persons> personsList) {
        int child = 0;
        for(Persons persons : personsList) {
            Optional<MedicalRecords> medicalRecords = getMedicalRecord(persons.getFirstName(), persons.getLastName());
            if(medicalRecords.get().getAge() < 18) {
                child++;
            }
        }
        return child;
    }
}
