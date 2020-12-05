package pl.put.poznan.transformer.controllers;

import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import pl.put.poznan.transformer.PropertyEditors.SimpleJSONDTOPropertyEditor;
import pl.put.poznan.transformer.models.DTO.SimpleJSONDTO;

@ControllerAdvice
public class ControllerAdvize {

    @Autowired
    private SimpleJSONDTOPropertyEditor simpleJSONDTOPropertyEditor;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.registerCustomEditor(
                SimpleJSONDTO.class, this.simpleJSONDTOPropertyEditor
        );
    }
}
