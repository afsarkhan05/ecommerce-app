package com.zubi.ecommerce.product.controller;

import com.zubi.ecommerce.product.service.ProductService;
import com.zubi.ecommerce.product.utils.AppConstants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setUserService(ProductService productService){
        this.productService = productService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable(value = AppConstants.ID, required = true) Long userId) {
        // User registration logic
        return productService.get(userId).map(this::ok).orElseThrow(EntityNotFoundException::new);
        //return ResponseEntity.ok(userService.get(userId));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listProduct() {
        // User registration logic
        return productService.list().map(this::ok).orElseThrow(EntityNotFoundException::new);
        //return ResponseEntity.ok(userService.get(userId));
    }

    private ResponseEntity ok(Object list){
        return new ResponseEntity(list, HttpStatus.OK);
    }

    // Additional endpoints for login, profile updates, etc.
}
