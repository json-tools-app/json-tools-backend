package pl.put.poznan.transformer.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.models.DTO.JSONDTO;

import java.util.Optional;

/**
 * Class use to beautyfy process
 * @version 1.0
 */

public class BeautyfyProcessor extends Processor{

    private final Logger logger = LoggerFactory.getLogger(BeautyfyProcessor.class);

    private final Optional<Processor> processor;

    /**
     * Main empty constructor
     */
    public BeautyfyProcessor() {
        this.processor = Optional.empty();
    }

    /**
     * Constructor used when we want to do more than one processing
     * @param processor - another processor to start
     */

    public BeautyfyProcessor(Processor processor) {
        this.processor = Optional.of(processor);
    }

    /**
     * Recursive processor running and beautyfy
     * @param from prepared data
     * @return processed data
     */
    @Override
    public JSONDTO process(JSONDTO from) {
        this.logger.debug("Beautyfing request");
        if(this.processor.isPresent())
            from = this.processor.get().process(from);
        from.setOutput(
                from.getInput().toPrettyString()
        );
        return from;
    }
}
