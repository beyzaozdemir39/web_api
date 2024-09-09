package com.example.web_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {

    @NotNull(message = "Category name cannot be null")
    private String name;

    @NotNull(message = "Category name cannot be null")
    private String description;
}