package com.thread.app.mapper;

import com.thread.app.dto.ProductDTO;
import com.thread.app.entity.ProductEntity;
import com.thread.app.protos.*;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class ProductMapper {

    public static ProductDTO fromGrpc(CreateProductRequest request) {
        return new  ProductDTO(
                request.getName(),
                request.getSku(),
                request.getPrice(),
               request.getStock()
        );
    }

    public static Product toGrpc(ProductDTO created) {
        return Product.newBuilder()
                .setId(created.getId())
                .setName(created.getName())
                .setSku(created.getSku())
                .setPrice(created.getPrice())
                .setStock(created.getStock())
                .build();
    }

    public static CreateProductsResponse toGrpcList(List<ProductDTO> products) {

        CreateProductsResponse.Builder builder =
                CreateProductsResponse.newBuilder();

        products.forEach(product ->
                builder.addProducts(toGrpc(product)));

        return builder.build();
    }


    public ProductDTO toDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setName(productEntity.getName());
        productDTO.setSku(productEntity.getSku());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setStock(productEntity.getStock());
        return productDTO;
    }

    public ProductEntity toEntity(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDTO.getName());
        productEntity.setSku(productDTO.getSku());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setStock(productDTO.getStock());
        return productEntity;
    }



}
