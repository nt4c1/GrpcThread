package com.thread.app.dto;


import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Serdeable
public class ProductDTO {
    private Long id;
    private String name;
    private String sku;
    private double price;
    private int stock;

    public ProductDTO(String name, String sku, double price, int stock) {
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.stock = stock;
    }

    public ProductDTO() {
    }
}
