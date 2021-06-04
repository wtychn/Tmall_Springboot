package com.wtychn.tmall.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.wtychn.tmall.pojo.Product;

public interface ProductESDAO extends ElasticsearchRepository<Product, Integer> {

}
