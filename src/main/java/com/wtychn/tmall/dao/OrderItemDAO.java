package com.wtychn.tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wtychn.tmall.pojo.Order;
import com.wtychn.tmall.pojo.OrderItem;
import com.wtychn.tmall.pojo.Product;
import com.wtychn.tmall.pojo.User;

public interface OrderItemDAO extends JpaRepository<OrderItem,Integer>{
    List<OrderItem> findByOrderOrderByIdDesc(Order order);
    List<OrderItem> findByProduct(Product product);
    List<OrderItem> findByUserAndOrderIsNull(User user);
}
