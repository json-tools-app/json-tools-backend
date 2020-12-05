package pl.put.poznan.transformer.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.models.DTO.JSONDTO;

import java.util.Optional;

public class MinifyingProcessor extends Processor{

    private final Logger logger = LoggerFactory.getLogger(MinifyingProcessor.class);

    private final Optional<Processor> processor;
    public MinifyingProcessor() {
        this.processor = Optional.empty();
    }

    public MinifyingProcessor(Processor processor) {
        this.processor = Optional.of(processor);
    }

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
