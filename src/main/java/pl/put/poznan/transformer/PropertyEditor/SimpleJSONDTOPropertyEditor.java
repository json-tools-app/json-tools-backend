package pl.put.poznan.transformer.PropertyEditor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.put.poznan.transformer.models.DTO.JSONDTO;
import pl.put.poznan.transformer.models.DTO.SimpleJSONDTO;

import java.beans.PropertyEditorSupport;

@Component
public class SimpleJSONDTOPropertyEditor extends PropertyEditorSupport {

    private final Logger logger = LoggerFactory.getLogger(SimpleJSONDTOPropertyEditor.class);

    @SneakyThrows
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        this.logger.debug("Transforming text into simpleJSONDTO");
        JSONDTO simpleJSONDTO = SimpleJSONDTO.builder()
                .input(
                        new ObjectMapper().readTree("{" + text + "}")
                )
                .build();
        this.setValue(simpleJSONDTO);
    }

    @Override
    public String getAsText() {
        this.logger.debug("Transforming simpleJSONDTO into text");
        return ((SimpleJSONDTO) this.getValue()).getOutput();
    }
}
