package pl.put.poznan.transformer.controllers;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.put.poznan.transformer.models.DTO.ComparableJSONDTO;
import pl.put.poznan.transformer.models.DTO.SimpleJSONDTO;
import pl.put.poznan.transformer.models.DTO.StringDTO;
import pl.put.poznan.transformer.processors.ComparingProcessor;

@RestController
@RequestMapping("/compare")
public class ComparingController {
    @GetMapping("")
    public EntityModel<StringDTO> compare(
            @RequestParam("json1") SimpleJSONDTO json1,
            @RequestParam("json2") SimpleJSONDTO json2
    ){
        ComparableJSONDTO comparableJSONDTO = ComparableJSONDTO.builder()
                .json1(json1)
                .json2(json2)
                .build();

        return new ComparingProcessor().process(comparableJSONDTO).toEntityModel();

    }
}
