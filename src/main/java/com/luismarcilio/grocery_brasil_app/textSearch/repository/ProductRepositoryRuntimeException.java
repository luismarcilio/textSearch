package com.luismarcilio.grocery_brasil_app.textSearch.repository;

public class ProductRepositoryRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProductRepositoryRuntimeException(Exception other){
        super(other);
    }
    
}
