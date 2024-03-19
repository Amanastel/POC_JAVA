package com.featureflag.featureflag.controller;


import java.time.LocalDateTime;

import com.featureflag.featureflag.config.FeatureFlags;
import com.featureflag.featureflag.model.Product;
import com.featureflag.featureflag.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.togglz.core.manager.FeatureManager;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    FeatureManager featureManager;

    @GetMapping("/foo")
    public Product doFoo() {
        Product product = new Product();
        product.setName("product 1");
        product.setDescription("this is a cool product!");
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setPrice(4.99);

        if (featureManager.isActive(FeatureFlags.PRICE_INCREASE)) {
            product.setPrice(99.99);
        }

        if (featureManager.isActive(FeatureFlags.DESCRIPTION_UPDATE)) {
            product.setDescription("This is a brand new description");
        }

        return productRepository.save(product);

    }

}