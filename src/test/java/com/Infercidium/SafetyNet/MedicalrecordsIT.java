package com.Infercidium.SafetyNet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalrecordsIT {

    @Autowired
    private MockMvc mvc;

    //Delete
    @Test
    public void successDeleteMedicalRecords() throws Exception {
        mvc.perform(delete("/medicalRecord/Eric/Cadigan")).andExpect(status().isOk());
    }
    @Test
    public void echecDeleteMedicalRecords() throws Exception {
        mvc.perform(delete("/medicalRecord/Jess/Ixtepas")).andExpect(status().isNotFound());
    }

    //Get
    @Test
    public void succesGetMedicalRecords() throws Exception {
        mvc.perform(get("/medicalRecord/John/Boyd")).andExpect(status().isOk());
    }

    @Test
    public void echecGetMedicalRecords() throws Exception {
        mvc.perform(get("/medicalRecord/Jess/Ixtepas")).andExpect(status().isNotFound());
    }

    @Test
    public void successGetAllMedicalRecords() throws Exception {

        mvc.perform(get("/medicalRecord/")).andExpect(status().isOk());
    }
}
