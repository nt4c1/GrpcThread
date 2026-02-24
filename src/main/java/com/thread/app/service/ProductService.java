package com.thread.app.service;

import com.thread.app.dto.ProductDTO;

import java.util.List;
import java.util.Map;

public interface ProductService {

    // -------- SINGLE OPERATIONS --------
    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO getProductById(Long id);

    ProductDTO getProductByName(String name);

    ProductDTO updateStock(Long id, int stock);

    void deleteProductById(Long id);

    List<ProductDTO> listAllProducts();

    List<ProductDTO> createProductsBatch(List<ProductDTO> productDTOs);

    List<ProductDTO> updateStocksBatch(Map<Long, Integer> stockMap);
    

    void deleteProductsBatch(List<Long> ids);

    void shutdown();

    Object updateStockByName(String name, int stock);
}