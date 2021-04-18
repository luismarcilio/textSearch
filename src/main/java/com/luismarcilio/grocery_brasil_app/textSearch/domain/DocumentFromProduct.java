package com.luismarcilio.grocery_brasil_app.textSearch.domain;

import com.luismarcilio.grocery_brasil_app.textSearch.WithDebug;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

public class DocumentFromProduct implements DocumentFromEntity<Product>{

    @Override
	@WithDebug
    public Document getDocument(Product product) {
        Document document = new Document();
        document.add(new StringField("id", product.getId(), Field.Store.YES));
        document.add(new TextField("name", product.getName(), Field.Store.YES));
        document.add(new StoredField("eanCode", product.getEanCode()));
        if(product.getNcmCode() != null) document.add(new StoredField("ncmCode", product.getNcmCode()));
        document.add(new StoredField("normalized", product.isNormalized()?"true":"false"));
        if(product.getThumbnail() != null) document.add(new StoredField("thumbnail", product.getThumbnail()));
        document.add(new StoredField("normalizationStatus", product.getNormalizationStatus()));
        if(product.getUnity() != null && product.getUnity().getName() != null) document.add(new StoredField("unity.name", product.getUnity().getName()));
        return document;
    }

    

}
