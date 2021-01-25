package controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import pl.put.poznan.transformer.app.TextTransformerApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest(classes = TextTransformerApplication.class)
@AutoConfigureMockMvc
public class DroppingController {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void drop_and_ok() throws Exception {
        this.mockMvc.perform(
                get("/drop")
                .param("json", "\"id\": \"1001\", \"type\": \"Regular\"")
                .param("toDrop", "id")
        )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "{\"data\":\"{\\\"type\\\":\\\"Regular\\\"}\"}"
                ));
    }

    @Test
    void drop_and_404() throws Exception {
        this.mockMvc.perform(
                get("/dropp")
                        .param("json", "\"id\": \"1001\", \"type\": \"Regular\"")
                        .param("toDrop", "id")
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void drop_and_missing_param() throws Exception {
        this.mockMvc.perform(
                get("/dropp")
                        .param("json", "\"id\": \"1001\", \"type\": \"Regular\"")
        )
                .andExpect(status().is4xxClientError());
    }
}
