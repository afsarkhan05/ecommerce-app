package com.zubi.ecommerce.product.service;

import com.zubi.ecommerce.product.dto.ProductDTO;
import com.zubi.ecommerce.product.model.Product;
import com.zubi.ecommerce.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setUserRepository(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductDTO> get(Long userId) {
        return Optional.of(productRepository.findById(userId)
                .map(Product::toDTO)
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Optional<List<ProductDTO>> list() {
        return Optional.of(productRepository.findAll()
                        .stream().map(Product::toDTO)
                .collect(Collectors.toList()));
        //return Optional.empty();
    }


    public Product addProduct(ProductDTO dto){
        return dto.toEntity();
    }
}
