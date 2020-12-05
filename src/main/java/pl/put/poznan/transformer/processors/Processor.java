package pl.put.poznan.transformer.processors;

import pl.put.poznan.transformer.models.DTO.JSONDTO;

public abstract class Processor {
    public abstract JSONDTO process(JSONDTO from);
}
