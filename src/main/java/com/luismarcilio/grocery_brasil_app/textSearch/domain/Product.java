package com.luismarcilio.grocery_brasil_app.textSearch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private String id;
    private String name;
    private String eanCode;    
    private String ncmCode;    
    private Unity unity;    
    private boolean normalized;
    private String thumbnail;
    private int normalizationStatus;    
}
