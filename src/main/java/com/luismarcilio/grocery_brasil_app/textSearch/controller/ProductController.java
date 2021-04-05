package com.luismarcilio.grocery_brasil_app.textSearch.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.luismarcilio.grocery_brasil_app.textSearch.domain.Product;
import com.luismarcilio.grocery_brasil_app.textSearch.domain.ProductNotFoundException;
import com.luismarcilio.grocery_brasil_app.textSearch.service.ProductService;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController()
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping("/product")
    public Object addProduct(@RequestBody Product product) {
        productService.save(product);
        // Create resource location
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @GetMapping("/product")
    public List<Product> getProductsByNameText(@RequestParam(name="text") String text) throws ParseException, IOException{
        return productService.findByNameText(text).collect(Collectors.toList());
    }

}
