package com.thread.app.service;

import com.thread.app.dto.ProductDTO;
import com.thread.app.entity.ProductEntity;
import com.thread.app.exception.AlreadyExistsException;
import com.thread.app.exception.NotFound;
import com.thread.app.mapper.ProductMapper;
import com.thread.app.repository.ProductRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Singleton
public class ProductServiceTImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public ProductServiceTImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        if (productRepository.existsBySku(productDTO.getSku())) {
            throw new AlreadyExistsException("Product with sku " + productDTO.getSku() + " already exists");
        }
        ProductEntity productEntity = productMapper.toEntity(productDTO);
        productEntity = productRepository.save(productEntity);
        return productMapper.toDTO(productEntity);
    }
    @Override
    public ProductDTO getProductById(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new NotFound("Product with id " + id + " not found")
                );
        return productMapper.toDTO(productEntity);
    }

    @Override
    public ProductDTO getProductByName(String name) {
        ProductEntity entity = productRepository.findByName(name)
                .orElseThrow(() ->
                        new NotFound("Product with name " + name + " not found")
                );

        return productMapper.toDTO(entity);
    }

    @Override
    public Object updateStockByName(String name, int stock) {
        ProductEntity entity =  productRepository.findByName(name)
                .orElseThrow(() -> new NotFound("Product not found"));
        entity.setStock(stock);
        entity.setName(name);
        return productMapper.toDTO(productRepository.update(entity));
    }

    @Override
    @Transactional
    public ProductDTO updateStock(Long id, int stock) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new NotFound("Product with id " + id + " not found"));
        productEntity.setStock(stock);
        productRepository.update(productEntity);
        return productMapper.toDTO(productEntity);
    }

    @Override
    @Transactional
    public void deleteProductById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new NotFound("Product with id " + id + " not found");
        }
    }

    @Override
    public List<ProductDTO> listAllProducts() {
            return productRepository.findAll()
                    .stream()
                    .map(productMapper::toDTO)
                    .toList();
    }

    @Override
    public List<ProductDTO> createProductsBatch(List<ProductDTO> productDTOs) {
        if (productDTOs == null || productDTOs.isEmpty()) {
            return List.of();
        }

        List<CompletableFuture<ProductDTO>> futures = productDTOs.stream()
                .map(dto->
                        CompletableFuture.supplyAsync(()-> createProduct(dto),executorService)).toList();
        return futures.stream()
                .map(CompletableFuture::join)
                .toList();

    }
}