package com.wtychn.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wtychn.tmall.pojo.Product;
import com.wtychn.tmall.pojo.ProductImage;

import java.util.List;

public interface ProductImageDAO extends JpaRepository<ProductImage,Integer>{
    public List<ProductImage> findByProductAndTypeOrderByIdDesc(Product product, String type);
}
