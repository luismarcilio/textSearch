package com.luismarcilio.grocery_brasil_app.textSearch.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T,I> {
    public void save(T entity);
    public void saveAll(List<T> entityList) ;
    public Optional<T> findById(I id) ;
}
