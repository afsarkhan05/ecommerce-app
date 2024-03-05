package com.zubi.ecommerce.product.dto;

import com.zubi.ecommerce.common.enums.ProductStatus;
import com.zubi.ecommerce.product.model.Product;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDTO {

    private Long productId;
    private String productName;

    private Double price;

    private Integer quantity;

    private ProductStatus status;

    public Product toEntity(){
        return Product.builder()
                .id(getProductId())
                .name(getProductName())
                .price(getPrice())
                .quantity(getQuantity())
                .status(getStatus())
                .build();
    }
}
