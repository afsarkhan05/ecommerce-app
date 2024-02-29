package com.zubi.ecommerce.product.service;

import com.zubi.ecommerce.product.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDTO> get(Long productId);
    Optional<List<ProductDTO>> list();
}
