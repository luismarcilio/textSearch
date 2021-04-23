package com.luismarcilio.grocery_brasil_app.textSearch.controller;

import java.net.URI;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.luismarcilio.grocery_brasil_app.textSearch.WithDebug;
import com.luismarcilio.grocery_brasil_app.textSearch.domain.Product;
import com.luismarcilio.grocery_brasil_app.textSearch.domain.ProductNotFoundException;
import com.luismarcilio.grocery_brasil_app.textSearch.domain.Products;
import com.luismarcilio.grocery_brasil_app.textSearch.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
	@WithDebug
    public Product getProductById(@PathVariable String id) {
        return productService.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping("")
	@WithDebug
    public Object addProduct(@RequestBody @Valid Product product) {
        productService.save(product);
        // Create resource location
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @GetMapping("")
    @WithDebug
    public Products getProductsByNameText(@RequestParam(name="text") String text){
        return new Products(productService.fuzzyFindByNameText(text).collect(Collectors.toList()));
    }

    @GetMapping("/all")
	@WithDebug
    public Products getAllProducts() {
        return new Products(productService.findAll().collect(Collectors.toList()));
    }

}
