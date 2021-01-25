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
public class MinifyController {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void minify_and_ok() throws Exception {
        this.mockMvc.perform(
                get("/minify")
                        .param("json", "\n" +
                                "\t\t\t\t\t\t\"id\": \"0001\",\n" +
                                "\t\t\t\t\t\t\"type\": \"donut\",\n" +
                                "\t\t\t\t\t\t\"name\": \"Cake\",\n" +
                                "\t\t\t\t\t\t\"ppu\": 0.55")
        )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "{\"data\":\"{\\\"id\\\":\\\"0001\\\",\\\"type\\\":\\\"donut\\\",\\\"name\\\":\\\"Cake\\\",\\\"ppu\\\":0.55}\"}"
                ));
    }

    @Test
    void minify_and_404() throws Exception {
        this.mockMvc.perform(
                get("/minifyy")
                        .param("json", "\n" +
                                "\t\t\t\t\t\t\"id\": \"0001\",\n" +
                                "\t\t\t\t\t\t\"type\": \"donut\",\n" +
                                "\t\t\t\t\t\t\"name\": \"Cake\",\n" +
                                "\t\t\t\t\t\t\"ppu\": 0.55")
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void minify_and_missing_param() throws Exception {
        this.mockMvc.perform(
                get("/minifyy")
        )
                .andExpect(status().is4xxClientError());
    }
}
