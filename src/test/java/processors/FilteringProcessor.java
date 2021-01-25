package processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.put.poznan.transformer.app.TextTransformerApplication;
import pl.put.poznan.transformer.models.DTO.DropableJSONDTO;
import pl.put.poznan.transformer.models.DTO.FilterableJSONDTO;
import pl.put.poznan.transformer.models.DTO.SimpleJSONDTO;
import pl.put.poznan.transformer.processors.BeautyfyProcessor;
import pl.put.poznan.transformer.processors.DroppingProcessor;
import pl.put.poznan.transformer.processors.FilterProcessor;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TextTransformerApplication.class)
public class FilteringProcessor {
    private final FilterProcessor filteringProcessor
            = new FilterProcessor();

    @Test
    void filter() throws JsonProcessingException {
        FilterableJSONDTO simpleJSONDTO = FilterableJSONDTO.builder()
                .input(new ObjectMapper().readTree(
                       "{\"id\": \"1001\", \"type\": \"Regular\"}"
                ))
                .toFilter(Set.of("type"))
                .build();

        String result = this.filteringProcessor.process(simpleJSONDTO).getOutput();
        assertEquals(result, "{\"type\":\"Regular\"}");
    }

    @Test
    void chain_with_dropping() throws JsonProcessingException {
        FilterableJSONDTO simpleJSONDTO = FilterableJSONDTO.builder()
                .input(new ObjectMapper().readTree(
                        "{\"id\": \"1001\", \"type\": \"Regular\"}"
                ))
                .toFilter(Set.of("type"))
                .build();

        FilterableJSONDTO result = (FilterableJSONDTO) this.filteringProcessor.process(simpleJSONDTO);
        DropableJSONDTO dropableJSONDTO = DropableJSONDTO.builder()
                .input(result.getInput())
                .toDrop(Set.of("id"))
                .build();

        String result1 = new DroppingProcessor().process(dropableJSONDTO).getOutput();
        assertEquals(result1, "{\"type\":\"Regular\"}");
    }

    @Test
    void chain_with_beautyfy() throws JsonProcessingException {
        FilterableJSONDTO simpleJSONDTO = FilterableJSONDTO.builder()
                .input(new ObjectMapper().readTree(
                        "{\"id\": \"1001\", \"type\": \"Regular\"}"
                ))
                .toFilter(Set.of("type"))
                .build();

        FilterableJSONDTO result = (FilterableJSONDTO) this.filteringProcessor.process(simpleJSONDTO);
        SimpleJSONDTO simpleJSONDTO1 = SimpleJSONDTO.builder()
                .input(result.getInput())
                .build();
        String response = new BeautyfyProcessor().process(simpleJSONDTO1).getOutput();
        assertEquals(response, "{\n" +
                "  \"id\" : \"1001\",\n" +
                "  \"type\" : \"Regular\"\n" +
                "}");
    }
}
