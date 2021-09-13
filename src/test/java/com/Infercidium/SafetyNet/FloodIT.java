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
public class FloodIT {
    @Autowired
    private MockMvc mvc;

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
