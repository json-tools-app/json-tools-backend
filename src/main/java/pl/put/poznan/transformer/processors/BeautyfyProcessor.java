package pl.put.poznan.transformer.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.put.poznan.transformer.models.DTO.JSONDTO;

import java.util.Optional;

public class BeautyfyProcessor extends Processor{

    private final Logger logger = LoggerFactory.getLogger(BeautyfyProcessor.class);

    private final Optional<Processor> processor;
    public BeautyfyProcessor() {
        this.processor = Optional.empty();
    }

    public BeautyfyProcessor(Processor processor) {
        this.processor = Optional.of(processor);
    }

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
