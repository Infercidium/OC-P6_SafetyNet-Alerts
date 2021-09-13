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
public class PersonsIT {

    @Autowired
    private MockMvc mvc;

    //Delete
    @Test
    public void successDeletePersons() throws Exception {
        mvc.perform(delete("/medicalRecord/Clive/Ferguson")).andExpect(status().isOk());
        mvc.perform(delete("/person/Clive/Ferguson")).andExpect(status().isOk());
    }
    @Test
    public void echecMedicDeletePersons() throws Exception {
        mvc.perform(delete("/person/Kendrik/Stelzer")).andExpect(status().isConflict());
    }
    @Test
    public void echecDeletePersons() throws Exception {
        mvc.perform(delete("/person/Jess/Ixtepas")).andExpect(status().isNotFound());
    }

    //Get
    @Test
    public void succesGetPersons() throws Exception {
        mvc.perform(get("/person/John/Boyd")).andExpect(status().isOk());
    }

    @Test
    public void echecGetPersons() throws Exception {
        mvc.perform(get("/person/Jess/Ixtepas")).andExpect(status().isNotFound());
    }

    @Test
    public void successGetAllPersons() throws Exception {

        mvc.perform(get("/person/")).andExpect(status().isOk());
    }
}
