package com.luismarcilio.grocery_brasil_app.textSearch.domain;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

public class DocumentFromProduct implements DocumentFromEntity<Product>{

    
    @Override
    public Document getDocument(Product product) {
        Document document = new Document();
        document.add(new Field("id", product.getId(), StringField.TYPE_STORED));
        document.add(new Field("name", product.getName(), TextField.TYPE_STORED));
        document.add(new Field("eanCode", product.getEanCode(), StringField.TYPE_STORED));
        document.add(new Field("thumbnail", product.getThumbnail(), StringField.TYPE_STORED));
        return document;
    }

    

}
