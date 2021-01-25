package controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import pl.put.poznan.transformer.app.TextTransformerApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest(classes = TextTransformerApplication.class)
@AutoConfigureMockMvc
public class BeautyfyController {
    @Test
    void beautyfy_and_ok(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(
                get("/beautyfy")
                .param("json", "\"id\": \"1001\", \"type\": \"Regular\"")
        )
                .andExpect(status().isOk())
                .andExpect(mvcResult ->
                        mvcResult.getResponse().getContentAsString().equals(
                            "{\n" +
                                    "    \"data\": \"{\\n  \\\"id\\\" : \\\"1001\\\",\\n  \\\"type\\\" : \\\"Regular\\\"\\n}\"\n" +
                                    "}"
                        )
                );
    }

    @Test
    void beautyfy_and_404(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(
                get("/beautyfyyy")
                        .param("json", "\"id\": \"1001\", \"type\": \"Regular\"")
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void beautyfy_and_missing_param(@Autowired MockMvc mockMvc) throws Exception {
        mockMvc.perform(
                get("/beautyfyyy")
        )
                .andExpect(status().is4xxClientError());
    }

}
