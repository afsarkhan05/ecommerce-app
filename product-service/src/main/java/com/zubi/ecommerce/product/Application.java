package com.zubi.ecommerce.product;

import com.zubi.ecommerce.product.utils.AppConstants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@OpenAPIDefinition(servers = {@Server(url = "/product-service", description = AppConstants.DESC)})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}