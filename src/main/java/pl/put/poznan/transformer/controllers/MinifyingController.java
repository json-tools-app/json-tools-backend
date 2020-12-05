package pl.put.poznan.transformer.controllers;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.put.poznan.transformer.models.DTO.SimpleJSONDTO;
import pl.put.poznan.transformer.models.DTO.StringDTO;
import pl.put.poznan.transformer.processors.MinifyingProcessor;

@RestController
@RequestMapping("/minify")
public class MinifyingController {
    @GetMapping("")
    public EntityModel<StringDTO> get(
            @RequestParam("json")SimpleJSONDTO simpleJSONDTO
    ){
        return new MinifyingProcessor()
                .process(simpleJSONDTO)
                .toEntityModel();
    }
}
