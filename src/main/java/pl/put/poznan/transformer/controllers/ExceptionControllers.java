package pl.put.poznan.transformer.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.put.poznan.transformer.models.DTO.ExceptionDTO;

@RestController
@ControllerAdvice
public class ExceptionControllers {
    @ExceptionHandler({JsonParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public EntityModel<ExceptionDTO> handle(JsonParseException ex){
        return EntityModel.of(
                new ExceptionDTO(
                        ex.getMessage()
                )
        );
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public EntityModel<ExceptionDTO> handle(IllegalArgumentException ex){
        return EntityModel.of(
                new ExceptionDTO(
                        "Malformed request"
                )
        );
    }
}
