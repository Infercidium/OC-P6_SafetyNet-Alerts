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
public class CommunityEmailIT {
    @Autowired
    private MockMvc mvc;

    @Test
    public void successCommunityEmail() throws Exception {
        mvc.perform(get("/communityEmail?city=Culver")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email", is("jaboyd@email.com")))
                .andExpect(jsonPath("$[3].email", is("tcoop@ymail.com")))
                .andExpect(jsonPath("$[7].email", is("zarc@email.com")))
                .andExpect(jsonPath("$[11].email", is("bstel@email.com")));
    }
    @Test
    public void echecNotFoundCommunityEmail() throws Exception {
        mvc.perform(get("/communityEmail?city=Inexistant city")).andExpect(status().isNotFound());
    }
    @Test
    public void echecBadParamCommunityEmail() throws Exception {
        mvc.perform(get("/communityEmail")).andExpect(status().isBadRequest());
    }
}
