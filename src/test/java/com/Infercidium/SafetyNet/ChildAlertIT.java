package com.Infercidium.SafetyNet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChildAlertIT {
    @Autowired
    private MockMvc mvc;

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
}
