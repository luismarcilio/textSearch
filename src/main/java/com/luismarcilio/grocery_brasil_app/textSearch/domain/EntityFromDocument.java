package com.luismarcilio.grocery_brasil_app.textSearch.domain;

import org.apache.lucene.document.Document;

public interface EntityFromDocument<T> {
    public T getEntity(Document document);
}
