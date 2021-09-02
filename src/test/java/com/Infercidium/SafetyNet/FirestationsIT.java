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
        mvc.perform(delete("/firestation/address/112 Steppes Pl")).andExpect(status().isOk());
    }
    @Test
    public void echecDeleteFirestationAddress() throws Exception {
        mvc.perform(delete("/firestation/address/Inexistant address"))
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
    public void succesGetFirestation() throws Exception {
        mvc.perform(get("/firestation/1509 Culver St")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address", is("1509 Culver St")))
                .andExpect(jsonPath("$[0].station", is(3)));
    }

    @Test
    public void echecGetFirestation() throws Exception {
        mvc.perform(get("/firestation/1 Impasse rien")).andExpect(status().isNotFound());
    }

    @Test
    public void successGetAllFirestations() throws Exception {

        mvc.perform(get("/firestations")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address", is("1509 Culver St")))
                .andExpect(jsonPath("$[4].address", is("748 Townings Dr")));
    }

    //URL
    @Test
    public void successFirestation() throws Exception {
        mvc.perform(get("/firestation?stationNumber=1")).andExpect(status().isOk())
                .andExpect(jsonPath("Adults", is(5)))
                .andExpect(jsonPath("Child", is(1)))
                .andExpect(jsonPath("Residents[0].firstName", is("Peter")));
    }
    @Test
    public void echecNotFoundFirestation() throws Exception {
        mvc.perform(get("/firestation?stationNumber=8")).andExpect(status().isNotFound());
    }
    @Test
    public void echecBadParamFirestation() throws Exception {
        mvc.perform(get("/firestation")).andExpect(status().isBadRequest());
    }

    @Test
    public void successPhoneAlert() throws Exception {
        mvc.perform(get("/phoneAlert?firestation=1")).andExpect(status().isOk())
                .andExpect(jsonPath("$[*].phone", contains("841-874-6512", "841-874-8547","841-874-7462", "841-874-7784")));
    }
    @Test
    public void echecNotFoundPhoneAlert() throws Exception {
        mvc.perform(get("/phoneAlert?firestation=8")).andExpect(status().isNotFound());
    }
    @Test
    public void echecBadParamPhoneAlert() throws Exception {
        mvc.perform(get("/phoneAlert")).andExpect(status().isBadRequest());
    }

    @Test
    public void successFire() throws  Exception {
        mvc.perform(get("/fire?address=1509 Culver St")).andExpect(status().isOk())
                .andExpect(jsonPath("Station[0]", is(3)))
                .andExpect(jsonPath("Residents[0].age", is(37)))
                .andExpect(jsonPath("Residents[*].firstName", contains("John", "Jacob", "Tenley", "Roger", "Felicia")));
    }
    @Test
    public void echecNotFoundFire() throws Exception {
        mvc.perform(get("/fire?address=inexistant address")).andExpect(status().isNotFound());
    }
    @Test
    public void echecBadParamFire() throws Exception {
        mvc.perform(get("/fire")).andExpect(status().isBadRequest());
    }

    @Test
    public void successFlood() throws  Exception {
        mvc.perform(get("/flood/stations?station=1")).andExpect(status().isOk())
                .andExpect(jsonPath("['908 73rd St'][*].firstName", contains("Reginold", "Jamie")))
                .andExpect(jsonPath("['947 E. Rose Dr'][*].age", contains(45, 41, 7)))
                .andExpect(jsonPath("['644 Gershwin Cir'][0].lastName", is("Duncan")));
    }
    @Test
    public void echecNotFoundFlood() throws Exception {
        mvc.perform(get("/flood/stations?station=8")).andExpect(status().isNotFound());
    }
    @Test
    public void echecBadParamFlood() throws Exception {
        mvc.perform(get("/flood/stations")).andExpect(status().isBadRequest());
    }
}
