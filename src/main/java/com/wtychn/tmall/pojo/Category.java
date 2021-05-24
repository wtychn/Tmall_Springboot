package com.wtychn.tmall.pojo;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "category")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Data
@ApiModel(value = "商品类别")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id")
    int id;

    String name;

    @Transient
    List<Product> products;

    @Transient
    List<List<Product>> productsByRow;

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }
}
