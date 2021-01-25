package processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.put.poznan.transformer.app.TextTransformerApplication;
import pl.put.poznan.transformer.models.DTO.ComparableJSONDTO;
import pl.put.poznan.transformer.models.DTO.DropableJSONDTO;
import pl.put.poznan.transformer.models.DTO.SimpleJSONDTO;
import pl.put.poznan.transformer.processors.BeautyfyProcessor;
import pl.put.poznan.transformer.processors.ComparingProcessor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

@SpringBootTest(classes = TextTransformerApplication.class)
public class DroppingProcessor {
    private final pl.put.poznan.transformer.processors.DroppingProcessor droppingProcessor
            = new pl.put.poznan.transformer.processors.DroppingProcessor();

    @Test
    void drop() throws JsonProcessingException {
        DropableJSONDTO simpleJSONDTO = DropableJSONDTO.builder()
                .input(
                        new ObjectMapper()
                                .readTree("{\"id\": \"1001\", \"type\": \"Regular\"}")
                )
                .toDrop(Set.of("id"))
                .build();

        String result = this.droppingProcessor.process(simpleJSONDTO).getOutput();
        assertEquals(result, "{\"type\":\"Regular\"}");
    }

    @Test
    void chain_with_beautyfy() throws JsonProcessingException {
        SimpleJSONDTO simpleJSONDTO = SimpleJSONDTO.builder()
                .input(
                        new ObjectMapper().readTree(
                                "{\"id\": \"1001\", \"type\": \"Regular\"}")
                )
                .build();

        SimpleJSONDTO responseDTO = (SimpleJSONDTO) new BeautyfyProcessor().process(simpleJSONDTO);
        DropableJSONDTO dropableJSONDTO = DropableJSONDTO.builder()
                .input(responseDTO.getInput())
                .toDrop(Set.of("id"))
                .build();
        String response = this.droppingProcessor.process(dropableJSONDTO).getOutput();
        assertEquals(response, "{\"type\":\"Regular\"}");
    }

    @Test
    void chain_with_comparing() throws JsonProcessingException {
        DropableJSONDTO simpleJSONDTO1 = DropableJSONDTO.builder()
                .input(
                        new ObjectMapper().readTree(
                                "{\"id\": \"1001\", \"type\": \"Regular\"}")
                )
                .toDrop(Set.of("id"))
                .build();

        DropableJSONDTO rdto1 = (DropableJSONDTO) this.droppingProcessor.process(simpleJSONDTO1);
        SimpleJSONDTO rdto11 = SimpleJSONDTO.builder()
                .input(rdto1.getInput())
                .build();
        SimpleJSONDTO rdto2 = SimpleJSONDTO.builder()
                .input(
                        new ObjectMapper().readTree(
                                "{\"id\": \"1001\", \"type\": \"Regular\"}")
                )
                .build();

        ComparableJSONDTO comparableJSONDTO = ComparableJSONDTO.builder()
                .json1(rdto11)
                .json2(rdto2)
                .build();
        String result = new ComparingProcessor().process(comparableJSONDTO).getOutput();
        assertEquals(result, "Differences: (id)");
    }
}
