package pl.put.poznan.transformer.models.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ComparableJSONDTO extends JSONDTO{
    @Getter @Setter
    private SimpleJSONDTO json1;
    @Getter @Setter
    private SimpleJSONDTO json2;
}
