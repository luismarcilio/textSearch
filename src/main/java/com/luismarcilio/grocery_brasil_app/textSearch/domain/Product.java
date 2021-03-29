package com.luismarcilio.grocery_brasil_app.textSearch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private long id;
    private String name;
    private String eanCode;    
    private boolean normalized;
    private String thumbnail;
    private int normalizationStatus;    
}
