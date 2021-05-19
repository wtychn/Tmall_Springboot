package com.wtychn.tmall.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wtychn.tmall.pojo.Product;
import com.wtychn.tmall.pojo.PropertyValue;
import com.wtychn.tmall.service.ProductService;
import com.wtychn.tmall.service.PropertyValueService;

import java.util.List;

@RestController
public class PropertyValueController {
    @Autowired PropertyValueService propertyValueService;
    @Autowired ProductService productService;

    @GetMapping("/products/{pid}/propertyValues")
    public List<PropertyValue> list(@PathVariable("pid") int pid) throws Exception {
        Product product = productService.get(pid);
        propertyValueService.init(product);
        return propertyValueService.list(product);
    }

    @PutMapping("/propertyValues")
    public Object update(@RequestBody PropertyValue bean) throws Exception {
        propertyValueService.update(bean);
        return bean;
    }

}
