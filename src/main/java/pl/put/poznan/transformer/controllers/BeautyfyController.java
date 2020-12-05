package pl.put.poznan.transformer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.put.poznan.transformer.models.DTO.SimpleJSONDTO;
import pl.put.poznan.transformer.models.DTO.StringDTO;
import pl.put.poznan.transformer.processors.BeautyfyProcessor;

@RestController
@RequestMapping("/beautyfy")
public class BeautyfyController {

    private final Logger logger = LoggerFactory.getLogger(BeautyfyController.class);

    @GetMapping("")
    public EntityModel<StringDTO> get(
            @RequestParam("json")SimpleJSONDTO simpleJSONDTO
    ){
        this.logger.info("Beautyfy request processing");
        return new BeautyfyProcessor().process(simpleJSONDTO).toEntityModel();
    }
}
