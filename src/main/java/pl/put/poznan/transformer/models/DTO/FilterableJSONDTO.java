package pl.put.poznan.transformer.models.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class FilterableJSONDTO extends JSONDTO{
    @Getter
    private final Set<String> toFilter;
}
