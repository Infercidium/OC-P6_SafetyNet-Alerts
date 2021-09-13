package com.Infercidium.SafetyNet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FireIT {
    @Autowired
    private MockMvc mvc;

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
}
