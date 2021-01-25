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
public class ComparingController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void compare_and_ok() throws Exception {
        this.mockMvc.perform(
                get("/compare")
                .param("json1", "\"id\": \"1001\", \"type\": \"Regular\", \"a\":\"c\"")
                .param("json2", "\"id\": \"1011\", \"type\": \"Regular\"")
        )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "{\"data\":\"Differences: (a) (id)\"}"
                ));
    }

    @Test
    void compare_and_404() throws Exception {
        this.mockMvc.perform(
                get("/compareee")
                        .param("json1", "\"id\": \"1001\", \"type\": \"Regular\", \"a\":\"c\"")
                        .param("json2", "\"id\": \"1011\", \"type\": \"Regular\"")
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void compare_and_missing_param() throws Exception {
        this.mockMvc.perform(
                get("/compareee")
                        .param("json1", "\"id\": \"1001\", \"type\": \"Regular\", \"a\":\"c\"")
        )
                .andExpect(status().is4xxClientError());
    }
}
