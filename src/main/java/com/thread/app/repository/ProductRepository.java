package com.thread.app.repository;

import com.thread.app.entity.ProductEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import javax.annotation.Nullable;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity , Long> {
    boolean existsBySku(@Nullable String sku);
    Optional<ProductEntity> findByName(String name);
}
