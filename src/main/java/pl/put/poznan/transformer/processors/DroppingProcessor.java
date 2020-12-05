package pl.put.poznan.transformer.processors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import pl.put.poznan.transformer.models.DTO.DropableJSONDTO;
import pl.put.poznan.transformer.models.DTO.JSONDTO;


import java.util.Optional;

public class DroppingProcessor extends Processor{
    private final Optional<Processor> processor;
    public DroppingProcessor() {
        this.processor = Optional.empty();
    }

    public DroppingProcessor(Processor processor) {
        this.processor = Optional.of(processor);
    }

    @Override
    public JSONDTO process(JSONDTO from) {
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
