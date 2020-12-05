package pl.put.poznan.transformer.processors;

import pl.put.poznan.transformer.models.DTO.JSONDTO;

import java.util.Optional;

public class MinifyingProcessor extends Processor{
    private final Optional<Processor> processor;
    public MinifyingProcessor() {
        this.processor = Optional.empty();
    }

    public MinifyingProcessor(Processor processor) {
        this.processor = Optional.of(processor);
    }

    @Override
    public JSONDTO process(JSONDTO from) {
        if(this.processor.isPresent())
            from = this.processor.get().process(from);
        from.setOutput(
                from.getInput().toString()
        );
        return from;
    }
}
