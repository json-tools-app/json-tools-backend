package pl.put.poznan.transformer.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.models.DTO.JSONDTO;

import java.util.Optional;

/**
 * Class use to minify process
 * @version 1.0
 */

public class MinifyingProcessor extends Processor{

    private final Logger logger = LoggerFactory.getLogger(MinifyingProcessor.class);

    private final Optional<Processor> processor;

    /**
     * Main MinifyingProcessor constructor
     */
    public MinifyingProcessor() {
        this.processor = Optional.empty();
    }

    /**
     * Constructor used when we want to do more than one processing
     * @param processor - another processor to start
     */

    public MinifyingProcessor(Processor processor) {
        this.processor = Optional.of(processor);
    }

    /**
     * Recursive processor running and minifying
     * @param from prepared data
     * @return processed data
     */

    @Override
    public JSONDTO process(JSONDTO from) {
        this.logger.debug("Minifying request");
        if(this.processor.isPresent())
            from = this.processor.get().process(from);
        from.setOutput(
                from.getInput().toString()
        );
        return from;
    }
}
