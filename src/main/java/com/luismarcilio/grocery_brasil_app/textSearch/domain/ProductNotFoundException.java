package com.luismarcilio.grocery_brasil_app.textSearch.domain;

public class ProductNotFoundException extends RuntimeException{

    private static final long serialVersionUID = -4594718179573857442L;

    public ProductNotFoundException(String id){
        super("Product not found: "+id);
    }
    
}
