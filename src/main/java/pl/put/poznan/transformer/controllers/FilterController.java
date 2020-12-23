package pl.put.poznan.transformer.controllers;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.put.poznan.transformer.models.DTO.FilterableJSONDTO;
import pl.put.poznan.transformer.models.DTO.SimpleJSONDTO;
import pl.put.poznan.transformer.models.DTO.StringDTO;
import pl.put.poznan.transformer.processors.FilterProcessor;

import java.util.Set;

@RestController
@RequestMapping("/filter")
public class FilterController {
    @GetMapping("")
    public EntityModel<StringDTO> filter(
            @RequestParam("json")SimpleJSONDTO simpleJSONDTO,
            @RequestParam("toFilter") Set<String> toFilter
    ){
        FilterableJSONDTO filterableJSONDTO = FilterableJSONDTO.builder()
                .toFilter(toFilter)
                .input(
                        simpleJSONDTO.getInput()
                ).build();
        return new FilterProcessor()
                .process(
                        filterableJSONDTO
                ).toEntityModel();
    }
}
