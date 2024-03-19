package com.featureflag.featureflag.repository;

import com.featureflag.featureflag.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<Product, Long>{

}
