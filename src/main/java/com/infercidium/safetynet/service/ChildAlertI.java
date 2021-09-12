package com.infercidium.safetynet.service;

import com.infercidium.safetynet.dto.PersonsAndMedicalRecordsDTO;

import java.util.List;
import java.util.Map;

public interface ChildAlertI {

    Map<String, List<PersonsAndMedicalRecordsDTO>> childAlert(String address);
}
