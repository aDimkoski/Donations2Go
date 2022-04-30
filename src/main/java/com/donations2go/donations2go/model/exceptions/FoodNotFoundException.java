package com.donations2go.donations2go.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class FoodNotFoundException extends RuntimeException{
    public FoodNotFoundException(Long id) {
        super("Food with id "+id+" was not found");
    }
}
