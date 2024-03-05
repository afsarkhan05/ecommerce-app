package com.zubi.ecommerce.product.model;

import com.zubi.ecommerce.common.enums.ProductStatus;
import com.zubi.ecommerce.product.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_detail")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {


    @Column( name = "id")
    @Id
    private Long id;

    @Column(name ="name")
    private String name;

    @Column(name= "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    public ProductDTO toDTO(){
        return ProductDTO.builder()
                .productName(getName())
                .productId(getId())
                .price(getPrice())
                .quantity(getQuantity())
                .status(getStatus())
                .build();
    }
}
