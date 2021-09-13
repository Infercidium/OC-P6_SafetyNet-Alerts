package com.Infercidium.SafetyNet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonInfoIT {
    @Autowired
    private MockMvc mvc;

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
    }
}
