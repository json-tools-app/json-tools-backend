package processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import pl.put.poznan.transformer.models.DTO.ComparableJSONDTO;
import pl.put.poznan.transformer.models.DTO.DropableJSONDTO;
import pl.put.poznan.transformer.models.DTO.SimpleJSONDTO;
import pl.put.poznan.transformer.processors.DroppingProcessor;
import pl.put.poznan.transformer.processors.FilterProcessor;
import pl.put.poznan.transformer.processors.MinifyingProcessor;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComparingProcessor {
    private final pl.put.poznan.transformer.processors.ComparingProcessor comparingProcessor
            = new pl.put.poznan.transformer.processors.ComparingProcessor();
    private final MinifyingProcessor minifyingProcessor
            = new MinifyingProcessor();

    @Test
    void compare() throws JsonProcessingException {
        ComparableJSONDTO comparableJSONDTO = ComparableJSONDTO.builder()
                .json1(
                        SimpleJSONDTO.builder()
                            .input(
                                    new ObjectMapper().readTree(
                                            "{\"id\": \"1001\", \"type\": \"Regular\", \"a\":\"c\"}"
                                    )
                            ).build()
                )
                .json2(
                        SimpleJSONDTO.builder()
                            .input(
                                    new ObjectMapper().readTree(
                                            "{\"id\": \"1011\", \"type\": \"Regular\"}"
                                    )
                            )
                        .build()
                ).build();

        String response = this.comparingProcessor.process(comparableJSONDTO).getOutput();
        assertEquals(response, "Differences: (a) (id)");
    }

    @Test
    void chain_with_dropping() throws JsonProcessingException {
        DropableJSONDTO simpleJSONDTO1 = DropableJSONDTO.builder()
                .input(
                        new ObjectMapper().readTree(
                                "{\"id\": \"1001\", \"type\": \"Regular\"}")
                )
                .toDrop(Set.of("id"))
                .build();

        DropableJSONDTO rdto1 = (DropableJSONDTO) new DroppingProcessor().process(simpleJSONDTO1);
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
        String result = this.comparingProcessor.process(comparableJSONDTO).getOutput();
        assertEquals(result, "Differences: (id)");
    }

    @Test
    void chain_with_minifying() throws JsonProcessingException {
        SimpleJSONDTO simpleJSONDTO = SimpleJSONDTO.builder()
                .input(
                        new ObjectMapper().readTree("{" + "\n" +
                                "\t\t\t\t\t\t\"id\": \"0001\",\n" +
                                "\t\t\t\t\t\t\"type\": \"donut\",\n" +
                                "\t\t\t\t\t\t\"name\": \"Cake\",\n" +
                                "\t\t\t\t\t\t\"ppu\": 0.55" + "}")
                )
                .build();

        SimpleJSONDTO responseDTO1 = (SimpleJSONDTO) this.minifyingProcessor.process(simpleJSONDTO);
        SimpleJSONDTO simpleJSONDTO2 = SimpleJSONDTO.builder()
                .input(
                        new ObjectMapper().readTree("{" + "\n" +
                                "\t\t\t\t\t\t\"type\": \"donut\",\n" +
                                "\t\t\t\t\t\t\"name\": \"Cake\",\n" +
                                "\t\t\t\t\t\t\"ppu\": 0.55" + "}")
                )
                .build();

        SimpleJSONDTO responseDTO2 = (SimpleJSONDTO) this.minifyingProcessor.process(simpleJSONDTO2);

        ComparableJSONDTO comparableJSONDTO = ComparableJSONDTO.builder()
                .json1(responseDTO1)
                .json2(responseDTO2)
                .build();
        String result = this.comparingProcessor.process(comparableJSONDTO).getOutput();
        assertEquals(result, "Differences: (id)");
    }
}
