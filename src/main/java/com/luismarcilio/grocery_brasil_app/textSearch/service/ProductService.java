package com.luismarcilio.grocery_brasil_app.textSearch.service;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import com.luismarcilio.grocery_brasil_app.textSearch.domain.Product;
import com.luismarcilio.grocery_brasil_app.textSearch.repository.ProductRepository;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(Product product){
        productRepository.save(product);
    }

    public Stream<Product> findByNameText(String name) throws ParseException, IOException{
        return productRepository.findByNameText(name, 10);
    }

    public Optional<Product> findById(String id){
        return productRepository.findById(id);
    }
}
