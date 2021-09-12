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

    /*@Autowired
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

        mvc.perform(get("/medicalRecords")).andExpect(status().isOk());
    }

    //URL
    @Test
    public void successChildAlert() throws Exception {
        mvc.perform(get("/childAlert?address=947 E. Rose Dr")).andExpect(status().isOk())
                .andExpect(jsonPath("Adults[0].firstName", is("Brian")))
                .andExpect(jsonPath("Child[0].firstName", is("Kendrik")));
    }
    @Test
    public void echecNotFoundChildAlert() throws Exception {
        mvc.perform(get("/childAlert?address=Inexistant address")).andExpect(status().isNotFound());
    }
    @Test
    public void echecBadParamChildAlert() throws Exception {
        mvc.perform(get("/childAlert")).andExpect(status().isBadRequest());
    }

    @Test
    public void successPersonInfo() throws Exception {
        mvc.perform(get("/personInfo?firstName=Jacob&lastName=boyd")).andExpect(status().isOk());
    }
    @Test
    public void echecNotFoundPersonInfo() throws Exception {
        mvc.perform(get("/personInfo?firstName=Jess&lastName=Xistepas")).andExpect(status().isNotFound());
    }
    @Test
    public void echecBadParamPersonInfo() throws Exception {
        mvc.perform(get("/personInfo")).andExpect(status().isBadRequest());
    }*/
}
