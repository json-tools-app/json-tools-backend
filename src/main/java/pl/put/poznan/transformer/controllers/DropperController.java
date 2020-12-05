package pl.put.poznan.transformer.controllers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.put.poznan.transformer.models.DTO.DropableJSONDTO;
import pl.put.poznan.transformer.models.DTO.JSONDTO;
import pl.put.poznan.transformer.models.DTO.SimpleJSONDTO;
import pl.put.poznan.transformer.models.DTO.StringDTO;
import pl.put.poznan.transformer.processors.DroppingProcessor;

import java.util.Set;

@RestController
@RequestMapping("/drop")
public class DropperController {

    @GetMapping("")
    public EntityModel<StringDTO> drop(
            @RequestParam("json") SimpleJSONDTO simpleJSONDTO,
            @RequestParam("toDrop") Set<String> toDrop
    ){
        JSONDTO dropalbleJSONDTO = DropableJSONDTO.builder()
                .toDrop(toDrop)
                .input(
                        simpleJSONDTO.getInput()
                ).build();

        return new DroppingProcessor()
                .process(
                        dropalbleJSONDTO
                ).toEntityModel();
//                .add(
//                    WebMvcLinkBuilder.linkTo(
//                            WebMvcLinkBuilder.methodOn(DropperController.class).drop(simpleJSONDTO, toDrop)
//                    ).withSelfRel()
//                );
    }
}
