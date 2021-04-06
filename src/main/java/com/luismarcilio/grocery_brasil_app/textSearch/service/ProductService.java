package com.luismarcilio.grocery_brasil_app.textSearch.service;

import java.util.Optional;
import java.util.stream.Stream;

import com.luismarcilio.grocery_brasil_app.textSearch.WithLog;
import com.luismarcilio.grocery_brasil_app.textSearch.domain.Product;
import com.luismarcilio.grocery_brasil_app.textSearch.repository.ProductRepository;

import org.springframework.stereotype.Service;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

	@WithLog
    public void save(Product product){
        productRepository.save(product);
    }

	@WithLog
    public Stream<Product> findByNameText(String name){
        return productRepository.findByNameText(name, 10);
    }

	@WithLog
    public Optional<Product> findById(String id){
        return productRepository.findById(id);
    }
	@WithLog
    public Stream<Product> findAll() {
        return productRepository.findAll();
    }
}
