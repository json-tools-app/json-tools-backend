package pl.put.poznan.transformer.processors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.models.DTO.DropableJSONDTO;
import pl.put.poznan.transformer.models.DTO.JSONDTO;


import java.util.Optional;

public class DroppingProcessor extends Processor{

    private final Logger logger = LoggerFactory.getLogger(DroppingProcessor.class);

    private final Optional<Processor> processor;
    public DroppingProcessor() {
        this.processor = Optional.empty();
    }

    public DroppingProcessor(Processor processor) {
        this.processor = Optional.of(processor);
    }

    @Override
    public JSONDTO process(JSONDTO from) {
        this.logger.debug("Dropping request");
        if(this.processor.isPresent())
            from = this.processor.get().process(from);
        // if JSONDTO is not subclass of DropableJSONDTO then omit
        if(from.getClass().isAssignableFrom(DropableJSONDTO.class)){
            for(String field : ((DropableJSONDTO) from).getToDrop())
                ((ObjectNode) from.getInput()).remove(field);
        }
        from.setOutput(
                from.getInput().toString()
        );
        return from;
    }
}
