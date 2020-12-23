package pl.put.poznan.transformer.processors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.models.DTO.DropableJSONDTO;
import pl.put.poznan.transformer.models.DTO.FilterableJSONDTO;
import pl.put.poznan.transformer.models.DTO.JSONDTO;


import java.util.Iterator;
import java.util.Optional;

/**
 * Class use to do drop process
 *
 * @author Piotr Tylczynski
 * @version 1.0
 */

public class FilterProcessor extends Processor{

    private final Logger logger = LoggerFactory.getLogger(FilterProcessor.class);

    private final Optional<Processor> processor;

    /**
     * Main DroppingProcessor constructor
     */
    public FilterProcessor() {
        this.processor = Optional.empty();
    }

    /**
     * DroppingProcessor constructor which is used
     * when we want to activate more than just one processor
     *
     * @param processor another processor to start
     */

    public FilterProcessor(Processor processor) {
        this.processor = Optional.of(processor);
    }

    /**
     * Recursive processors running and filtering selected info
     *
     * @param from prepared data
     * @return processed data
     */

    @Override
    public JSONDTO process(JSONDTO from) {
        this.logger.debug("Filtering request");
        if(this.processor.isPresent())
            from = this.processor.get().process(from);
        // if JSONDTO is not subclass of FilterableDTO then omit
            ObjectNode response = JsonNodeFactory.instance.objectNode();
            if(from.getClass().isAssignableFrom(FilterableJSONDTO.class)){
                for (Iterator<String> it = ((JsonNode) ((FilterableJSONDTO) from).getInput()).fieldNames(); it.hasNext(); ) {
                    String field = it.next();
                    if(((FilterableJSONDTO) from).getToFilter().contains(field)){
                        response.put(
                                field, ((ObjectNode) from.getInput()).get(field).asText()
                        );
                    }
                }
            }
        from.setOutput(
                response.toString()
        );
        return from;
    }
}
