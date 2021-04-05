package com.luismarcilio.grocery_brasil_app.textSearch.controller;

import com.luismarcilio.grocery_brasil_app.textSearch.domain.ProductNotFoundException;
import com.luismarcilio.grocery_brasil_app.textSearch.repository.ProductRepositoryRuntimeException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String productNotFound(ProductNotFoundException e){
        return e.getMessage();
    }

    
    @ResponseBody
    @ExceptionHandler(ProductRepositoryRuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String serverError(ProductRepositoryRuntimeException e){
        return e.getMessage();
    }

}
