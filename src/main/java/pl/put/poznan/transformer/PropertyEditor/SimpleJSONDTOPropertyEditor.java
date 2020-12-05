package pl.put.poznan.transformer.PropertyEditors;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import pl.put.poznan.transformer.models.DTO.JSONDTO;
import pl.put.poznan.transformer.models.DTO.SimpleJSONDTO;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

@Component
public class SimpleJSONDTOPropertyEditor extends PropertyEditorSupport {
    @SneakyThrows
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        JSONDTO simpleJSONDTO = SimpleJSONDTO.builder()
                .input(
                        new ObjectMapper().readTree("{" + text + "}")
                )
                .build();
        this.setValue(simpleJSONDTO);
    }

    @Override
    public String getAsText() {
        return ((SimpleJSONDTO) this.getValue()).getOutput();
    }
}
