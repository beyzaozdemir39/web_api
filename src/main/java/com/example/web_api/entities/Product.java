package com.example.web_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Ürün adı boş olamaz")
    @Size(min = 3, max = 100, message = "Ürün adı 3 ile 100 karakter arasında olmalıdır")
    private String name;

    @NotNull(message = "Ürün açıklaması boş olamaz")
    private String description;

    @NotNull(message = "Ürün fiyatı boş olamaz")
    @Min(value = 0, message = "Ürün fiyatı 0'dan küçük olamaz")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;
}
