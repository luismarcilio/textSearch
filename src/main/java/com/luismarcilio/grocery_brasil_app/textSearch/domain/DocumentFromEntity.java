package com.luismarcilio.grocery_brasil_app.textSearch.domain;

import org.apache.lucene.document.Document;

public interface DocumentFromEntity<T> {
    public Document getDocument(T entity);
    
}
