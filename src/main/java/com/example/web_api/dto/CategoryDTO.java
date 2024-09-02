package com.example.web_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    private String name;
    private String description;
}