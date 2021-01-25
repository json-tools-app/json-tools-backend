package processors;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import pl.put.poznan.transformer.models.DTO.DropableJSONDTO;
import pl.put.poznan.transformer.models.DTO.FilterableJSONDTO;
import pl.put.poznan.transformer.models.DTO.SimpleJSONDTO;
import pl.put.poznan.transformer.processors.DroppingProcessor;
import pl.put.poznan.transformer.processors.FilterProcessor;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BeautyfyProcessor {
    private final pl.put.poznan.transformer.processors.BeautyfyProcessor beautyfyProcessor =
            new pl.put.poznan.transformer.processors.BeautyfyProcessor();

    @Test
    void beautyfy() throws JsonProcessingException {
        SimpleJSONDTO simpleJSONDTO = SimpleJSONDTO.builder()
                .input(
                        new ObjectMapper().readTree(
                                "{\"id\": \"1001\", \"type\": \"Regular\"}")
                )
                .build();

        String response = this.beautyfyProcessor.process(simpleJSONDTO).getOutput();
        assertEquals(response, "{\n  \"id\" : \"1001\",\n  \"type\" : \"Regular\"\n}");
    }

    @Test
    void chain_with_dropping() throws JsonProcessingException {
        SimpleJSONDTO simpleJSONDTO = SimpleJSONDTO.builder()
                .input(
                        new ObjectMapper().readTree(
                                "{\"id\": \"1001\", \"type\": \"Regular\"}")
                )
                .build();

        SimpleJSONDTO responseDTO = (SimpleJSONDTO) this.beautyfyProcessor.process(simpleJSONDTO);
        DropableJSONDTO dropableJSONDTO = DropableJSONDTO.builder()
                .input(responseDTO.getInput())
                .toDrop(Set.of("id"))
                .build();
        String response = new DroppingProcessor().process(dropableJSONDTO).getOutput();
        assertEquals(response, "{\"type\":\"Regular\"}");
    }

    @Test
    void chain_with_filtering() throws JsonProcessingException {
        SimpleJSONDTO simpleJSONDTO = SimpleJSONDTO.builder()
                .input(
                        new ObjectMapper().readTree(
                                "{\"id\": \"1001\", \"type\": \"Regular\"}")
                )
                .build();

        SimpleJSONDTO responseDTO = (SimpleJSONDTO) this.beautyfyProcessor.process(simpleJSONDTO);
        FilterableJSONDTO dropableJSONDTO = FilterableJSONDTO.builder()
                .input(responseDTO.getInput())
                .toFilter(Set.of("type"))
                .build();
        String response = new FilterProcessor().process(dropableJSONDTO).getOutput();
        assertEquals(response, "{\"type\":\"Regular\"}");
    }
}
