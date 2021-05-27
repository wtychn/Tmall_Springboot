package com.wtychn.tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wtychn.tmall.pojo.Order;
import com.wtychn.tmall.pojo.User;

public interface OrderDAO extends JpaRepository<Order,Integer>{
    public List<Order> findByUserAndStatusNotOrderByIdDesc(User user, String status);
}
