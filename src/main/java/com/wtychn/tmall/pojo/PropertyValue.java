package com.wtychn.tmall.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@Entity
@ApiModel(value = "商品属性内容")
@Table(name = "propertyvalue")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class PropertyValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name="pid")
    private Product product;

    @ManyToOne
    @JoinColumn(name="ptid")
    private Property property;

    private String value;

    @Override
    public String toString() {
        return "PropertyValue [id=" + id + ", product=" + product + ", property=" + property + ", value=" + value + "]";
    }

}
