package pl.put.poznan.transformer.processors;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.models.DTO.DropableJSONDTO;
import pl.put.poznan.transformer.models.DTO.JSONDTO;


import java.util.Optional;

/**
 * Class use to do drop process
 *
 * @author Piotr Tylczynski
 * @version 1.0
 */

public class DroppingProcessor extends Processor{

    private final Logger logger = LoggerFactory.getLogger(FilterProcessor.class);

    private final Optional<Processor> processor;

    /**
     * Main DroppingProcessor constructor
     */
    public DroppingProcessor() {
        this.processor = Optional.empty();
    }

    /**
     * DroppingProcessor constructor which is used
     * when we want to activate more than just one processor
     *
     * @param processor another processor to start
     */

    public DroppingProcessor(Processor processor) {
        this.processor = Optional.of(processor);
    }

    /**
     * Recursive processors running and dropping selected info
     *
     * @param from prepared data
     * @return processed data
     */

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
