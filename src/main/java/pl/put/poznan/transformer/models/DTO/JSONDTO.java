package pl.put.poznan.transformer.models.DTO;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.hateoas.EntityModel;
import pl.put.poznan.transformer.processors.Processable;

@SuperBuilder
public abstract class JSONDTO implements Processable {
    @Getter @Setter
    @JsonIgnore
    private JsonNode input;

    @Getter @Setter
    private String output;

    public EntityModel<StringDTO> toEntityModel(){
        return EntityModel.of(
                new StringDTO(this.output)
        );
    }
}
