package com.wtychn.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wtychn.tmall.pojo.User;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByName(String name);
}
