package com.luismarcilio.grocery_brasil_app.textSearch.domain;

import org.apache.lucene.document.Document;

public class ProductFromDocument implements EntityFromDocument<Product>{

    @Override
    public Product getEntity(Document document) {
        Product product = new Product();
        product.setId(document.getField("id").stringValue());
        product.setEanCode(document.getField("eanCode").stringValue());
        product.setName(document.getField("name").stringValue());
        product.setThumbnail(document.getField("thumbnail").stringValue());
        return product;
        

    }
    
}
