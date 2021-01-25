package processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.put.poznan.transformer.app.TextTransformerApplication;
import pl.put.poznan.transformer.models.DTO.DropableJSONDTO;
import pl.put.poznan.transformer.models.DTO.FilterableJSONDTO;
import pl.put.poznan.transformer.models.DTO.JSONDTO;
import pl.put.poznan.transformer.models.DTO.SimpleJSONDTO;
import pl.put.poznan.transformer.processors.DroppingProcessor;
import pl.put.poznan.transformer.processors.FilterProcessor;
import pl.put.poznan.transformer.processors.MinifyingProcessor;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TextTransformerApplication.class)
public class MinifyProcessor {

    private final MinifyingProcessor minifyingProcessor
            = new MinifyingProcessor();

    @Test
    void minify() throws JsonProcessingException {
        SimpleJSONDTO simpleJSONDTO = SimpleJSONDTO.builder()
                .input(
                        new ObjectMapper()
                                .readTree("{" + "\n" +
                                        "\t\t\t\t\t\t\"id\": \"0001\",\n" +
                                        "\t\t\t\t\t\t\"type\": \"donut\",\n" +
                                        "\t\t\t\t\t\t\"name\": \"Cake\",\n" +
                                        "\t\t\t\t\t\t\"ppu\": 0.55" + "}")
                ).build();
        SimpleJSONDTO response = (SimpleJSONDTO) this.minifyingProcessor.process(simpleJSONDTO);
        assertEquals(response.getOutput(), "{\"id\":\"0001\",\"type\":\"donut\",\"name\":\"Cake\",\"ppu\":0.55}");
    }

    @Test
    void chain_with_dropping() throws JsonProcessingException {
        SimpleJSONDTO simpleJSONDTO = SimpleJSONDTO.builder()
                .input(
                        new ObjectMapper().readTree("{" + "\n" +
                        "\t\t\t\t\t\t\"id\": \"0001\",\n" +
                        "\t\t\t\t\t\t\"type\": \"donut\",\n" +
                        "\t\t\t\t\t\t\"name\": \"Cake\",\n" +
                        "\t\t\t\t\t\t\"ppu\": 0.55" + "}")
                )
                .build();

        SimpleJSONDTO responseDTO = (SimpleJSONDTO) this.minifyingProcessor.process(simpleJSONDTO);
        DropableJSONDTO dropableJSONDTO = DropableJSONDTO.builder()
                .input(responseDTO.getInput())
                .toDrop(Set.of("id", "type", "name"))
                .build();
        String response = new DroppingProcessor().process(dropableJSONDTO).getOutput();
        assertEquals(response, "{\"ppu\":0.55}");
    }

    @Test
    void chain_with_filtering() throws JsonProcessingException {
        SimpleJSONDTO simpleJSONDTO = SimpleJSONDTO.builder()
                .input(
                        new ObjectMapper().readTree("{" + "\n" +
                                "\t\t\t\t\t\t\"id\": \"0001\",\n" +
                                "\t\t\t\t\t\t\"type\": \"donut\",\n" +
                                "\t\t\t\t\t\t\"name\": \"Cake\",\n" +
                                "\t\t\t\t\t\t\"ppu\": 0.55" + "}")
                )
                .build();

        SimpleJSONDTO responseDTO = (SimpleJSONDTO) this.minifyingProcessor.process(simpleJSONDTO);
        FilterableJSONDTO dropableJSONDTO = FilterableJSONDTO.builder()
                .input(responseDTO.getInput())
                .toFilter(Set.of("id"))
                .build();
        String response = new FilterProcessor().process(dropableJSONDTO).getOutput();
        assertEquals(response, "{\"id\":\"0001\"}");
    }
}
