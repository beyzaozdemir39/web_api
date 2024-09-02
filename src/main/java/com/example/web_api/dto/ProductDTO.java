package com.example.web_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private String name;
    private String description;
    private Double price;
    private Long categoryId;
}