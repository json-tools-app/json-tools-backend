package converters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.put.poznan.transformer.app.TextTransformerApplication;
import pl.put.poznan.transformer.models.DTO.SimpleJSONDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TextTransformerApplication.class)
public class SimpleJSONDTOPropertyEditor {
    @Autowired
    private pl.put.poznan.transformer.PropertyEditor.SimpleJSONDTOPropertyEditor editor;

    @Test
    void converts_from_string_order_int(){
        this.editor.setAsText("\"id\": \"1001\", \"type\": \"Regular\", \"a\":\"c\"");
        SimpleJSONDTO sjd = (SimpleJSONDTO) this.editor.getValue();
        assertEquals(sjd.getInput().get("id").asInt(), 1001);
    }

    @Test
    void converts_from_string_order_string(){
        this.editor.setAsText("\"id\": \"1001\", \"type\": \"Regular\", \"a\":\"c\"");
        SimpleJSONDTO sjd = (SimpleJSONDTO) this.editor.getValue();
        assertEquals(sjd.getInput().get("type").asText(), "Regular");
    }

    @Test
    void converts_to_string(){
        this.editor.setAsText("\"id\": \"1001\", \"type\": \"Regular\", \"a\":\"c\"");
        String text = this.editor.getAsText();
        assertEquals(text, ((SimpleJSONDTO) this.editor.getValue()).getOutput());
    }

    @Test
    void converts_to_string_output_mismatch(){
        this.editor.setAsText("\"id\": \"1001\", \"type\": \"Regular\", \"a\":\"c\"");
        String properOutput = ((SimpleJSONDTO) this.editor.getValue()).getOutput();
        this.editor.setAsText("\"id\": \"1001\", \"type\": \"Regularrrr\", \"a\":\"c\"");
        String text = this.editor.getAsText();
        assertEquals(text, properOutput);
    }
}
