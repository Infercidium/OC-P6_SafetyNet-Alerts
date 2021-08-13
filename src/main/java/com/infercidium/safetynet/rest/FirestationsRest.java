package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.model.Firestations;
import com.infercidium.safetynet.model.Persons;
import com.infercidium.safetynet.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class FirestationsRest {

    private final FirestationsRepository firestationsR;
    private final PersonsRepository personsR;
    private final MedicalrecordsRepository medicalrecordsR;
    private final MedicationsRepository medicationsR;
    private final AllergiesRepository allergiesR;

    public FirestationsRest(FirestationsRepository firestationsR, PersonsRepository personsR, MedicalrecordsRepository medicalrecordsR, MedicationsRepository medicationsR, AllergiesRepository allergiesR) {
        this.firestationsR = firestationsR;
        this.personsR = personsR;
        this.medicalrecordsR = medicalrecordsR;
        this.medicationsR = medicationsR;
        this.allergiesR = allergiesR;
    }

    @PostMapping(value = "/firestation")
    public ResponseEntity<Void> createStationMap(@Valid @RequestBody Firestations firestations) {
        if(!this.firestationsR.existsById(firestations.getAddress())) {
            Firestations verify = this.firestationsR.save(firestations);

            URI locate = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{address}")
                    .buildAndExpand(verify.getAddress())
                    .toUri();

            return ResponseEntity.created(locate).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/firestation/{address}")
    public ResponseEntity<Void> editStationMap(@PathVariable String address, @Valid @RequestBody Firestations firestations) {
        Firestations firestation = firestations;
        firestation.setAddress(address);
        if(this.firestationsR.existsById(address)) {
            this.firestationsR.save(firestation);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/firestation/{station}")
    public ResponseEntity<Void> removeStation(int station) {
        List<Firestations> firestations = getStation(station);
        this.firestationsR.deleteAll(firestations);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/firestation/{address}")
    public ResponseEntity<Void> removeAddress(@PathVariable String address) {
        Optional<Firestations> firestations = getAddress(address);
        this.firestationsR.delete(firestations.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/firestation/{address}")
    public Optional<Firestations> getAddress(@PathVariable String address) {
        Optional<Firestations> firestations = firestationsR.findById(address);
        return firestations;
    }

    @GetMapping(value = "/firestations")
    public List<Firestations> getStation(@RequestParam(required = false, defaultValue = "0") int station) {
        List<Firestations> firestations = new ArrayList<>();
        if(station == 0) {
            firestations = this.firestationsR.findAll();
        } else {
            firestations = this.firestationsR.findByStation(station);
        }
        return firestations;
    }

    @GetMapping(value = "/firestation")
    public String stationNumberInfo(@RequestParam int station) {
        String result = "Firestation : " + station + ", \n\n";
        int childT = 0, adultT = 0;
        List<Firestations> firestationsList = getStation(station);
        for (Firestations firestations : firestationsList) {
            result += "Address : " + firestations.getAddress() + ", \n";
            PersonsRest personsRest = new PersonsRest(personsR);
            List<Persons> personsList = personsRest.getPersonsAddress(firestations.getAddress());
            MedicalRecordsRest medicalRecordsRest = new MedicalRecordsRest(medicalrecordsR, medicationsR, allergiesR);
            int child = medicalRecordsRest.getChildNumber(personsList);
            int adult = personsList.size() - child;
            for(Persons persons : personsList){
                result += persons.getFirstName() + " " + persons.getLastName() + " : " + persons.getPhone() + ", \n";
            }
            result += "Number of children : " + child + ", number of adult : " + adult + ".\n\n";
            childT += child;
            adultT += adult;
        }
        result += "Total : number of children : " + childT + ", number of adult : " + adultT + ".";
        return result;
    }
}
