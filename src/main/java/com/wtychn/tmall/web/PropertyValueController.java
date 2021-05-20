package com.wtychn.tmall.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "属性值管理")
public class PropertyValueController {
    @Autowired PropertyValueService propertyValueService;
    @Autowired ProductService productService;

    @ApiOperation(value = "按产品id查询商品属性值")
    @GetMapping("/products/{pid}/propertyValues")
    public List<PropertyValue> list(@PathVariable("pid") int pid) throws Exception {
        Product product = productService.get(pid);
        propertyValueService.init(product);
        return propertyValueService.list(product);
    }

    @ApiOperation(value = "更新属性值")
    @PutMapping("/propertyValues")
    public Object update(@RequestBody PropertyValue bean) throws Exception {
        propertyValueService.update(bean);
        return bean;
    }

}
