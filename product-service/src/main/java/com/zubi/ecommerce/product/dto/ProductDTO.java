package com.zubi.ecommerce.product.dto;

import com.zubi.ecommerce.common.enums.ProductStatus;
import com.zubi.ecommerce.product.model.Product;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDTO {

    private Long userId;
    private String userName;

    private Integer quantity;

    private Double price;

    private ProductStatus status;

    public Product toEntity(){
        return Product.builder()
                .id(getUserId())
                .name(getUserName())
                .price(getPrice())
                .quantity(getQuantity())
                .status(getStatus())
                .build();
    }
}
