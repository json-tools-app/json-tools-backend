package pl.put.poznan.transformer.models.DTO;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import pl.put.poznan.transformer.processors.Processable;

@SuperBuilder
public abstract class JSONDTO implements Processable {
    @Getter @Setter
    @JsonIgnore
    private JsonNode input;

    @Getter @Setter
    private String output;

    private final Logger logger = LoggerFactory.getLogger(JSONDTO.class);

    public EntityModel<StringDTO> toEntityModel(){
        this.logger.debug("Converting JSONDTO into EntityModel");
        return EntityModel.of(
                new StringDTO(this.output)
        );
    }
}
