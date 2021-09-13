package com.Infercidium.SafetyNet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationsIT {

    @Autowired
    private MockMvc mvc;

    //Delete
    @Test
    public void successDeleteFirestationAddress() throws Exception {
        mvc.perform(delete("/firestation/address/951 LoneTree Rd")).andExpect(status().isOk());
    }
    @Test
    public void echecDeleteFirestationAddress() throws Exception {
        mvc.perform(delete("/firestation/address/Inexistant address"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void successDeleteFirestationMapping() throws Exception {
        mvc.perform(delete("/firestation/address/112 Steppes Pl?station=3")).andExpect(status().isOk());
    }
    @Test
    public void echecDeleteFirestationMapping() throws Exception {
        mvc.perform(delete("/firestation/address/Inexistant address?station=1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void successDeleteFirestationStation() throws Exception {
        mvc.perform(delete("/firestation/station/4")).andExpect(status().isOk());
    }
    @Test
    public void echecDeleteFirestationStation() throws Exception {
        mvc.perform(delete("/firestation/station/8")).andExpect(status().isNotFound());
    }

    //Get
    @Test
    public void succesGetFirestationAddress() throws Exception {
        mvc.perform(get("/firestation/?address=1509 Culver St")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address[1]", is("1509 Culver St")))
                .andExpect(jsonPath("$[0].station", is(3)));
    }

    @Test
    public void echecGetFirestationAddress() throws Exception {
        mvc.perform(get("/firestation/?address=1 Impasse rien")).andExpect(status().isNotFound());
    }

    @Test
    public void succesGetFirestationStation() throws Exception {
        mvc.perform(get("/firestation/3")).andExpect(status().isOk())
                .andExpect(jsonPath("$.address[1]", is("1509 Culver St")))
                .andExpect(jsonPath("$.station", is(3)));
    }

    @Test
    public void echecGetFirestationStation() throws Exception {
        mvc.perform(get("/firestation/8")).andExpect(status().isNotFound());
    }

    @Test
    public void successGetAllFirestations() throws Exception {

        mvc.perform(get("/firestation/")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address[0]", is("748 Townings Dr")))
                .andExpect(jsonPath("$[3].address[0]", is("489 Manchester St")))
                .andExpect(jsonPath("$[3].station", is(4)));
    }
}
