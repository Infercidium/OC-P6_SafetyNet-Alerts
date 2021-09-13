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
public class StationNumberIT {
    @Autowired
    private MockMvc mvc;

    @Test
    public void successFirestation() throws Exception {
        mvc.perform(get("/firestation?stationNumber=1")).andExpect(status().isOk())
                .andExpect(jsonPath("Adults", is(5)))
                .andExpect(jsonPath("Child", is(1)))
                .andExpect(jsonPath("Residents[0].firstName", is("Reginold")));
    }
    @Test
    public void echecNotFoundFirestation() throws Exception {
        mvc.perform(get("/firestation?stationNumber=8")).andExpect(status().isNotFound());
    }
    @Test
    public void echecBadParamFirestation() throws Exception {
        mvc.perform(get("/firestation")).andExpect(status().isBadRequest());
    }
}
