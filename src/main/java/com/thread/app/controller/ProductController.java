package com.thread.app.controller;

import com.thread.app.dto.ProductDTO;
import com.thread.app.service.ProductService;
import io.micronaut.http.annotation.*;

import java.util.List;
import java.util.Map;

@Controller("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Post
    public ProductDTO create(@Body ProductDTO dto) {
        return productService.createProduct(dto);
    }

    @Post("/batch")
    public List<ProductDTO> createBatch(@Body List<ProductDTO> dtos) {
        return productService.createProductsBatch(dtos);
    }

    @Get("/{id}")
    public ProductDTO getById(Long id) {
        return productService.getProductById(id);
    }

    @Get
    public List<ProductDTO> getAll() {
        return productService.listAllProducts();
    }


    @Put("/{id}/stock/{stock}")
    public ProductDTO updateStock(Long id, int stock) {
        return productService.updateStock(id, stock);
    }


    @Delete("/{id}")
    public void delete(Long id) {
        productService.deleteProductById(id);
    }
}