package pl.put.poznan.transformer.models.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class DropableJSONDTO extends JSONDTO{
    @Getter
    private final Set<String> toDrop;
}
