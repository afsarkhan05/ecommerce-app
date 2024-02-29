package com.zubi.ecommerce.product.repository;


import com.zubi.ecommerce.product.model.Product;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("select a from Product a where a.id = :productId ")
    Optional<Product> findById(@Param("productId") Long productId);
}
