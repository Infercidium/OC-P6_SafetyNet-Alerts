package com.Infercidium.SafetyNet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.contains;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PhoneAlertIT {
    @Autowired
    private MockMvc mvc;

    @Test
    public void successPhoneAlert() throws Exception {
        mvc.perform(get("/phoneAlert?firestation=1")).andExpect(status().isOk())
                .andExpect(jsonPath("$[*].phone", contains("841-874-8547", "841-874-7462", "841-874-7784", "841-874-6512")));
    }
    @Test
    public void echecNotFoundPhoneAlert() throws Exception {
        mvc.perform(get("/phoneAlert?firestation=8")).andExpect(status().isNotFound());
    }
    @Test
    public void echecBadParamPhoneAlert() throws Exception {
        mvc.perform(get("/phoneAlert")).andExpect(status().isBadRequest());
    }
}
