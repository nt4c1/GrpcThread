package com.thread.app.service;

import com.thread.app.dto.ProductDTO;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO getProductById(Long id);

    ProductDTO getProductByName(String name);

    ProductDTO updateStock(Long id, int stock);

    void deleteProductById(Long id);

    List<ProductDTO> listAllProducts();

    List<ProductDTO> createProductsBatch(List<ProductDTO> productDTOs);


    Object updateStockByName(String name, int stock);
}