package com.infercidium.safetynet.rest;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;
import com.infercidium.safetynet.service.ChildAlertI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ChildAlertRest {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(ChildAlertRest.class);

    private final ChildAlertI childAlertI;

    public ChildAlertRest(final ChildAlertI childAlertIn) {
        this.childAlertI = childAlertIn;
    }


    @GetMapping(value = "/childAlert")
    public Map<String, List<PersonsAndMedicalRecordsDTO>> getChildAlert(
            @RequestParam final String address) {
        Map<String, List<PersonsAndMedicalRecordsDTO>> result
                = childAlertI.childAlert(address);
        LOGGER.info("List of children of address : " + address + ", found");
        return result;
    }
}
