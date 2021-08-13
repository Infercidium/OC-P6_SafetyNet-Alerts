package com.infercidium.safetynet.service;

import com.infercidium.safetynet.model.MedicalRecords;
import com.infercidium.safetynet.model.Name;
import com.infercidium.safetynet.repository.AllergiesRepository;
import com.infercidium.safetynet.repository.MedicalrecordsRepository;
import com.infercidium.safetynet.repository.MedicationsRepository;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public class MedicalRecordsService implements MedicalRecordsI{

    private final MedicalrecordsRepository medicalrecordsR;
    private final SecondaryTableService sts;

    public MedicalRecordsService(MedicalrecordsRepository medicalrecordsR, MedicationsRepository medicationsR, AllergiesRepository allergiesR) {
        this.medicalrecordsR = medicalrecordsR;
        this.sts = new SecondaryTableService(medicationsR, allergiesR);
    }

    @Override
    public ResponseEntity<Void> createMedicalRecords(MedicalRecords medicalRecords) {
        MedicalRecords verify = medicalRecords;
        if(!medicalrecordsR.existsById(medicalRecords.getName())) {
            verify.setMedications(sts.checkMedicationMedicalRecords(medicalRecords.getMedications()));
            verify.setAllergies(sts.checkAllergieMedicalRecords(medicalRecords.getAllergies()));
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

    @Override
    public ResponseEntity<Void> editMedicalRecords(Name name, MedicalRecords medicalRecords) {
        MedicalRecords verify = medicalRecords;
        if(medicalrecordsR.existsById(medicalRecords.getName())) {
            verify.setMedications(sts.checkMedicationMedicalRecords(medicalRecords.getMedications()));
            verify.setAllergies(sts.checkAllergieMedicalRecords(medicalRecords.getAllergies()));
            this.medicalrecordsR.save(verify);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Void> removeMedicalRecords(Name name) {
        Optional<MedicalRecords> medicalRecord = getMedicalRecord(name);
        this.medicalrecordsR.delete(medicalRecord.get());
        return ResponseEntity.ok().build();
    }

    @Override
    public Optional<MedicalRecords> getMedicalRecord(Name name) {
        Optional<MedicalRecords> medicalRecord = this.medicalrecordsR.findById(name);
        return medicalRecord;
    }

    @Override
    public List<MedicalRecords> getMedicalRecords() {
        List<MedicalRecords> medicalRecords = this.medicalrecordsR.findAll();
        return medicalRecords;
    }

    @Override
    public String addressChildrenInfo(String address) {
        return null;
    }
}
