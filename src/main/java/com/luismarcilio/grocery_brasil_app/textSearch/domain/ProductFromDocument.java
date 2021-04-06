package com.luismarcilio.grocery_brasil_app.textSearch.domain;

import com.luismarcilio.grocery_brasil_app.textSearch.WithLog;

import org.apache.lucene.document.Document;

public class ProductFromDocument implements EntityFromDocument<Product> {

    @Override
	@WithLog
    public Product getEntity(Document document) {
        return Product.builder()
            .id(document.getField("id").stringValue())
            .name(document.getField("name").stringValue())
            .eanCode(document.getField("eanCode").stringValue()).ncmCode(document.getField("ncmCode").stringValue())
            .normalized(Boolean.parseBoolean(document.getField("normalized").stringValue()))
            .thumbnail(document.getField("thumbnail").stringValue())
            .normalizationStatus(Integer.parseInt(document.getField("normalizationStatus").stringValue()))
            .unity(Unity.builder().name(document.getField("unity.name").stringValue()).build()).build();
    }

}
